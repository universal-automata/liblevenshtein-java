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
public abstract class AbstractDistanceAndTermComparator
  implements Comparator<Intersection> {

  /** Query term to consider while sorting correction candidates */
  @Setter String term;
}
