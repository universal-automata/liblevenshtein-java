package com.github.dylon.liblevenshtein.levenshtein;

public class StandardPositionTransitionFactory
	extends AbstractPositionTransitionFactory {

	@Override
	public IPositionTransitionFunction build() {
		return new StandardPositionTransitionFunction();
	}
}
