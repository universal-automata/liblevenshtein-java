package com.github.dylon.liblevenshtein.collection;

import it.unimi.dsi.fastutil.chars.CharIterator;

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
  State of(State current, char label);

	/**
	 * Returns the labels of the outgoing edges from the node.
   * @param current The active state of an automaton
   * @return The labels of the outgoing edges from the node.
	 */
  CharIterator of(State current);
}
