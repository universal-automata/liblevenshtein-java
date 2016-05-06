package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.github.liblevenshtein.collection.TakeIterator;
import com.github.liblevenshtein.collection.dictionary.DawgNode;
import com.github.liblevenshtein.collection.dictionary.SortedDawg;
import com.github.liblevenshtein.collection.dictionary.factory.DawgFactory;
import com.github.liblevenshtein.collection.dictionary.factory.DawgNodeFactory;
import com.github.liblevenshtein.collection.dictionary.factory.IDawgFactory;
import com.github.liblevenshtein.collection.dictionary.factory.IPrefixFactory;
import com.github.liblevenshtein.collection.dictionary.factory.PrefixFactory;
import com.github.liblevenshtein.collection.dictionary.factory.TransitionFactory;
import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.ICandidateCollection;
import com.github.liblevenshtein.transducer.IDistanceFunction;
import com.github.liblevenshtein.transducer.IState;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.MergeFunction;
import com.github.liblevenshtein.transducer.StandardPositionComparator;
import com.github.liblevenshtein.transducer.StandardPositionDistanceFunction;
import com.github.liblevenshtein.transducer.SubsumesFunction;
import com.github.liblevenshtein.transducer.Transducer;
import com.github.liblevenshtein.transducer.TransducerAttributes;
import com.github.liblevenshtein.transducer.UnsubsumeFunction;
import com.github.liblevenshtein.transducer.XPositionComparator;
import com.github.liblevenshtein.transducer.XPositionDistanceFunction;

