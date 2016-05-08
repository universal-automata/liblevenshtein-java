package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.State;

/**
 * Builds Levenshtein states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StateFactory implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds a new, Levenshtein state with the given position vectors.
   * @param positions Array of position vectors to link into the state.
   * @return New state having the position vectors.
   */
  public State build(final Position... positions) {
    final State state = new State();

    Position prev = null;
    for (final Position curr : positions) {
      state.insertAfter(prev, curr);
      prev = curr;
    }

    return state;
  }
}
