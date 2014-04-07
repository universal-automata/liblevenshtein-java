package com.github.dylon.liblevenshtein.levenshtein;

import java.util.List;

public interface IPositionTransitionFunction {

	List<int[]> of(int[] position, boolean[] characteristicVector, int offset);

	IPositionTransitionFunction maxEditDistance(int n);
}
