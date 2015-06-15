package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Value;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
public class Candidate {
  String term;
  int distance;
}
