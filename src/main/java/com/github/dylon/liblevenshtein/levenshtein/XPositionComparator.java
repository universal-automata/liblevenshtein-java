package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Comparator;

public class XPositionComparator implements Comparator<int[]> {

  @Override
  public int compare(final int[] a, final int[] b) {
    int c = a[0] - b[0];
    if (0 != c) return c;
    c = a[1] - b[1];
    if (0 != c) return c;
    return a[2] - b[2];
  }
}