/**
 * Fluently-builds Levenshtein transducers.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Slf4j
public class TransducerBuilder implements ITransducerBuilder, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Format of error messages about unsupported algorithms.
   */
  private static final String UNSUPPORTED_ALGORITHM = "Unsupported Algorithm: ";

  /**
   * Builds and recycles {@link DawgNode} {@link com.github.liblevenshtein.collection.dictionary.Prefix}es.
   */
  private final IPrefixFactory<DawgNode> prefixFactory = new PrefixFactory<>();

  /**
   * Builds DAWG collections from dictionaries.
   */
  @SuppressWarnings("unchecked")
  private final IDawgFactory<DawgNode, SortedDawg> dawgFactory =
    (IDawgFactory<DawgNode, SortedDawg>)
    (Object)
      new DawgFactory()
        .dawgNodeFactory(new DawgNodeFactory())
        .prefixFactory(prefixFactory)
        .transitionFactory(new TransitionFactory<DawgNode>());

  /**
   * Dictionary automaton for seeking spelling candidates.
   */
  private SortedDawg dictionary;

  /**
   * Desired Levenshtein algorithm for searching.
   * -- SETTER --
   * Desired Levenshtein algorithm for searching.
   * @param algorithm Desired Levenshtein algorithm for searching.
   * @return This {@link TransducerBuilder} for fluency.
   */
  @NonNull
  @Setter(onMethod = @_({@Override}))
  private Algorithm algorithm = Algorithm.STANDARD;

  /**
   * Default maximum number of errors tolerated between each spelling candidate
   * and the query term.
   * -- SETTER --
   * Default maximum number of errors tolerated between each spelling candidate
   * and the query term.
   * @param defaultMaxDistance Default maximum number of errors tolerated
   * between each spelling candidate and the query term.
   * @return This {@link TransducerBuilder} for fluency.
   */
  @Setter(onMethod = @_({@Override}))
  private int defaultMaxDistance = Integer.MAX_VALUE;

  /**
   * Whether the distances between each spelling candidate and the query term
   * should be included in the collections of spelling candidates.
   * -- SETTER --
   * Whether the distances between each spelling candidate and the query term
   * should be included in the collections of spelling candidates.
   * @param includeDistance Whether the distances between each spelling
   * candidate and the query term should be included in the collections of
   * spelling candidates.
   * @return This {@link TransducerBuilder} for fluency.
   */
  @Setter(onMethod = @_({@Override}))
  private boolean includeDistance = true;

  /**
   * If desired, the maximum number of elements in the collections of spelling
   * candidates.
   * -- SETTER --
   * <p>
   * <b>WARNING</b> This parameter has been deprecated and is schemduled for
   * removal in the next major release.  Rather than specifying it here, take
   * {@link #maxCandidates} number of elements from the {@link ICandidateCollection}
   * returned from the transducer.
   * </p>
   *
   * <p>
   * If desired, the maximum number of elements in the collections of spelling
   * candidates.
   * </p>
   *
   * @param maxCandidates If desired, the maximum number of elements in the
   * collections of spelling candidates.
   * @return This {@link TransducerBuilder} for fluency.
   */
  @Setter(onMethod = @_({@Override, @Deprecated}))
  private int maxCandidates = Integer.MAX_VALUE;

  /**
   * {@inheritDoc}
   */
  @Override
  public ITransducerBuilder dictionary(@NonNull final Collection<String> dictionary) {
    return dictionary(dictionary, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ITransducerBuilder dictionary(
      @NonNull final Collection<String> dictionary,
      final boolean isSorted) {

    if (dictionary instanceof SortedDawg) {
      this.dictionary = (SortedDawg) dictionary;
    }
    else {
      this.dictionary = dawgFactory.build(dictionary, isSorted);
    }

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <CandidateType> ITransducer<CandidateType> build() {
    log.info("Building transducer out of [{}] terms with algorithm [{}], "
        + "defaultMaxDistance [{}], includeDistance [{}], and maxCandidates [{}]",
        dictionary.size(), algorithm, defaultMaxDistance, includeDistance,
        maxCandidates);

    final IStateFactory stateFactory =
      new StateFactory().elementFactory(new ElementFactory<int[]>());

    final TransducerAttributes<DawgNode, CandidateType> attributes =
      new TransducerAttributes<DawgNode, CandidateType>()
        .maxDistance(defaultMaxDistance)
        .stateTransitionFactory(buildStateTransitionFactory(stateFactory))
        .candidateFactory(
          (ICandidateFactory<CandidateType>)
          (includeDistance
            ? new CandidateFactory.WithDistance()
            : new CandidateFactory.WithoutDistance()))
        .intersectionFactory(new IntersectionFactory<DawgNode>())
        .minDistance(buildMinDistance())
        .isFinal(dawgFactory.isFinal(dictionary))
        .dictionaryTransition(dawgFactory.transition(dictionary))
        .initialState(buildInitialState(stateFactory))
        .dictionaryRoot(dictionary.root())
        .dictionary(dictionary)
        .algorithm(algorithm)
        .maxCandidates(maxCandidates)
        .includeDistance(includeDistance);

    final Transducer<DawgNode, CandidateType> transducer =
      new Transducer<>(attributes);

    if (maxCandidates == Integer.MAX_VALUE) {
      return transducer;
    }

    return new DeprecatedTransducerForLimitingNumberOfCandidates<>(
        maxCandidates,
        transducer);
  }

  /**
   * Builds the function that finds the distance between spelling candidates and
   * the query term.
   * @return Levenshtein algorithm-specific, distance function.
   */
  protected IDistanceFunction buildMinDistance() {
    switch (algorithm) {
      case STANDARD:
        return new StandardPositionDistanceFunction();
      case TRANSPOSITION: // fall through
      case MERGE_AND_SPLIT:
        return new XPositionDistanceFunction();
      default:
        throw new IllegalArgumentException(UNSUPPORTED_ALGORITHM + algorithm);
    }
  }

  /**
   * Builds the initial state from which to begin searching the dictionary
   * automaton for spelling candidates.
   * @param stateFactory Builds and recycles Levenshtein states.
   * @return Start state for traversing the dictionary automaton.
   */
  protected IState buildInitialState(@NonNull final IStateFactory stateFactory) {
    switch (algorithm) {
      case STANDARD:
        return stateFactory.build(new int[] {0, 0});
      case TRANSPOSITION: // fall through
      case MERGE_AND_SPLIT:
        return stateFactory.build(new int[] {0, 0, 0});
      default:
        throw new IllegalArgumentException(UNSUPPORTED_ALGORITHM + algorithm);
    }
  }

  /**
   * Builds a state-transition factory from the parameters specified at the time
   * {@link #build()} was called.
   * @param stateFactory Builds Levenshtein states.
   * @return New state-transition factory.
   */
  protected IStateTransitionFactory buildStateTransitionFactory(
      @NonNull final IStateFactory stateFactory) {

    final StateTransitionFactory stateTransitionFactory =
      new StateTransitionFactory().stateFactory(stateFactory);

    final PositionTransitionFactory positionTransitionFactory;
    final IPositionFactory positionFactory;

    switch (algorithm) {
      case STANDARD:
        positionTransitionFactory =
          new PositionTransitionFactory.ForStandardPositions();
        positionFactory = new PositionFactory.ForStandardPositions();
        stateTransitionFactory
          .comparator(new StandardPositionComparator())
          .positionTransitionFactory(positionTransitionFactory)
          .merge(new MergeFunction.ForStandardPositions()
              .positionFactory(positionFactory))
          .unsubsume(new UnsubsumeFunction.ForStandardPositions()
              .subsumes(new SubsumesFunction.ForStandardAlgorithm())
              .positionFactory(positionFactory));
        break;
      case TRANSPOSITION:
        positionTransitionFactory =
          new PositionTransitionFactory.ForTranspositionPositions();
        positionFactory = new PositionFactory.ForXPositions();
        stateTransitionFactory
          .comparator(new XPositionComparator())
          .positionTransitionFactory(positionTransitionFactory)
          .merge(new MergeFunction.ForXPositions()
              .positionFactory(positionFactory))
          .unsubsume(new UnsubsumeFunction.ForXPositions()
              .subsumes(new SubsumesFunction.ForTransposition())
              .positionFactory(positionFactory));
        break;
      case MERGE_AND_SPLIT:
        positionTransitionFactory =
          new PositionTransitionFactory.ForMergeAndSplitPositions();
        positionFactory = new PositionFactory.ForXPositions();
        stateTransitionFactory
          .comparator(new XPositionComparator())
          .positionTransitionFactory(positionTransitionFactory)
          .merge(new MergeFunction.ForXPositions()
              .positionFactory(positionFactory))
          .unsubsume(new UnsubsumeFunction.ForXPositions()
              .subsumes(new SubsumesFunction.ForMergeAndSplit())
              .positionFactory(positionFactory));
        break;
      default:
        throw new IllegalArgumentException(UNSUPPORTED_ALGORITHM + algorithm);
    }

    positionTransitionFactory
      .stateFactory(stateFactory)
      .positionFactory(positionFactory);

    return stateTransitionFactory;
  }

  /**
   * Restricts the number of spelling candidates for a query.  This class is
   * only intended as a temporary shim until I remove
   * {@link TransducerBuilder#maxCandidates} from the next API.
   * @param <CandidateType> Kind of spelling candidates returned.
   * @author Dylon Edwards
   * @since 2.1.2
   */
  @Deprecated
  @EqualsAndHashCode(callSuper = false)
  private static class DeprecatedTransducerForLimitingNumberOfCandidates<CandidateType>
      extends Transducer<DawgNode, CandidateType> {

    private static final long serialVersionUID = 1L;

    /**
     * Number of elements to take from the spelling candidates.
     */
    private final int elementsToTake;

    /**
     * Searches the dictionary automaton for spelling candidates.
     */
    @NonNull private final ITransducer<CandidateType> transducer;

    /**
     * Constructs a new transducer that limits the number of spelling candidates
     * it returns.
     * @param elementsToTake Limit of spelling candidates to return.
     * @param transducer Generates the spelling candidates which are limited.
     */
    @SuppressWarnings("unchecked")
    DeprecatedTransducerForLimitingNumberOfCandidates(
        final int elementsToTake,
        final ITransducer<CandidateType> transducer) {
      super(((Transducer<DawgNode, CandidateType>) transducer).attributes());
      this.transducer = transducer;
      this.elementsToTake = elementsToTake;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICandidateCollection<CandidateType> transduce(final String term) {
      return limit(transducer.transduce(term));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICandidateCollection<CandidateType> transduce(
        final String term,
        final int maxCandidates) {
      return limit(transducer.transduce(term, maxCandidates));
    }

    /**
     * Returns an {@link ICandidateCollection} that only returns the first
     * {@link #elementsToTake} elements from {@code candidates}.
     * @param candidates Spelling candidates for some query.
     * @return {@link ICandidateCollection} that only returns the first
     * {@link #elementsToTake} candidates.
     */
    private ICandidateCollection<CandidateType> limit(
        final ICandidateCollection<CandidateType> candidates) {
      return new ICandidateCollection<CandidateType>() {
        @Override public Iterator<CandidateType> iterator() {
          return new TakeIterator<>(elementsToTake, candidates.iterator());
        }
      };
    }
  }
}
