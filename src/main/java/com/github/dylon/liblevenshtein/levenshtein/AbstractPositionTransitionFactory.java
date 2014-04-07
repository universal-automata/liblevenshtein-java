package com.github.dylon.liblevenshtein.levenshtein;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public abstract class AbstractPositionTransitionFactory
	implements IPositionTransitionFactory {

	Queue<IPositionTransitionFunction> transitions = new ArrayDeque<>();

	@Override
	public IPositionTransitionFunction build(final int n) {
		IPositionTransitionFunction transition = transitions.poll();

		if (null == transition) {
			transition = build();
		}

		transition.maxEditDistance(n);
		return transition;
	}

	@Override
	public void recycle(final IPositionTransitionFunction transition) {
		transitions.offer((StandardPositionTransitionFunction) transition);
	}
}
