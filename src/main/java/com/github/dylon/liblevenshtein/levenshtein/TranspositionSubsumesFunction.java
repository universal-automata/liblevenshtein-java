package com.github.dylon.liblevenshtein.levenshtein;

public class TranspositionSubsumesFunction
	extends AbstractSubsumesFunction {

	@Override
	public boolean at(
			final int i, final int e, final int s,
			final int j, final int f, final int t,
			final int n) {

		if (s == 1) {
			if (t == 1) {
				return (i == j);
			}

			return (f == n) && (i == j);
		}

		if (t == 1) {
			return ((i < j) ? (j - i) : (i - j)) + 1 <= (f - e);
		}

		return ((i < j) ? (j - i) : (i - j)) <= (f - e);
	}
}
