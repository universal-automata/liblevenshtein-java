package com.github.dylon.liblevenshtein.levenshtein;

public interface IState {

  int size();

  void add(int[] position);

	void insert(int index, int[] position);

  int[] getOuter(int index);

  int[] getInner(int index);

  int[] removeInner();

  void clear();
}
