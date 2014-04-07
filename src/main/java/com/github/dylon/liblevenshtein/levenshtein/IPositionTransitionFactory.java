package com.github.dylon.liblevenshtein.levenshtein;

public interface IPositionTransitionFactory {

	IPositionTransitionFunction build(int n);

	IPositionTransitionFunction build();

	void recycle(IPositionTransitionFunction transition);
}
