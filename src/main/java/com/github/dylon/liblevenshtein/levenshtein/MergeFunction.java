package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;

import lombok.Setter;

import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionFactory;

/**
 * Merges one state into another, according to rules specific to the Levenshtein
 * algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class MergeFunction implements IMergeFunction, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * -- Setter --
   * Builds and recycles position vectors.
   * @param positionFactory Builds and recycles position vectors.
   * @return This {@link MergeFunction} for fluency.
   */
  @Setter
  protected IPositionFactory positionFactory;

  /**
   * {@link MergeFunction} specific to the standard, Levenshtein algorithm.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForStandardPositions extends MergeFunction {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
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
        }
        else {
          state.add(a);
        }
      }
    }
  }

  /**
   * {@link MergeFunction} specific to the transposition and merge-and-split,
   * Levenshtein algorithm.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForXPositions extends MergeFunction {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
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
        }
        else {
          state.add(a);
        }
      }
    }
  }
}
