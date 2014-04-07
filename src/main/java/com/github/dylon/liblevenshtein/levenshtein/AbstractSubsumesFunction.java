package com.github.dylon.liblevenshtein.levenshtein;

public abstract class AbstractSubsumesFunction
  implements ISubsumesFunction {

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
}
