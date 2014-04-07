package com.github.dylon.liblevenshtein.levenshtein;

public interface IPositionFactory {

	int[] build(int i, int e);

	int[] build(int i, int e, int x);

	void recycle(int[] position);
}
