package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;

import com.github.dylon.liblevenshtein.levenshtein.IStateTransitionFunction;

/**
 * Builds functions that transition states to other states, given some evidence.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IStateTransitionFactory extends Serializable {

	static final long serialVersionUID = 1L;

  /**
   * Builds a new state-transition function that only considers spelling
   * candidates no more than {@code maxDistance} errors from the query term.
   * @param maxDistance Maximum number of errors tolerated in transforming the
   * query term into the spelling candidate.
   * @return New state-transition function that only considers spelling
   * candidates within {@code maxDistance} errors from the query term.
   */
  IStateTransitionFunction build(int maxDistance);

  /**
   * Recycles a state-transition function so it may be re-used in
   * {@link #build(int) build}.
   * @param transition {@link IStateTransitionFunction} to recycle.  You must
   * discard your reference to {@code transition} once you've recycled it.
   */
  default void recycle(IStateTransitionFunction transition) {
    // default behavior is to do nothing
  }
}
