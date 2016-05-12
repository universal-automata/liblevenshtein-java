package com.github.liblevenshtein.transducer;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sorts position vectors for the standard, Levenshtein algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StandardPositionComparator implements Serializable, Comparator<Position> {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  public int compare(final Position lhs, final Position rhs) {
    final int c = lhs.termIndex() - rhs.termIndex();
    if (0 != c) {
      return c;
    }
    return lhs.numErrors() - rhs.numErrors();
  }
}
