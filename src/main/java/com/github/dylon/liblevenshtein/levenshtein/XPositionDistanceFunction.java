package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Distance function for the Levenshtein distance algorithms, extended with
 * transposition (standard- and t-positions), and with merge and split
 * (standard- and s-positions). Because this functor applies to both the
 * transposition and merge-and-split automata, it uses the identifier, x, to
 * represent both t and s.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class TPositionDistanceFunction implements IDistanceFunction {

  /**
   * {@inheritDoc}
   */
  @Override
  public int at(final int[][] state, final int w) {
  	int minimumDistance = Integer.MAX_VALUE;

  	for (final int[] position : state) {
  		// (1 == x) -> t-position or s-position
  		// (0 == x) -> standard position
  		// only calculate the distance for standard positions.
  		final int x = position[2];
  		if (0 == x) {
  			final int i = position[0];
  			final int e = position[1];
  			final int distance = w - i + e;
  			if (distance < minimumDistance) {
  				minimumDistance = distance;
  			}
  		}
  	}

  	return minimumDistance;
  }
}
