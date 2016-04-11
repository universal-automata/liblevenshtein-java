package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sorts position vectors for the standard, Levenshtein algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StandardPositionComparator implements Serializable, Comparator<int[]> {

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
    return a[1] - b[1];
  }
}
