package com.github.dylon.liblevenshtein.collection;

/**
 * Deterministically-transitions between states according to some input.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ITransitionFunction<State> {

  /**
   * Determines the next state given the current one and some input.
   * @param current The active state of an automaton
   * @param label Input used to determine the next state
   * @return The next state of the automaton
   */
  State transition(State current, char label);
}
