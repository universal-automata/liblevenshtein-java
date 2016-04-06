package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.Collection;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.val;

import com.github.dylon.liblevenshtein.collection.TakeIterator;
import com.github.dylon.liblevenshtein.collection.dawg.AbstractDawg;
import com.github.dylon.liblevenshtein.collection.dawg.DawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IDawgFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.PrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.PrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.TransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.ICandidateCollection;
import com.github.dylon.liblevenshtein.levenshtein.IDistanceFunction;
import com.github.dylon.liblevenshtein.levenshtein.IState;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;
import com.github.dylon.liblevenshtein.levenshtein.MergeFunction;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionComparator;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionDistanceFunction;
import com.github.dylon.liblevenshtein.levenshtein.SubsumesFunction;
import com.github.dylon.liblevenshtein.levenshtein.Transducer;
import com.github.dylon.liblevenshtein.levenshtein.TransducerAttributes;
import com.github.dylon.liblevenshtein.levenshtein.UnsubsumeFunction;
import com.github.dylon.liblevenshtein.levenshtein.XPositionComparator;
import com.github.dylon.liblevenshtein.levenshtein.XPositionDistanceFunction;

/**
 * Fluently-builds Levenshtein transducers.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class TransducerBuilder implements ITransducerBuilder {

  /**
   * Builds and recycles {@link DawgNode} {@link Prefix}es.
   */
  final IPrefixFactory<DawgNode> prefixFactory = new PrefixFactory<>();

  /**
   * Builds DAWG collections from dictionaries.
   */
  final IDawgFactory<DawgNode, AbstractDawg> dawgFactory = new DawgFactory()
    .dawgNodeFactory(new DawgNodeFactory())
    .prefixFactory(prefixFactory)
    .transitionFactory(new TransitionFactory<DawgNode>());

  /**
   * Dictionary automaton for seeking spelling candidates.
   */
  AbstractDawg dictionary;

  /**
   * Desired Levenshtein algorithm for searching.
   * -- SETTER --
   * Desired Levenshtein algorithm for searching.
   * @param algorithm Desired Levenshtein algorithm for searching.
   * @return This {@link TransducerBuilder} for fluency.
   */
  @NonNull
  @Setter(onMethod=@_({@Override}))
  Algorithm algorithm = Algorithm.STANDARD;

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
  @Setter(onMethod=@_({@Override}))
  int defaultMaxDistance = Integer.MAX_VALUE;

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
  @Setter(onMethod=@_({@Override}))
  boolean includeDistance = true;

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
  @Setter(onMethod=@_({@Override, @Deprecated}))
  int maxCandidates = Integer.MAX_VALUE;

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

    if (dictionary instanceof AbstractDawg) {
      this.dictionary = (AbstractDawg) dictionary;
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
      	.dictionaryRoot(dictionary.root());

    final Transducer<DawgNode, CandidateType> transducer =
    	new Transducer<DawgNode, CandidateType>(attributes);

    if (maxCandidates == Integer.MAX_VALUE) {
      return transducer;
    }

    return new DeprecatedTransducerForLimitingNumberOfCandidates<CandidateType>(
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
        throw new IllegalArgumentException("Unsupported Algorithm: " + algorithm);
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
        return stateFactory.build(new int[] {0,0});
      case TRANSPOSITION: // fall through
      case MERGE_AND_SPLIT:
        return stateFactory.build(new int[] {0,0,0});
      default:
        throw new IllegalArgumentException("Unsupported Algorithm: " + algorithm);
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
        throw new IllegalArgumentException("Unsupported Algorithm: " + algorithm);
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
  @RequiredArgsConstructor
  private static class DeprecatedTransducerForLimitingNumberOfCandidates<CandidateType>
      implements ITransducer<CandidateType> {

    /**
     * Number of elements to take from the spelling candidates.
     */
    private final int elementsToTake;

    /**
     * Searches the dictionary automaton for spelling candidates.
     */
    @NonNull private final ITransducer<CandidateType> transducer;

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
          return new TakeIterator<CandidateType>(elementsToTake, candidates.iterator());
        }
      };
    }
  }
}
