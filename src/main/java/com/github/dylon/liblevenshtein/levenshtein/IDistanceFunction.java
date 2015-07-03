package com.github.dylon.liblevenshtein.levenshtein;

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
public interface IDistanceFunction {

  /**
   * @param state Levenshtein state whose minimum, Levenshtein distance is to be
   * determined.
   * @param w Length of the query term.
   * @return Minimum distance between the spelling candidate and query term,
   * implied by the positions within {@code state}.
   */
  int at(IState state, int w);
}
