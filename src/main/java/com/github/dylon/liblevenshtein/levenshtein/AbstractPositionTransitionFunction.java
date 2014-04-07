package com.github.dylon.liblevenshtein.levenshtein;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class AbstractPositionTransitionFunction
	implements IPositionTransitionFunction {

	@Setter IStateFactory stateFactory;

	@Setter IPositionFactory positionFactory;

	@Setter int maxEditDistance;

	/**
	 * Returns the first index of the characteristic vector between indices, i and
	 * k, that is true.  This corresponds to the first index of the relevant
	 * subword whose element is the character of interest.
	 * @param characteristicVector
	 * @param k
	 * @param i
	 * @return
	 */
	protected int indexOf(
			final boolean[] characteristicVector,
			final int k,
			final int i) {

		for (int j = 0; j < k; ++j) {
			if (characteristicVector[i + j]) {
				return j;
			}
		}

		return -1;
	}
}
