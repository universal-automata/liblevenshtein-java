package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ICandidateCollection {
  boolean offer(String term, int distance);
}
