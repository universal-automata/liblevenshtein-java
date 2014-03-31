package com.github.dylon.liblevenshtein.collection;

/**
 * Specifies the necessary methods for functors that return whether nodes of
 * some type represent the final states of an automaton.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IFinalFunction<State> {

  /**
   * Whether the state represents the last character of some term.
   * @param current State to check for finality.
   * @return Whether this node represents the last character of some term.
   */
  boolean isFinal(State current);
}
