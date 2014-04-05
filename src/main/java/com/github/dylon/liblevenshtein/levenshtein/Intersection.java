package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@Accessors(fluent=true)
public class Intersection<DictionaryNode> {
  String candidate;
  DictionaryNode dictionaryNode;
  int[][] levenshteinState;
  int distance;
}
