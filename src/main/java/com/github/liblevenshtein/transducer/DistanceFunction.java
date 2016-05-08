package com.github.liblevenshtein.transducer;

import java.io.Serializable;

/**
 * The distance of each position in a state can be defined as follows:
 * <p>
 * <code>distance = w - i + e</code>
 * <p>
 * For every accepting position, it must be the case that <code>w - i &le; n - e</code>.
 * It follows directly that the distance of every accepted position must be no
 * more than {@code n}:
 * <p>
 * <code>(w - i &le; n - e) &hArr; (w - i + e &le; n) &hArr; (distance &le; n)</code>
 * <p>
 * The Levenshtein distance between any two terms is defined as the minimum
 * edit distance between the two terms.  Therefore, iterate over each position
 * in an accepting state, and take the minimum distance among all its accepting
 * positions as the corresponding Levenshtein distance.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class DistanceFunction implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * @param state Levenshtein state whose minimum, Levenshtein distance is to be
   * determined.
   * @param w Length of the query term.
   * @return Minimum distance between the spelling candidate and query term,
   * implied by the positions within {@code state}.
   */
  public abstract int at(State state, int w);

  /**
    * Distance function for the standard, Levenshtein distance algorithm.
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class ForStandardPositions extends DistanceFunction {

    private static final long serialVersionUID = 1L;

    /**
      * {@inheritDoc}
      */
    @Override
    public int at(final State state, final int queryLength) {
      int minimumDistance = Integer.MAX_VALUE;

      for (final Position position : state) {
        final int i = position.termIndex();
        final int e = position.numErrors();
        final int distance = queryLength - i + e;
        if (distance < minimumDistance) {
          minimumDistance = distance;
        }
      }

      return minimumDistance;
    }
  }

  /**
    * Distance function for the Levenshtein distance algorithms, extended with
    * transposition (standard- and t-positions), and with merge and split
    * (standard- and s-positions).
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class ForSpecialPositions extends DistanceFunction {

    private static final long serialVersionUID = 1L;

    /**
      * {@inheritDoc}
      */
    @Override
    public int at(final State state, final int queryLength) {
      int minimumDistance = Integer.MAX_VALUE;

      for (final Position position : state) {
        if (!position.isSpecial()) {
          final int i = position.termIndex();
          final int e = position.numErrors();
          final int distance = queryLength - i + e;
          if (distance < minimumDistance) {
            minimumDistance = distance;
          }
        }
      }

      return minimumDistance;
    }
  }
}
