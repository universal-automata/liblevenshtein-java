package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

public class XPositionFactory implements IPositionFactory {
	private final Queue<int[]> xPositions = new ArrayDeque<>();

	@Override
	public int[] build(final int i, final int e) {
		throw new UnsupportedOperationException(
				"XPositionFactory only supports build(int,int,int)");
	}

	@Override
	public int[] build(final int i, final int e, final int x) {
		int[] position = xPositions.poll();

		if (null == position) {
			position = new int[3];
		}

		position[0] = i;
		position[1] = e;
		position[2] = x;
		return position;
	}

	@Override
	public void recycle(final int[] position) {
		xPositions.offer(position);
	}
}
