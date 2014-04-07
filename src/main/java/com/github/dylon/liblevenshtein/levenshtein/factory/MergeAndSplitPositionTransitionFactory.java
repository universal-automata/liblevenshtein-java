package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.MergeAndSplitPositionTransitionFunction;

public class MergeAndSplitPositionTransitionFactory
	extends AbstractPositionTransitionFactory {

	@Override
	public IPositionTransitionFunction build() {
		return new MergeAndSplitPositionTransitionFunction();
	}
}
