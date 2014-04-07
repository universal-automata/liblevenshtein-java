package com.github.dylon.liblevenshtein.levenshtein;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class StateFactory implements IStateFactory {
	private final Queue<List<int[]>> states = new ArrayDeque<>();

	@Override
	public List<int[]> build(final int[]... positions) {
		List<int[]> state = states.poll();

		if (null == state) {
			state = new ArrayList<int[]>(positions.length << 2);
		}

		for (final int[] position : positions) {
			state.add(position);
		}

		return state;
	}

	@Override
	public void recycle(final List<int[]> state) {
		state.clear();
		states.offer(state);
	}
}
