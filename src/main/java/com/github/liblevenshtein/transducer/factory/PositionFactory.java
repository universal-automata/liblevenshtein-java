package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;

import lombok.NoArgsConstructor;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.SpecialPosition;

/**
 * Builds position vectors for the given algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@NoArgsConstructor
public class PositionFactory implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds a dummy position.
   * @return Dummy position.
   */
  public Position build() {
    return new Position(-1, -1);
  }

  /**
   * Builds a position vector for the standard, Levenshtein algorihtm.
   * @param termIndex Current index of the spelling candidate.
   * @param numErrors Number of accumulated errors at index {@code termIndex}.
   * @return New position vector having index {@code termIndex} and error
   *   {@code numErrors}.
   */
  public Position build(final int termIndex, final int numErrors) {
    return new Position(termIndex, numErrors);
  }

  /**
   * Builds a position vector for the transposition and merge-and-split,
   * Levenshtein algorihtms.
   * @param termIndex Current index of the spelling candidate.
   * @param numErrors Number of accumulated errors at index {@code termIndex}.
   * @param isSpecial Either {@code 1} or {@code 0}, depending on whether the
   *   position is a special case (numErrors.g. a transposition position).
   * @return New position vector having index {@code termIndex}, error
   *   {@code numErrors}, and special marker {@code isSpecial}.
   */
  public Position build(final int termIndex, final int numErrors, final boolean isSpecial) {
    if (isSpecial) {
      return new SpecialPosition(termIndex, numErrors);
    }

    return new Position(termIndex, numErrors);
  }
}
