package com.github.dylon.liblevenshtein.levenshtein.factory;

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
import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.PrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.TransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.DistanceComparator;
import com.github.dylon.liblevenshtein.levenshtein.IDistanceFunction;
import com.github.dylon.liblevenshtein.levenshtein.IState;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;
import com.github.dylon.liblevenshtein.levenshtein.Match;
import com.github.dylon.liblevenshtein.levenshtein.MergeFunction;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionComparator;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionDistanceFunction;
import com.github.dylon.liblevenshtein.levenshtein.SubsumesFunction;
import com.github.dylon.liblevenshtein.levenshtein.Transducer;
import com.github.dylon.liblevenshtein.levenshtein.UnsubsumeFunction;
import com.github.dylon.liblevenshtein.levenshtein.XPositionComparator;
import com.github.dylon.liblevenshtein.levenshtein.XPositionDistanceFunction;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class TransducerBuilder implements ITransducerBuilder {

  final IPrefixFactory<DawgNode> prefixFactory = new PrefixFactory<>();

  final IDawgFactory<DawgNode, AbstractDawg> dawgFactory = new DawgFactory()
    .dawgNodeFactory(new DawgNodeFactory())
    .prefixFactory(prefixFactory)
    .transitionFactory(new TransitionFactory<DawgNode>());

  AbstractDawg dictionary;

  @NonNull
  @Setter(onMethod=@_({@Override}))
  Algorithm algorithm = Algorithm.STANDARD;

  @Setter(onMethod=@_({@Override}))
  DistanceComparator nearestCandidatesComparator = null;

  @Setter(onMethod=@_({@Override}))
  boolean caseInsensitiveSort = true;

  @Setter(onMethod=@_({@Override}))
  int defaultMaxDistance = Integer.MAX_VALUE;

  @NonNull
  @Setter(onMethod=@_({@Override}))
  Match strategy = Match.TERM;

  @Setter(onMethod=@_({@Override}))
  boolean includeDistance = true;

  @Setter(onMethod=@_({@Override}))
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
  public ITransducer<?> build() {
    final IStateFactory stateFactory =
      new StateFactory().elementFactory(new ElementFactory<int[]>());

    return buildTransducer()
        .defaultMaxDistance(defaultMaxDistance)
        .stateTransitionFactory(buildStateTransitionFactory(stateFactory))
        .candidatesBuilder(includeDistance
            ? new CandidateCollectionBuilder
              .WithDistance()
                .maxCandidates(maxCandidates)
            : new CandidateCollectionBuilder
              .WithoutDistance()
                .maxCandidates(maxCandidates))
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

  protected <CandidateType> Transducer<DawgNode, CandidateType> buildTransducer() {
    switch (strategy) {
      case TERM:
        return new Transducer.OnTerms<DawgNode, CandidateType>();
      case PREFIX:
        return new Transducer
          .OnPrefixes<DawgNode, CandidateType>()
            .prefixFactory(prefixFactory);
      default:
        throw new IllegalArgumentException("Unsupported Match strategy: " + strategy);
    }
  }

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

  protected DistanceComparator buildNearestCandidatesComparator() {
    if (null != nearestCandidatesComparator) {
      return nearestCandidatesComparator;
    }

    if (caseInsensitiveSort) {
      return new DistanceComparator.WithCaseInsensitiveSort();
    }

    return new DistanceComparator.WithCaseSensitiveSort();
  }
}
