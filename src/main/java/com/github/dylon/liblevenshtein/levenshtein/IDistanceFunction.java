package com.github.dylon.liblevenshtein.levenshtein;

/**
 * The distance of each position in a state can be defined as follows:
 *
 *   distance = w - i + e
 *
 * For every accepting position, it must be the case that w - i &lt;= n - e.  It
 * follows directly that the distance of every accepted position must be no
 * more than n:
 *
 * (w - i &lt;= n - e) &lt;=&gt; (w - i + e &lt;= n) &lt;=&gt; (distance &lt;= n)
 *
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
   */
  int at(IState state, int w);
}
