package com.github.dylon.liblevenshtein.levenshtein;

import java.util.ArrayDeque;
import java.util.Deque;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.val;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.IFinalFunction;
import com.github.dylon.liblevenshtein.collection.dawg.ITransitionFunction;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.ICandidateCollectionBuilder;
import com.github.dylon.liblevenshtein.levenshtein.factory.IIntersectionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IStateTransitionFactory;

/**
 * The algorithm for imitating Levenshtein automata was taken from the
 * following journal article:
 *
 * @ARTICLE{Schulz02faststring,
 *   author = {Klaus Schulz and Stoyan Mihov},
 *   title = {Fast String Correction with Levenshtein-Automata},
 *   journal = {INTERNATIONAL JOURNAL OF DOCUMENT ANALYSIS AND RECOGNITION},
 *   year = {2002},
 *   volume = {5},
 *   pages = {67--85}
 * }
 *
 * As well, this Master Thesis helped me understand its concepts:
 *
 *   www.fmi.uni-sofia.bg/fmi/logic/theses/mitankin-en.pdf
 *
 * The supervisor of the student who submitted the thesis was one of the authors
 * of the journal article, above.
 *
 * The algorithm for constructing a DAWG (Direct Acyclic Word Graph) from the
 * input dictionary of words (DAWGs are otherwise known as an MA-FSA, or Minimal
 * Acyclic Finite-State Automata), was taken and modified from the following
 * blog from Steve Hanov:
 *
 *   http://stevehanov.ca/blog/index.php?id=115
 *
 * The algorithm therein was taken from the following paper:
 *
 * @MISC{Daciuk00incrementalconstruction,
 *   author = {Jan Daciuk and
 *     Bruce W. Watson and
 *     Richard E. Watson and
 *     Stoyan Mihov},
 *   title = {Incremental Construction of Minimal Acyclic Finite-State Automata},
 *   year = {2000}
 * }
 *
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Setter
@FieldDefaults(level=AccessLevel.PROTECTED)
public class Transducer<DictionaryNode, CandidateType>
  implements ITransducer<CandidateType> {

  private static final QueueFullException queueIsFull = new QueueFullException();

  /**
   * Default, maximum number of spelling errors candidates may have from the
   * query term.
   */
  int defaultMaxDistance = Integer.MAX_VALUE;

  /**
   * Returns state-transition functions for specific, max edit distances
   */
  @NonNull IStateTransitionFactory stateTransitionFactory;

  /**
   * Returns instances of some, generic collection that is used to store
   * spelling candidates for the query term.
   */
  @NonNull ICandidateCollectionBuilder<CandidateType> candidatesBuilder;

  /**
   * Returns instances of a data structure used for maintaining information
   * regarding each step in intersecting the dictionary automaton with the
   * Levenshtein automaton.
   */
  @NonNull IIntersectionFactory<DictionaryNode> intersectionFactory;

  /**
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   */
  @NonNull IDistanceFunction minDistance;

  /**
   * Returns whether a dictionary node is the final character in some term.
   */
  @NonNull IFinalFunction<DictionaryNode> isFinal;

  /**
   * Transition function for dictionary nodes.
   */
  @NonNull ITransitionFunction<DictionaryNode> dictionaryTransition;

  /**
   * State at which to begin traversing the Levenshtein automaton.
   */
  @NonNull IState initialState;

  /**
   * Root node of the dictionary, at which to begin searching for spelling
   * candidates.
   */
  @NonNull DictionaryNode dictionaryRoot;

  /** Pools instances of characteristic vectors */
  private boolean[][] characteristicVectors = new boolean[32][];

  /**
   * Returns the characteristic vector of the term, from its characters between
   * index i and index k. The characteristic vector contains true at each index
   * where the corresponding character of the term is the value of x, and false
   * elsewhere.
   * @param x char to find all occurrences of in the relevant substring of term
   * @param term Term in which to find all occurrences of the character, x
   * @param k Length of the substring of term to examine
   * @param i Base-index of the substring of term to examine
   * @return Characteristic vector marking where x appears in the relevant
   * substring of term.
   */
  private boolean[] characteristicVector(
      final char x,
      final String term,
      final int k,
      final int i) {

    boolean[] characteristicVector;

    if (k >= characteristicVectors.length) {
      final int m = characteristicVectors.length << 1;
      final int n = (m > k) ? m : (k << 1);

      final boolean[][] characteristicVectors = new boolean[n][];

      for (int j = 0; j < this.characteristicVectors.length; ++j) {
        characteristicVectors[j] = this.characteristicVectors[j];
      }

      characteristicVector = new boolean[k];
      characteristicVectors[k] = characteristicVector;
      this.characteristicVectors = characteristicVectors;
    }
    else {
      characteristicVector = characteristicVectors[k];

      if (null == characteristicVector) {
        characteristicVector = new boolean[k];
        characteristicVectors[k] = characteristicVector;
      }
    }

    for (int j = 0; j < k; ++j) {
      characteristicVector[j] = (x == term.charAt(i + j));
    }

    return characteristicVector;
  }

  /**
   * Sets the default, maximum number of spelling errors candidates may have
   * from the query term.
   * @param defaultMaxDistance Default, maximum number of spelling errors
   * candidates may have from the query term.
   */
  public Transducer defaultMaxDistance(final int defaultMaxDistance) {
    if (defaultMaxDistance < 0) {
      throw new IllegalArgumentException(
          "defaultMaxDistance must be non-negative");
    }
    this.defaultMaxDistance = defaultMaxDistance;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICandidateCollection<CandidateType> transduce(
      @NonNull final String term) {
    return transduce(term, defaultMaxDistance);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICandidateCollection<CandidateType> transduce(
      @NonNull final String term,
      final int maxDistance) {

    if (maxDistance < 0) {
      throw new IllegalArgumentException(
          "maxDistance must be non-negative: " + maxDistance);
    }

    final int termLength = term.length();
    final IStateTransitionFunction stateTransition =
      stateTransitionFactory.build(maxDistance);
    final ICandidateCollection<CandidateType> candidates = candidatesBuilder.build();

    // so results can be ranked by similarity to the query term, etc.
    final Deque<Intersection<DictionaryNode>> pendingQueue = new ArrayDeque<>();

    pendingQueue.addLast(
        intersectionFactory.build(
          "",
          dictionaryRoot,
          initialState));

    // f(x) := x * 2 + 1
    // a := (n - 1) / 2
    // f(a) = (n - 1) / 2 * 2 + 1 = n - 1 + 1 = n
    //
    // We want to cap the value of "a" at "n = max integer" so "f(a)" does not
    // overflow, which it would if "a > (n - 1) / 2".  In other words, define:
    // g(x) := { x * 2 + 1  , if a < (n - 1) / 2
    //         { n          , otherwise
    //
    // If there was no upper bound for integer values, this would be equivalent:
    // h(x) := min {f(x), n}
    final int a = (maxDistance < (Integer.MAX_VALUE - 1) >> 1)
      ? (maxDistance << 1) + 1
      : Integer.MAX_VALUE;

    try {
      while (!pendingQueue.isEmpty()) {
        Intersection<DictionaryNode> intersection = pendingQueue.removeFirst();
        final String candidate = intersection.candidate();
        final DictionaryNode dictionaryNode = intersection.dictionaryNode();
        final IState levenshteinState = intersection.levenshteinState();

        intersectionFactory.recycle(intersection);
        intersection = null;

        final int i = levenshteinState.getOuter(0)[0];
        final int b = termLength - i;
        final int k = (a < b) ? a : b;
        final CharIterator labels = dictionaryTransition.of(dictionaryNode);
        while (labels.hasNext()) {
          final char label = labels.nextChar();
          final DictionaryNode nextDictionaryNode =
            dictionaryTransition.of(dictionaryNode, label);
          final boolean[] characteristicVector =
            characteristicVector(label, term, k, i);
          final IState nextLevenshteinState =
            stateTransition.of(levenshteinState, /*given*/ characteristicVector);
          if (null != nextLevenshteinState) {
            final String nextCandidate = candidate + label;

            pendingQueue.addLast(
                intersectionFactory.build(
                  nextCandidate,
                  nextDictionaryNode,
                  nextLevenshteinState));

            if (isFinal.at(nextDictionaryNode)) {
              final int distance = minDistance.at(nextLevenshteinState, termLength);
              if (distance <= maxDistance && !candidates.offer(nextCandidate, distance)) {
                throw queueIsFull;
              }
            }
          }
        }
      }
    }
    catch (final QueueFullException exception) {
      // Nothing to do, this was expected (early termination) ...
    }
    finally {
      stateTransitionFactory.recycle(stateTransition);
    }

    return candidates;
  }

  /**
   * Specifies when transduce(...) should return early.  This is thrown
   * (optionally) when not all the candidate terms where queued into the
   * results.
   */
  protected static class QueueFullException extends RuntimeException {
    static final long serialVersionUID = 1L;
  }
}
