package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Comparator;

public class StandardPositionComparator implements Comparator<int[]> {

  @Override
  public int compare(final int[] a, final int[] b) {
    int c = a[0] - b[0];
    if (0 != c) return c;
    return a[1] - b[1];
  }
}
