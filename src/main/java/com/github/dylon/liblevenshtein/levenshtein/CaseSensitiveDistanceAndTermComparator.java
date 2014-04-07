package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class CaseSensitiveDistanceAndTermComparator
  extends AbstractDistanceAndTermComparator {

  /**
   * {@inheritDoc}
   */
  @Override
  public int compare(final Intersection a, final Intersection b) {
    int c = a.distance() - b.distance();
    if (0 != c) return c;
    c = Math.abs(a.candidate().compareToIgnoreCase(term))
      - Math.abs(b.candidate().compareToIgnoreCase(term));
    if (0 != c) return c;
    c = Math.abs(a.candidate().compareTo(term))
      - Math.abs(b.candidate().compareTo(term));
    if (0 != c) return c;
    c = a.candidate().compareToIgnoreCase(b.candidate());
    if (0 != c) return c;
    return a.candidate().compareTo(b.candidate());
  }
}
