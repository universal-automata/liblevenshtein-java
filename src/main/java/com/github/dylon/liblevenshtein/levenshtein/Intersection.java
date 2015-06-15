package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Data;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
public class Intersection<DictionaryNode> {
  String candidate;
  DictionaryNode dictionaryNode;
  IState levenshteinState;
  int distance;
}
