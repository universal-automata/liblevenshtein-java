package com.github.liblevenshtein.transducer;

import java.io.Serializable;
import java.util.Comparator;

import lombok.Setter;

import com.github.liblevenshtein.transducer.factory.PositionTransitionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

/**
 * Transitions one state to another, given a set of inputs.  This function
 * doesn't need to know about the Levenshtein algorithm, as the
 * algorithm-specific components are injected via setters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Setter
public class StateTransitionFunction implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Sorts {@link State} elements in an unsubsumption-friendly fashion.
   */
  private Comparator<Position> comparator;

  /**
   * Builds and recycles {@link State} instances.
   */
  private StateFactory stateFactory;

  /**
   * Builds position vector, transition functions according to the Levenshtein
   * algorithm.
   */
  private PositionTransitionFactory transitionFactory;

  /**
   * Merges states together according to the Levenshtein algorithm.
   */
  private MergeFunction merge;

  /**
   * Removes positions from a state that are subsumed by other positions in that
   * state.
   */
  private UnsubsumeFunction unsubsume;

  /**
   * Max number of errors tolerated in spelling candidates, from the query term.
   */
  private int maxDistance;

  /**
   * Length of the query term.
   */
  private int queryLength;

  /**
   * Returns the state consisting of all the possible position-transitions from
   * the current state, given the characteristic vector.
   * @param currState Source state to transition from.
   * @param characteristicVector Relevant subwords denoting whether the indices
   *   of the current, spelling candidate from the dictionary has characters
   *   matching the one being sought from the query term.
   * @return Next state, consisting of all possible position-transitions from
   *   the source {@code currState}, conditioned on {@code characteristicVector}.
   */
  public State of(
      final State currState,
      final boolean[] characteristicVector) {

    final PositionTransitionFunction transition = transitionFactory.build();
    final int offset = currState.head().termIndex();
    final State nextState = stateFactory.build();
    final int n = maxDistance;

    for (final Position position : currState) {
      final State positions =
        transition.of(n, position, characteristicVector, offset);

      if (null == positions) {
        continue;
      }

      merge.into(nextState, positions);
    }

    unsubsume.at(nextState, queryLength);

    if (null != nextState.head()) {
      nextState.sort(comparator);
      return nextState;
    }

    return null;
  }
}
