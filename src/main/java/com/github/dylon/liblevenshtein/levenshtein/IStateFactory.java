package com.github.dylon.liblevenshtein.levenshtein;

import java.util.List;

public interface IStateFactory {

	List<int[]> build(int[]... positions);

	void recycle(List<int[]> state);
}
