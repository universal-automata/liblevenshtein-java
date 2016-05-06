package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;

import com.github.liblevenshtein.transducer.IPositionTransitionFunction;

/**
 * Returns functions that transition positions to all possible positions, given
 * the evidence.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IPositionTransitionFactory extends Serializable {

  long serialVersionUID = 1L;

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
  default void recycle(final IPositionTransitionFunction transition) {
    // default behavior is to do nothing
  }
}
