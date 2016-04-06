package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;

/**
 * Builds instances of Levenshtein distance metrics.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDistanceFactory<Term> extends Serializable {

  /**
   * Returns a Levenshtein distance metric that utilizes the request algorithm.
   * @param algorithm Type of metric that should be returned
   * @return A distance metric that utilizes the request alagorithm
   */
  IDistance<Term> build(Algorithm algorithm);
}
