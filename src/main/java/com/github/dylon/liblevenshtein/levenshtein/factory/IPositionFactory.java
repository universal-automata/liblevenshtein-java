package com.github.dylon.liblevenshtein.levenshtein.factory;

public interface IPositionFactory {

	int[] build(int i, int e);

	int[] build(int i, int e, int x);

	void recycle(int[] position);
}
