package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;

/**
 * Builds position vectors for a specific, Levenshtein algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IPositionFactory extends Serializable {

  long serialVersionUID = 1L;

  /**
   * Builds a position vector for the standard, Levenshtein algorihtm.
   * @param i Current index of the spelling candidate.
   * @param e Number of accumulated errors at index {@code i}.
   * @return New position vector having index {@code i} and error {@code e}.
   */
  default int[] build(final int i, final int e) {
    throw new UnsupportedOperationException("build(i,e) is not implemented");
  }

  /**
   * Builds a position vector for the transposition and merge-and-split,
   * Levenshtein algorihtms.
   * @param i Current index of the spelling candidate.
   * @param e Number of accumulated errors at index {@code i}.
   * @param x Either {@code 1} or {@code 0}, depending on whether the position
   * is a special case (e.g. a transposition position).
   * @return New position vector having index {@code i}, error {@code e}, and
   * special marker {@code x}.
   */
  default int[] build(final int i, final int e, final int x) {
    throw new UnsupportedOperationException("build(i,e,x) is not implemented");
  }

  /**
   * Recycles a position vector for re-use.
   * @param position Position vector to recycle.
   */
  default void recycle(final int[] position) {
    // default behavior is to do nothing
  }
}
