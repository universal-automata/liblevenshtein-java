package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
public class LevenshteinTransitionFunction
  implements ILevenshteinTransitionFunction {

  @Setter private int maxDistance;

  /**
   * {@inheritDoc}
   */
  @Override
  public int[][] of(
      final int[][] levenshteinState,
      final boolean[] characteristicVector) {
    throw new RuntimeException("not yet implemented");
  }
}
