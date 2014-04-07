package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

public class StandardPositionFactory implements IPositionFactory {
	private final Queue<int[]> standardPositions = new ArrayDeque<>();

	@Override
	public int[] build(final int i, final int e) {
		int[] position = standardPositions.poll();

		if (null == position) {
			position = new int[2];
		}

		position[0] = i;
		position[1] = e;
		return position;
	}

	@Override
	public int[] build(final int i, final int e, final int x) {
		throw new UnsupportedOperationException(
				"StandardPositionFactory only supports build(int,int)");
	}

	@Override
	public void recycle(final int[] position) {
		standardPositions.offer(position);
	}
}
