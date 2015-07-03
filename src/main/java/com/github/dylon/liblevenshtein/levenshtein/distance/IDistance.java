package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Specifies the interface that all distance functions must implement.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDistance<Term> {

  /**
   * Finds the distance between two terms, {@code v} and {@code w}. The distance
   * between two terms is complemented by their similarity, which is determined
   * by subtracting their distance from the maximum distance they may be apart.
   * @param v Term to compare with {@code w}
   * @param w Term to compare with {@code v}
   * @return Distance between {@code v} and {@code w}
   */
  int between(Term v, Term w);
}
