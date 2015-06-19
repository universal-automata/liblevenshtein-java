package com.github.dylon.liblevenshtein.levenshtein;

public abstract class SubsumesFunction implements ISubsumesFunction {

  @Override
  public boolean at(
      final int i, final int e,
      final int j, final int f) {

    throw new UnsupportedOperationException("at(i,e, j,f) is not supported");
  }

  @Override
  public boolean at(
      final int i, final int e, final int s,
      final int j, final int f, final int t,
      final int n) {

    throw new UnsupportedOperationException(
        "at(i,e,s, j,f,t, n) is not supported");
  }

  public static class ForStandardAlgorithm extends SubsumesFunction {

    @Override
    public boolean at(
        final int i, final int e,
        final int j, final int f) {

      return ((i < j) ? (j - i) : (i -j)) <= (f - e);
    }
  }

  public static class ForTransposition extends SubsumesFunction {

    @Override
    public boolean at(
        final int i, final int e, final int s,
        final int j, final int f, final int t,
        final int n) {

      if (s == 1) {
        if (t == 1) {
          return (i == j);
        }

        return (f == n) && (i == j);
      }

      if (t == 1) {
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
        return ((j < i) ? (i - j - 1) : (j - i + 1)) <= (f - e);
      }

      return ((i < j) ? (j - i) : (i - j)) <= (f - e);
    }
  }

  public static class ForMergeAndSplit extends SubsumesFunction {

    @Override
    public boolean at(
        final int i, final int e, final int s,
        final int j, final int f, final int t,
        final int n) {

      if (s == 1 && t == 0) {
        return false;
      }

      return ((i < j) ? (j - i) : (i - j)) <= (f - e);
    }
  }
}
