package com.github.dylon.liblevenshtein.levenshtein;

public class StandardSubsumesFunction
  extends AbstractSubsumesFunction {

  @Override
  public boolean at(
      final int i, final int e,
      final int j, final int f) {

    return ((i < j) ? (j - i) : (i -j)) <= (f - e);
  }
}
