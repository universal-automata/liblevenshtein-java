package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;

public interface IPositionTransitionFactory {

  IPositionTransitionFunction build();

  void recycle(IPositionTransitionFunction transition);
}
