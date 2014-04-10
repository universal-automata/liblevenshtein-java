package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Collection;
import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.collection.dawg.AbstractDawg;
import com.github.dylon.liblevenshtein.collection.dawg.DawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.IDawg;
import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IDawgFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.PrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.TransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IStateFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IStateTransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IntersectionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.NearestCandidatesFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionTransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateTransitionFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Builder implements IBuilder {

  final IDawgFactory<DawgNode, AbstractDawg> dawgFactory = new DawgFactory()
    .dawgNodeFactory(new DawgNodeFactory())
    .prefixFactory(new PrefixFactory<DawgNode>())
    .transitionFactory(new TransitionFactory<DawgNode>());

  AbstractDawg dictionary;

  @Setter(onMethod=@_({@Override}))
  Algorithm algorithm;

  @Setter(onMethod=@_({@Override}))
  DistanceComparator nearestCandidatesComparator = null;

  @Setter(onMethod=@_({@Override}))
  boolean caseInsensitiveSort = true;

  @Setter(onMethod=@_({@Override}))
  int defaultMaxDistance = Integer.MAX_VALUE;

  @Setter(onMethod=@_({@Override}))
  Match strategy = Match.TERM;

  @Setter(onMethod=@_({@Override}))
  boolean includeDistance = true;

  /**
   * {@inheritDoc}
   */
  @Override
  public IBuilder dictionary(Collection<String> dictionary) {
    return dictionary(dictionary, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBuilder dictionary(Collection<String> dictionary, boolean isSorted) {
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
  public ITransducer build() {
    final IStateFactory stateFactory =
      new StateFactory().elementFactory(new ElementFactory<int[]>());

    return buildTransducer()
      .defaultMaxDistance(defaultMaxDistance)
      .stateTransitionFactory(buildStateTransitionFactory(stateFactory))
      .candidatesBuilder(buildCandidatesBuilder())
      .nearestCandidatesFactory(
          new NearestCandidatesFactory<DawgNode>()
            .comparator(buildNearestCandidatesComparator()))
      .intersectionFactory(new IntersectionFactory<DawgNode>())
      .minDistance(buildMinDistance())
      .isFinal(dawgFactory.isFinal(dictionary))
      .dictionaryTransition(dawgFactory.transition(dictionary))
      .initialState(buildInitialState(stateFactory))
      .dictionaryRoot(dictionary.root());
  }

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

  protected Transducer<DawgNode> buildTransducer() {
    switch (strategy) {
      case TERM:
        return new Transducer.OnTerms<DawgNode>();
      case PREFIX:
        return new Transducer.OnPrefixes<DawgNode>();
      default:
        throw new IllegalArgumentException("Unsupported Match strategy: " + strategy);
    }
  }

  protected IStateTransitionFactory buildStateTransitionFactory(
      @NonNull final IStateFactory stateFactory) {

    final StateTransitionFactory stateTransitionFactory =
      new StateTransitionFactory().stateFactory(stateFactory);

    final IPositionFactory positionFactory;

    switch (algorithm) {
      case STANDARD:
        positionFactory = new PositionFactory.ForStandardPositions();
        stateTransitionFactory
          .comparator(new StandardPositionComparator())
          .positionTransitionFactory(
              new PositionTransitionFactory.ForStandardPositions())
          .merge(new MergeFunction.ForStandardPositions()
              .positionFactory(positionFactory))
          .unsubsume(new UnsubsumeFunction.ForStandardPositions()
              .subsumes(new SubsumesFunction.ForStandardAlgorithm())
              .positionFactory(positionFactory));
        break;
      case TRANSPOSITION:
        positionFactory = new PositionFactory.ForXPositions();
        stateTransitionFactory
          .comparator(new XPositionComparator())
          .positionTransitionFactory(
              new PositionTransitionFactory.ForTranspositionPositions())
          .merge(new MergeFunction.ForXPositions()
              .positionFactory(positionFactory))
          .unsubsume(new UnsubsumeFunction.ForXPositions()
              .subsumes(new SubsumesFunction.ForTransposition())
              .positionFactory(positionFactory));
        break;
      case MERGE_AND_SPLIT:
        positionFactory = new PositionFactory.ForXPositions();
        stateTransitionFactory
          .comparator(new XPositionComparator())
          .positionTransitionFactory(
              new PositionTransitionFactory.ForMergeAndSplitPositions())
          .merge(new MergeFunction.ForXPositions()
              .positionFactory(positionFactory))
          .unsubsume(new UnsubsumeFunction.ForXPositions()
              .subsumes(new SubsumesFunction.ForMergeAndSplit())
              .positionFactory(positionFactory));
        break;
      default:
        throw new IllegalArgumentException("Unsupported Algorithm: " + algorithm);
    }

    return stateTransitionFactory;
  }

  protected DistanceComparator buildNearestCandidatesComparator() {
    if (null != nearestCandidatesComparator) {
      return nearestCandidatesComparator;
    }

    if (caseInsensitiveSort) {
      return new DistanceComparator.WithCaseInsensitiveSort();
    }

    return new DistanceComparator.WithCaseSensitiveSort();
  }

  protected ICandidateCollectionBuilder buildCandidatesBuilder() {
    if (includeDistance) {
      return buildCandidatesWithDistanceBuilder();
    }

    return buildCandidatesWithoutDistanceBuilder();
  }

  protected ICandidateCollectionBuilder buildCandidatesWithDistanceBuilder() {
    return new CandidateCollectionBuilder.WithDistance();
  }

  protected ICandidateCollectionBuilder buildCandidatesWithoutDistanceBuilder() {
    return new CandidateCollectionBuilder.WithoutDistance();
  }
}
