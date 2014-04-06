package com.github.dylon.liblevenshtein.levenshtein;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.ITransitionFunction;
import com.github.dylon.liblevenshtein.collection.dawg.IFinalFunction;

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
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class AbstractTransducer<DictionaryNode>
  implements ITransducer<DictionaryNode> {

  /**
   * Default, maximum number of spelling errors candidates may have from the
   * query term.
   */
  int defaultMaxDistance = Integer.MAX_VALUE;

  /**
   * Returns state-transition functions for specific, max edit distances
   */
  ILevenshteinTransitionFunctionFactory stateTransitionFactory;

  /**
   * Returns instances of some, generic collection that is used to store
   * spelling candidates for the query term.
   */
  ICandidateCollectionFactory<DictionaryNode> candidatesFactory;

  /**
   * Returns instances of priority queues used for tracking the dictionary,
   * spelling candidates most-similar to the query term.
   */
  INearestCandidatesFactory<DictionaryNode> nearestCandidatesFactory;

  /**
   * Returns instances of a data structure used for maintaining information
   * regarding each step in intersecting the dictionary automaton with the
   * Levenshtein automaton.
   */
  IIntersectionFactory<DictionaryNode> intersectionFactory;

  /**
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   */
  IDistanceFunction minDistance;

  /**
   * Returns whether a dictionary node is the final character in some term.
   */
  IFinalFunction<DictionaryNode> isFinal;

  /**
   * Transition function for dictionary nodes.
   */
  ITransitionFunction<DictionaryNode> dictionaryTransition;

  /**
   * State at which to begin traversing the Levenshtein automaton.
   */
  int[][] initialState;

  /**
   * Root node of the dictionary, at which to begin searching for spelling
   * candidates.
   */
  DictionaryNode dictionaryRoot;

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
  public void defaultMaxDistance(final int defaultMaxDistance) {
    if (defaultMaxDistance < 0) {
      throw new IllegalArgumentException(
          "defaultMaxDistance must be non-negative");
    }
    this.defaultMaxDistance = defaultMaxDistance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICandidateCollection<DictionaryNode> transduce(
      @NonNull final String term) {
    return transduce(term, defaultMaxDistance);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICandidateCollection<DictionaryNode> transduce(
      @NonNull final String term,
      final int maxDistance) {

    if (maxDistance < 0) {
      throw new IllegalArgumentException(
          "maxDistance must be non-negative: " + maxDistance);
    }

    final int termLength = term.length();
    final ILevenshteinTransitionFunction stateTransition =
      stateTransitionFactory.build(maxDistance);
    final ICandidateCollection<DictionaryNode> candidates =
      candidatesFactory.build();

    // so results can be ranked by similarity to the query term, etc.
    final PriorityQueue<Intersection<DictionaryNode>> nearestCandidates =
      nearestCandidatesFactory.build(term);

    nearestCandidates.enqueue(
        intersectionFactory.build(
          "",
          dictionaryRoot,
          initialState,
          minDistance.at(initialState, termLength)));

    final int a = (maxDistance << 1) + 1;
    Intersection<DictionaryNode> intersection = null;

    try {
      while (!nearestCandidates.isEmpty()) {
        intersection = nearestCandidates.dequeue();
        final String candidate = intersection.candidate();
        final DictionaryNode dictionaryNode = intersection.dictionaryNode();
        final int[][] levenshteinState = intersection.levenshteinState();
        final int i = levenshteinState[0][0];
        final int b = termLength - i;
        final int k = (a < b) ? a : b;
        final CharIterator labels = dictionaryTransition.of(dictionaryNode);
        while (labels.hasNext()) {
          final char label = labels.nextChar();
          final DictionaryNode nextDictionaryNode =
            dictionaryTransition.of(dictionaryNode, label);
          final boolean[] characteristicVector =
            characteristicVector(label, term, k, i);
          final int[][] nextLevenshteinState =
            stateTransition.of(levenshteinState, /*given*/ characteristicVector);
          if (null != nextLevenshteinState) {
            final String nextCandidate = candidate + label;
            final int distance =
              minDistance.at(nextLevenshteinState, termLength);
            enqueueAll(
                nearestCandidates,
                candidates,
                nextCandidate,
                nextDictionaryNode,
                nextLevenshteinState,
                distance,
                maxDistance);
          }
        }
      }
    }
    catch (final QueueFullException exception) {
      // Nothing to do, this was expected (early termination) ...
    }
    finally {
      if (null != intersection) {
        intersectionFactory.recycle(intersection);
      }
      nearestCandidatesFactory.recycle(nearestCandidates);
      stateTransitionFactory.recycle(stateTransition);
    }

    return candidates;
  }

  /**
   * Enqueues into the results collection, candidates, all of the spelling
   * candidates corresponding to the dictionary node.
   * @param nearestCandidates Maintains which nodes to explore next
   * @param candidates Collection of spelling candidates
   * @param candidate Prefix (maybe whole term) of some spelling candidate
   * @param dictionaryNode Current node in the dictionary automaton
   * @param levenshteinState Current state in the Levenshtein automaton
   * @param distance Minimum distance corresponding to levenshteinState
   * @param maxDistance Maximum number of spelling errors candidates may have
   * @throws QueueFullException When the results queue can no longer accept
   * spelling candidates. This signifies that the transducer should return
   * immediately.
   */
  protected abstract void enqueueAll(
      PriorityQueue<Intersection<DictionaryNode>> nearestCandidates,
      ICandidateCollection<DictionaryNode> candidates,
      String candidate,
      DictionaryNode dictionaryNode,
      int[][] levenshteinState,
      int distance,
      int maxDistance);

  /**
   * Specifies when transduce(...) should return early.  This is thrown
   * (optionally) from enqueueAll(...) when not all the candidate terms where
   * queued into the results.
   */
  protected static class QueueFullException extends RuntimeException {
    static final long serialVersionUID = 1L;
  }
}
