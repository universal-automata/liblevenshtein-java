package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Transitions Levenshtein states according to the evidence.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IStateTransitionFunction {

  /**
   * Returns the state consisting of all the possible position-transitions from
   * the current state, given the characteristic vector.
   * @param state Source state to transition from.
   * @param characteristicVector Relevant subwords denoting whether the indices
   * of the current, spelling candidate from the dictionary has characters
   * matching the one being sought from the query term.
   * @return Next state, consisting of all possible position-transitions from
   * the source {@code state}, conditioned on {@code characteristicVector}.
   */
  IState of(IState state, boolean[] characteristicVector);
}
