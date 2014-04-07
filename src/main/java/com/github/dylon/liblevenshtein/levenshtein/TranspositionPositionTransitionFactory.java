package com.github.dylon.liblevenshtein.levenshtein;

public class TranspositionPositionTransitionFactory
	extends AbstractPositionTransitionFactory {

	@Override
	public IPositionTransitionFunction build() {
		return new TranspositionPositionTransitionFunction();
	}
}
