package com.github.liblevenshtein.transducer;

import java.io.Serializable;

/**
 * Un-subsumes the positions in a state, according to the Levenshtein algorithm
 * in use.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IUnsubsumeFunction extends Serializable {

  /**
   * Removes all the positions from {@code state} that are subsumed by other
   * position.
   * @param state State whose positions should be un-subsumed.
   */
  void at(IState state);
}
