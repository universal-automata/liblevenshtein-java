package com.github.dylon.liblevenshtein.levenshtein;

public interface ICharacteristicVectorFunction {

  int[] of(char label, String term, int k, int i);
}
