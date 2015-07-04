package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Comparator;

import lombok.Setter;

import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionTransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IStateFactory;

/**
 * Transitions one state to another, given a set of inputs.  This function
 * doesn't need to know about the Levenshtein algorithm, as the
 * algorithm-specific components are injected via setters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StateTransitionFunction implements IStateTransitionFunction {

  /**
   * Sorts {@link IState} elements in an unsubsumption-friendly fashion.
   * -- SETTER --
   * Sorts {@link IState} elements in an unsubsumption-friendly fashion.
   * @param comparator Sorts {@link IState} elements in an unsubsumption-friendly fashion.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  @Setter private Comparator<int[]> comparator;

  /**
   * Builds and recycles {@link IState} instances.
   * -- SETTER --
   * Builds and recycles {@link IState} instances.
   * @param stateFactory Builds and recycles {@link IState} instances.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  @Setter private IStateFactory stateFactory;

  /**
   * Builds position vector, transition functions according to the Levenshtein
   * algorithm.
   * -- SETTER --
   * Builds position vector, transition functions according to the Levenshtein
   * algorithm.
   * @param transitionFactory Builds position vector, transition functions
   * according to the Levenshtein algorithm.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  @Setter private IPositionTransitionFactory transitionFactory;

  /**
   * Merges states together according to the Levenshtein algorithm.
   * -- SETTER --
   * Merges states together according to the Levenshtein algorithm.
   * @param merge Merges states together according to the Levenshtein algorithm.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  @Setter private IMergeFunction merge;

  /**
   * Removes positions from a state that are subsumed by other positions in that
   * state.
   * -- SETTER --
   * Removes positions from a state that are subsumed by other positions in that
   * state.
   * @param unsubsume Removes positions from a state that are subsumed by other
   * positions in that state.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  @Setter private IUnsubsumeFunction unsubsume;

  /**
   * Max number of errors tolerated in spelling candidates, from the query term.
   * -- SETTER --
   * Max number of errors tolerated in spelling candidates, from the query term.
   * @param maxDistance Max number of errors tolerated in spelling candidates,
   * from the query term.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  @Setter private int maxDistance;

  /**
   * {@inheritDoc}
   */
  @Override
  public IState of(
      final IState currState,
      final boolean[] characteristicVector) {

    final IPositionTransitionFunction transition = transitionFactory.build();
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
    }

    unsubsume.at(nextState);

    if (nextState.size() > 0) {
      nextState.sort(comparator);
      return nextState;
    }

    return null;
  }
}
