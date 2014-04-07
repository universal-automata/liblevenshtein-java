package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class AbstractDistanceAndTermComparator
  implements Comparator<Intersection> {

  /** Query term to consider while sorting correction candidates */
  String term;
  String lowerTerm;

  public AbstractDistanceAndTermComparator term(final String term) {
    this.term = term;
    this.lowerTerm = term.toLowerCase();
    return this;
  }
}
