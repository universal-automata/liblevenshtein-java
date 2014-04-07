package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionTransitionFunction;

public class StandardPositionTransitionFactory
  extends AbstractPositionTransitionFactory {

  @Override
  protected IPositionTransitionFunction create() {
    return new StandardPositionTransitionFunction();
  }
}
