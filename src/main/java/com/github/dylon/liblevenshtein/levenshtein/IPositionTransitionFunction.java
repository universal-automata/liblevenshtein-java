package com.github.dylon.liblevenshtein.levenshtein;

public interface IPositionTransitionFunction {

  IState of(int n, int[] position, boolean[] characteristicVector, int offset);
}
