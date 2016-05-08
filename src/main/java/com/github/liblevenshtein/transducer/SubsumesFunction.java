package com.github.liblevenshtein.transducer;

import java.io.Serializable;

/**
 * Routines for determining whether one position subsumes another.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class SubsumesFunction implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Determines whether {@code lhs} subsumes {@code rhs}.
   * @param lhs {@link Position} doing the subsumption.
   * @param rhs {@link Position} being subsumed.
   * @param n Length of the query term.
   * @return {@code lhs} subsuem {@code rhs}.
   */
  public abstract boolean at(Position lhs, Position rhs, int n);

  /**
   * Routines for determining whether a standard position subsumes another.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForStandardAlgorithm extends SubsumesFunction {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean at(final Position lhs, final Position rhs, final int n) {
      final int i = lhs.termIndex();
      final int e = lhs.numErrors();
      final int j = rhs.termIndex();
      final int f = rhs.numErrors();
      return (i < j ? j - i : i - j) <= (f - e);
    }
  }

  /**
   * Routines for determining whether a transposition position subsumes another.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForTransposition extends SubsumesFunction {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean at(final Position lhs, final Position rhs, final int n) {
      final int i = lhs.termIndex();
      final int e = lhs.numErrors();
      final boolean s = lhs.isSpecial();
      final int j = rhs.termIndex();
      final int f = rhs.numErrors();
      final boolean t = rhs.isSpecial();

      if (s) {
        if (t) {
          return i == j;
        }

        return f == n && i == j;
      }

      if (t) {
        // We have two cases:
        //
        // Case 1: (j < i) => (j - i) = - (i - j)
        //                 => |j - (i - 1)| = |j - i + 1|
        //                                  = |-(i - j) + 1|
        //                                  = |-(i - j - 1)|
        //                                  = i - j - 1
        //
        // Case 1 holds, because i and j are integers, and j < i implies i is at
        // least 1 unit greater than j, further implying that i - j - 1 is
        // non-negative.
        //
        // Case 2: (j >= i) => |j - (i - 1)| = |j - i + 1| = j - i + 1
        //
        // Case 2 holds for the same reason case 1 does, in that j - i >= 0, and
        // adding 1 to the difference will only strengthen its non-negativity.
        //
        //return Math.abs(j - (i - 1)) <= (f - e);
        return (j < i ? i - j - 1 : j - i + 1) <= (f - e);
      }

      return (i < j ? j - i : i - j) <= (f - e);
    }
  }

  /**
   * Routines for determining whether a merge-and-split position subsumes another.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForMergeAndSplit extends SubsumesFunction {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean at(final Position lhs, final Position rhs, final int n) {
      final int i = lhs.termIndex();
      final int e = lhs.numErrors();
      final boolean s = lhs.isSpecial();
      final int j = rhs.termIndex();
      final int f = rhs.numErrors();
      final boolean t = rhs.isSpecial();

      if (s && !t) {
        return false;
      }

      return (i < j ? j - i : i - j) <= (f - e);
    }
  }
}
