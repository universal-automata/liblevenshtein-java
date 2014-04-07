package com.github.dylon.liblevenshtein.levenshtein;

public interface ISubsumesFunction {

	boolean at(int i, int e, int j, int f);

	boolean at(int i, int e, int s, int j, int f, int t, int n);
}
