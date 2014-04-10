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
        return ((i < j) ? (j - i) : (i - j)) + 1 <= (f - e);
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
