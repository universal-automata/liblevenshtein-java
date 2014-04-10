package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class DistanceComparator implements Comparator<Intersection> {

  /** Query term to consider while sorting correction candidates */
  @Setter String term;

  /**
   * Builds a new DistanceComparator with a term to sort by.
   * @param term The term to sort by
   * @return a new DistanceComparator with a term to sort by.
   */
  public abstract DistanceComparator build();

  /**
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class WithCaseInsensitiveSort extends DistanceComparator {

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
      return a.candidate().compareToIgnoreCase(b.candidate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistanceComparator build() {
      return new WithCaseInsensitiveSort();
    }
  }

  /**
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class WithCaseSensitiveSort extends DistanceComparator {

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

    /**
     * {@inheritDoc}
     */
    @Override
    public DistanceComparator build() {
      return new WithCaseSensitiveSort();
    }
  }
}
