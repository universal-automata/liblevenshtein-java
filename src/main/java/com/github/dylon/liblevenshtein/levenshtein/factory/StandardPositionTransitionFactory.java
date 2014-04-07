package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionTransitionFunction;

public class StandardPositionTransitionFactory
	extends AbstractPositionTransitionFactory {

	@Override
	public IPositionTransitionFunction build() {
		return new StandardPositionTransitionFunction();
	}
}
