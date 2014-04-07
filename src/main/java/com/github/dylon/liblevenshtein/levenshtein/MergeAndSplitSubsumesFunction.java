package com.github.dylon.liblevenshtein.levenshtein;

public class MergeAndSplitSubsumesFunction
	extends AbstractSubsumesFunction {

	@Override
	public boolean at(
			final int i, final int e, final int s,
			final int j, final int f, final int t,
			final int n) {

		if (s == 1 && t == 0) {
			return false;
		}

		return ((i < j) ? (j - i) : (i - j)) <= (f - e);
	}
}
