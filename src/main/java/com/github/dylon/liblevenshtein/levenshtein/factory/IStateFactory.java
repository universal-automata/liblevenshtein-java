package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IState;

/**
 * Builds Levenshtein states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IStateFactory {

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
  default void recycle(IState state) {
  	// default behavior is to do nothing
  }
}
