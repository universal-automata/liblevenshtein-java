package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Comparator;

import lombok.Setter;

import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionTransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IStateFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StateTransitionFunction implements IStateTransitionFunction {

  @Setter private Comparator<int[]> comparator;

  @Setter private IStateFactory stateFactory;

  @Setter private IPositionTransitionFactory transitionFactory;

  @Setter private IMergeFunction merge;

  @Setter private IUnsubsumeFunction unsubsume;

  @Setter private int maxDistance;

  /**
   * {@inheritDoc}
   */
  @Override
  public IState of(
      final IState currState,
      final boolean[] characteristicVector) {

    final IPositionTransitionFunction transition = transitionFactory.build();

    try {
      final int offset = currState.getOuter(0)[0];
      final IState nextState = stateFactory.build();
      final int n = maxDistance;

      for (int m = 0; m < currState.size(); ++m) {
        final IState positions =
          transition.of(n, currState.getOuter(m), characteristicVector, offset);

        if (null == positions) {
          continue;
        }

        merge.into(nextState, positions);
        stateFactory.recycle(positions);
      }

      unsubsume.at(nextState);

      if (nextState.size() > 0) {
        nextState.sort(comparator);
        return nextState;
      }

      stateFactory.recycle(nextState);
      return null;
    }
    finally {
      transitionFactory.recycle(transition);
    }
  }
}
