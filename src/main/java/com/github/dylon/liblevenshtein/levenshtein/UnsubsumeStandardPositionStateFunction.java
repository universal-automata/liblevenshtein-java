package com.github.dylon.liblevenshtein.levenshtein;

public class UnsubsumeStandardPositionStateFunction
	extends AbstractUnsubsumeFunction {

	@Override
	public void at(final IState state) {
		for (int m = 0; m < state.size(); ++m) {
			final int[] outer = state.getOuter(m);
			final int i = outer[0];
			final int e = outer[1];

			int n = m + 1;
			while (n < state.size()) {
				final int[] inner = state.getInner(n);
				final int f = inner[1];
				if (e < f) {
					break;
				}
				n += 1;
			}

			while (n < state.size()) {
				final int[] inner = state.getInner(n);
				final int j = inner[0];
				final int f = inner[1];

				if (subsumes.at(i,e, j,f)) {
					state.removeInner();
				}
				else {
					n += 1;
				}
			}
		}
	}
}
