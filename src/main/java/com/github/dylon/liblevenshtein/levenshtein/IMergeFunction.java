package com.github.dylon.liblevenshtein.levenshtein;

public interface IMergeFunction {

  void into(IState state, IState positions);
}
