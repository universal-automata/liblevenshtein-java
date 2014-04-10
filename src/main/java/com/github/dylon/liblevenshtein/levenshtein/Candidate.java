package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
@Accessors(fluent=true)
public class Candidate {
  String term;
  int distance;
}
