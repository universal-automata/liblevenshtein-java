package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Setter;
import lombok.experimental.Accessors;

import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionFactory;

@Accessors(fluent=true)
public abstract class MergeFunction implements IMergeFunction {

  @Setter
  protected IPositionFactory positionFactory;

  public static class ForStandardPositions extends MergeFunction {

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

  public static class ForXPositions extends MergeFunction {

    @Override
    public void into(final IState state, final IState positions) {
      for (int m = 0; m < positions.size(); ++m) {
        final int[] a = positions.getOuter(m);
        final int i = a[0];
        final int e = a[1];
        final int s = a[2];

        int n = 0;
        while (n < state.size()) {
          final int[] b = state.getOuter(n);
          final int j = b[0];
          final int f = b[1];
          final int t = b[2];

          if (e < f || e == f && (i < j || (i == j && s < t))) {
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
          final int t = b[2];

          if (j != i || f != e || t != s) {
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
}
