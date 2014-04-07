package com.github.dylon.liblevenshtein.levenshtein;

public class MergeAndSplitPositionTransitionFactory
	extends AbstractPositionTransitionFactory {

	@Override
	public IPositionTransitionFunction build() {
		return new MergeAndSplitPositionTransitionFunction();
	}
}
