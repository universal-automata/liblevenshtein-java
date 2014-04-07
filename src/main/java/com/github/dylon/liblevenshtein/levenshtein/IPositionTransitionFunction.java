package com.github.dylon.liblevenshtein.levenshtein;

public interface IPositionTransitionFunction {

	IState of(int[] position, boolean[] characteristicVector, int offset);

	IPositionTransitionFunction maxEditDistance(int n);
}
