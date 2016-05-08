package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Merges one state into another, according to rules specific to the Levenshtein
 * algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MergeFunction implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Merges the positions in the latter state into the former, in a
   * subsumption-friendly manner.
   * @param state The state into which the positions of the latter should be
   * merged.
   * @param positions The state from which the positions should be merged.
   */
  public abstract void into(State state, State positions);

  /**
   * Inserts a position after the current one, or at the head of the state if
   * there is no current one.
   * @param state {@link State} to insert an element into.
   * @param curr Current {@link Position} after which to insert another.
   * @param next Next {@link Position} to insert after the current one.
   */
  protected void insertAfter(
      final State state,
      final Position curr,
      final Position next) {
    if (null == curr) {
      state.head(next);
    }
    else {
      state.insertAfter(curr, next);
    }
  }

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
    public void into(final State state, final State positions) {
      for (final Position a : positions) {
        final int i = a.termIndex();
        final int e = a.numErrors();

        final StateIterator iter = state.iterator();

        Position prevB = null;
        while (iter.hasNext()) {
          final Position b = iter.peek();
          final int j = b.termIndex();
          final int f = b.numErrors();

          if (e < f || e == f && i < j) {
            prevB = b;
            iter.next();
          }
          else {
            break;
          }
        }

        if (iter.hasNext()) {
          final Position b = iter.peek();
          final int j = b.termIndex();
          final int f = b.numErrors();

          if (j != i || f != e) {
            insertAfter(state, prevB, a);
          }
        }
        else {
          insertAfter(state, prevB, a);
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
  public static class ForSpecialPositions extends MergeFunction {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void into(final State state, final State positions) {
      for (final Position a : positions) {
        final int i = a.termIndex();
        final int e = a.numErrors();
        final boolean s = a.isSpecial();

        final StateIterator iter = state.iterator();

        Position prevB = null;
        while (iter.hasNext()) {
          final Position b = iter.peek();
          final int j = b.termIndex();
          final int f = b.numErrors();
          final boolean t = b.isSpecial();

          if (e < f || e == f && (i < j || i == j && !s && t)) {
            prevB = b;
            iter.next();
          }
          else {
            break;
          }
        }

        if (iter.hasNext()) {
          final Position b = iter.next();
          final int j = b.termIndex();
          final int f = b.numErrors();
          final boolean t = b.isSpecial();

          if (j != i || f != e || t != s) {
            insertAfter(state, prevB, a);
          }
        }
        else {
          insertAfter(state, prevB, a);
        }
      }
    }
  }
}
