package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.github.liblevenshtein.collection.dictionary.Dawg;
import com.github.liblevenshtein.collection.dictionary.DawgNode;
import com.github.liblevenshtein.collection.dictionary.factory.DawgFactory;
import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.DistanceFunction;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.MergeFunction;
import com.github.liblevenshtein.transducer.SpecialPositionComparator;
import com.github.liblevenshtein.transducer.StandardPositionComparator;
import com.github.liblevenshtein.transducer.State;
import com.github.liblevenshtein.transducer.SubsumesFunction;
import com.github.liblevenshtein.transducer.Transducer;
import com.github.liblevenshtein.transducer.TransducerAttributes;
import com.github.liblevenshtein.transducer.UnsubsumeFunction;

/**
 * Fluently-builds Levenshtein transducers.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Slf4j
@Setter
@SuppressWarnings("checkstyle:classdataabstractioncoupling")
public class TransducerBuilder implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds DAWG collections from dictionaries.
   */
  private final DawgFactory dawgFactory = new DawgFactory();

  /**
   * Dictionary automaton for seeking spelling candidates.
   */
  @Setter
  @SuppressWarnings("unchecked")
  private Collection<String> dictionary = Collections.EMPTY_LIST;

  /**
   * Whether {@link #dictionary} is sorted.
   */
  @Setter
  private boolean isSorted = false;

  /**
   * Desired Levenshtein algorithm for searching.
   */
  @Setter
  @NonNull
  private Algorithm algorithm = Algorithm.STANDARD;

  /**
   * Default maximum number of errors tolerated between each spelling candidate
   * and the query term.
   */
  @Setter
  private int defaultMaxDistance = 2;

  /**
   * Whether the distances between each spelling candidate and the query term
   * should be included in the collections of spelling candidates.
   */
  @Setter
  private boolean includeDistance = true;

  /**
   * Specifies the collection of dictionary terms for the dictionary automaton.
   * @param dictionary Collection of dictionary terms to consider when
   *   generating spelling candidates.
   * @param isSorted Whether the dictionary is sorted.  If it is not sorted then
   *   it will probably be sorted.
   * @return This {@link TransducerBuilder} or an equivalent one, for fluency.
   */
  public TransducerBuilder dictionary(
      @NonNull final Collection<String> dictionary,
      final boolean isSorted) {
    this.dictionary = dictionary;
    this.isSorted = isSorted;
    return this;
  }

  /**
   * Builds a Levenshtein transducer according to the parameters set for this
   * {@link TransducerBuilder}.
   * @param <CandidateType> Implicit type of the spelling candidates generated
   *   by the transducer.
   * @return Levenshtein transducer for seeking spelling candidates for query
   *   terms (fuzzy searching!).
   */
  @SuppressWarnings("unchecked")
  public <CandidateType> ITransducer<CandidateType> build() {
    log.info("Building transducer out of [{}] terms with isSorted [{}], "
        + "algorithm [{}], defaultMaxDistance [{}], and includeDistance [{}]",
        dictionary.size(), isSorted, algorithm, defaultMaxDistance,
        includeDistance);

    final Dawg dictionary = dawgFactory.build(this.dictionary, this.isSorted);
    final PositionFactory positionFactory = new PositionFactory();
    final StateFactory stateFactory = new StateFactory();

    final PositionTransitionFactory positionTransitionFactory = positionTransitionFactory();
    positionTransitionFactory.stateFactory(stateFactory);
    positionTransitionFactory.positionFactory(positionFactory);

    final StateTransitionFactory stateTransitionFactory = stateTransitionFactory();
    stateTransitionFactory.stateFactory(stateFactory);
    stateTransitionFactory.positionTransitionFactory(positionTransitionFactory);

    final State initialState = stateFactory.build(positionFactory.build(0, 0));

    final TransducerAttributes<DawgNode, CandidateType> attributes =
      TransducerAttributes.<DawgNode, CandidateType>builder()
        .maxDistance(defaultMaxDistance)
        .stateTransitionFactory(stateTransitionFactory)
        .candidateFactory(candidateFactory())
        .minDistance(minDistance())
        .isFinal(dawgFactory.finalFunction(dictionary))
        .dictionaryTransition(dawgFactory.transitionFunction(dictionary))
        .dictionaryRoot(dictionary.root())
        .initialState(initialState)
        .dictionary(dictionary)
        .algorithm(algorithm)
        .includeDistance(includeDistance)
        .build();

    return new Transducer<>(attributes);
  }

  /**
   * Builds the factory for spelling candidates, according to whether they
   * should include the candidates' distances from query terms.
   * @param <CandidateType> Implicit type of the spelling candidates generated
   *   by the transducer.
   * @return Factory for spelling candidates.
   */
  @SuppressWarnings("unchecked")
  protected <CandidateType> CandidateFactory<CandidateType> candidateFactory() {
    return (CandidateFactory<CandidateType>)
      (includeDistance
        ? new CandidateFactory.WithDistance()
        : new CandidateFactory.WithoutDistance());
  }

  /**
   * Builds the function that finds the distance between spelling candidates and
   * the query term.
   * @return Levenshtein algorithm-specific, distance function.
   */
  protected DistanceFunction minDistance() {
    switch (algorithm) {
      case STANDARD:
        return new DistanceFunction.ForStandardPositions();
      case TRANSPOSITION: // fall through
      case MERGE_AND_SPLIT:
        return new DistanceFunction.ForSpecialPositions();
      default:
        throw new IllegalArgumentException(unsupportedAlgorithm(algorithm));
    }
  }

  /**
   * Builds an {@link #algorithm}-specific, position-transition factory.
   * @return {@link #algorithm}-specific, position-transition factory.
   */
  protected PositionTransitionFactory positionTransitionFactory() {
    switch (algorithm) {
      case STANDARD:
        return new PositionTransitionFactory.ForStandardPositions();
      case TRANSPOSITION:
        return new PositionTransitionFactory.ForTranspositionPositions();
      case MERGE_AND_SPLIT:
        return new PositionTransitionFactory.ForMergeAndSplitPositions();
      default:
        throw new IllegalArgumentException(unsupportedAlgorithm(algorithm));
    }
  }

  /**
   * Builds a state-transition factory from the parameters specified at the time
   * {@link #build()} was called.
   * @return New state-transition factory.
   */
  protected StateTransitionFactory stateTransitionFactory() {
    switch (algorithm) {
      case STANDARD:
        return new StateTransitionFactory()
          .comparator(new StandardPositionComparator())
          .merge(new MergeFunction.ForStandardPositions())
          .unsubsume(new UnsubsumeFunction.ForStandardPositions()
              .subsumes(new SubsumesFunction.ForStandardAlgorithm()));
      case TRANSPOSITION:
        return new StateTransitionFactory()
          .comparator(new SpecialPositionComparator())
          .merge(new MergeFunction.ForSpecialPositions())
          .unsubsume(new UnsubsumeFunction.ForSpecialPositions()
              .subsumes(new SubsumesFunction.ForTransposition()));
      case MERGE_AND_SPLIT:
        return new StateTransitionFactory()
          .comparator(new SpecialPositionComparator())
          .merge(new MergeFunction.ForSpecialPositions())
          .unsubsume(new UnsubsumeFunction.ForSpecialPositions()
              .subsumes(new SubsumesFunction.ForMergeAndSplit()));
      default:
        throw new IllegalArgumentException(unsupportedAlgorithm(algorithm));
    }
  }

  /**
   * Generates a message for algorithms that aren't supported by various
   * methods.
   * @param algorithm The unsupported algorithm.
   * @return A message stating that some algorithm is unsupported.
   */
  protected String unsupportedAlgorithm(final Algorithm algorithm) {
    return String.format("Unsupported algorithm [%s]", algorithm);
  }
}
