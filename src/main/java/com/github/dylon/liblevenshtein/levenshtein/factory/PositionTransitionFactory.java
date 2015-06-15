package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.MergeAndSplitPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.TranspositionPositionTransitionFunction;

@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class PositionTransitionFactory implements IPositionTransitionFactory {

  final Queue<IPositionTransitionFunction> transitions = new ArrayDeque<>();

  @Setter IStateFactory stateFactory;

  @Setter IPositionFactory positionFactory;

  @Override
  public IPositionTransitionFunction build() {
    IPositionTransitionFunction transition = transitions.poll();

    if (null == transition) {
      transition = create();
    }

    return transition;
  }

  protected abstract IPositionTransitionFunction create();

  @Override
  public void recycle(final IPositionTransitionFunction transition) {
    transitions.offer(transition);
  }

  public static class ForStandardPositions extends PositionTransitionFactory {

    @Override
    protected IPositionTransitionFunction create() {
      return new StandardPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }

  public static class ForTranspositionPositions extends PositionTransitionFactory {

    @Override
    protected IPositionTransitionFunction create() {
      return new TranspositionPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }

  public static class ForMergeAndSplitPositions extends PositionTransitionFactory {

    @Override
    protected IPositionTransitionFunction create() {
      return new MergeAndSplitPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }
}
