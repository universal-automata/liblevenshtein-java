package com.github.dylon.liblevenshtein.levenshtein;

public class StandardPositionMergeFunction extends AbstractMergeFunction {

  @Override
  public void into(final IState state, final IState positions) {
    for (int m = 0; m < positions.size(); ++m) {
      final int[] a = positions.getOuter(m);
      final int i = a[0];
      final int e = a[1];

      int n = 0;
      while (n < state.size()) {
        final int[] b = state.getOuter(n);
        final int j = b[0];
        final int f = b[1];

        if (e < f || e == f && i < j) {
          n += 1;
        }
        else {
          break;
        }
      }

      if (n < state.size()) {
        final int[] b = state.getOuter(n);
        final int j = b[0];
        final int f = b[1];

        if (j != i || f != e) {
          state.insert(n, a);
        }
        else {
          positionFactory.recycle(a);
        }
      }
      else {
        state.add(a);
      }
    }
  }
}
