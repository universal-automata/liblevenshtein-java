package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Distance function for the standard, Levenshtein distance algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StandardPositionDistanceFunction implements IDistanceFunction {

  /**
   * {@inheritDoc}
   */
  @Override
  public int at(final IState state, final int w) {
    int minimumDistance = Integer.MAX_VALUE;

    for (int m = 0; m < state.size(); ++m) {
      final int[] position = state.getOuter(m);
      final int i = position[0];
      final int e = position[1];
      final int distance = w - i + e;
      if (distance < minimumDistance) {
        minimumDistance = distance;
      }
    }

    return minimumDistance;
  }
}
