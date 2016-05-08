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
   * -- SETTER --
   * Sorts {@link State} elements in an unsubsumption-friendly fashion.
   * @param comparator Sorts {@link State} elements in an unsubsumption-friendly fashion.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  private Comparator<Position> comparator;

  /**
   * Builds and recycles {@link State} instances.
   * -- SETTER --
   * Builds and recycles {@link State} instances.
   * @param stateFactory Builds and recycles {@link State} instances.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  private StateFactory stateFactory;

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
  private PositionTransitionFactory transitionFactory;

  /**
   * Merges states together according to the Levenshtein algorithm.
   * -- SETTER --
   * Merges states together according to the Levenshtein algorithm.
   * @param merge Merges states together according to the Levenshtein algorithm.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  private MergeFunction merge;

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
  private UnsubsumeFunction unsubsume;

  /**
   * Max number of errors tolerated in spelling candidates, from the query term.
   * -- SETTER --
   * Max number of errors tolerated in spelling candidates, from the query term.
   * @param maxDistance Max number of errors tolerated in spelling candidates,
   * from the query term.
   * @return This {@link StateTransitionFunction} for fluency.
   */
  private int maxDistance;

  /**
   * @param queryLength Length of the query term.
   */
  private int queryLength;

  /**
   * {@inheritDoc}
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
