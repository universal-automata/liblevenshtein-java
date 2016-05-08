package com.github.liblevenshtein.transducer;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sorts elements for transposition and merge-and-split states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class SpecialPositionComparator implements Serializable, Comparator<Position> {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  public int compare(final Position lhs, final Position rhs) {
    int c;

    c = lhs.termIndex() - rhs.termIndex();
    if (0 != c) {
      return c;
    }

    c = lhs.numErrors() - rhs.numErrors();
    if (0 != c) {
      return c;
    }

    if (lhs.isSpecial()) {
      if (rhs.isSpecial()) {
        return 0;
      }

      return 1;
    }

    if (rhs.isSpecial()) {
      return -1;
    }

    return 0;
  }
}
