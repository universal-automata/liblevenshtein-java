package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;

import com.github.liblevenshtein.transducer.IState;

/**
 * Builds Levenshtein states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IStateFactory extends Serializable {

  long serialVersionUID = 1L;

  /**
   * Builds a new, Levenshtein state with the given position vectors.
   * @param positions Array of position vectors to link into the state.
   * @return New state having the position vectors.
   */
  IState build(int[]... positions);

  /**
   * Recycles a state for re-use.
   * @param state {@link IState} to recycle.  You must discard the state once
   * you've recycled it.
   */
  default void recycle(final IState state) {
    // default behavior is to do nothing
  }
}
