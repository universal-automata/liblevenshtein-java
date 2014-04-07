package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IState;

public interface IStateFactory {

  IState build(int[]... positions);

  void recycle(IState state);
}
