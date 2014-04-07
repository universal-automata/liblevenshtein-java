package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.TranspositionPositionTransitionFunction;

public class TranspositionPositionTransitionFactory
  extends AbstractPositionTransitionFactory {

  @Override
  protected IPositionTransitionFunction create() {
    return new TranspositionPositionTransitionFunction();
  }
}
