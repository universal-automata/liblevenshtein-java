package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sorts elements for transposition and merge-and-split states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class XPositionComparator implements Serializable, Comparator<int[]> {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  public int compare(final int[] a, final int[] b) {
    int c = a[0] - b[0];
    if (0 != c) {
      return c;
    }
    c = a[1] - b[1];
    if (0 != c) {
      return c;
    }
    return a[2] - b[2];
  }
}
