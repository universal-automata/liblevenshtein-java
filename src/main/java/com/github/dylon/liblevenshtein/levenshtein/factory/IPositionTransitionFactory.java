package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;

/**
 * Returns functions that transition positions to all possible positions, given
 * the evidence.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IPositionTransitionFactory extends Serializable {

	static final long serialVersionUID = 1L;

  /**
   * Builds a new position-transition function.
   * @return New position-transition function.
   */
  IPositionTransitionFunction build();

  /**
   * Recycles a position-transition function for re-use.
   * @param transition {@link IPositionTransitionFunction} to recycle.  You must
   * discard your reference to {@code transition} once it's been recycled.
   */
  default void recycle(IPositionTransitionFunction transition) {
    // default behavior is to do nothing
  }
}
