package com.github.dylon.liblevenshtein.collection;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Symmetric, immutable pairs are equivalent if they contain equivalent
 * elements, regardless the order of the elements.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
@Accessors(fluent = true)
public class SymmetricImmutablePair<Type extends Comparable<Type>>
    implements Comparable<SymmetricImmutablePair<Type>> {

  /** First element of this pair */
  private final Type first;

  /** Second element of this pair */
  private final Type second;

  /** Returned from {@link hashCode()} */
  private final int hashCode;

  /**
   * Constructs a new symmetric, immutable pair on the given parameters.  The
   * hashCode is determined upon construction, so the assumption is made that
   * first and second are immutable or will not be mutated. If they or any of
   * their hashable components are mutated post-construction, the hashCode will
   * be incorrect and the behavior of instances of this pair in such structures
   * as hash maps will be undefined (e.g. this pair may seem to "disappear" from
   * the hash map, even though it's still there).
   * @param first First element of this pair
   * @param second Second element of this pair
   */
  public SymmetricImmutablePair(
      @NonNull final Type first,
      @NonNull final Type second) {

    this.first = first;
    this.second = second;

    Type a, b;
    if (first.compareTo(second) > 0) {
      a = second; b = first;
    }
    else {
      a = first; b = second;
    }

    this.hashCode = new HashCodeBuilder(541, 7873)
      .append(a)
      .append(b)
      .toHashCode();
  }

  /**
   * {@inheritDoc}
   * Warning: You will be a NullPointerException if you pass null into here.
   */
  @Override
  public int compareTo(final SymmetricImmutablePair<Type> other) {
    Type a = first, b = second, c = other.first(), d = other.second(), t;

    if (a.compareTo(b) > 0) {
      t = a; a = b; b = t;
    }

    if (c.compareTo(d) > 0) {
      t = c; c = d; d = t;
    }

    final int a_c = a.compareTo(c);
    if (a_c == 0) return b.compareTo(d);
    return a_c;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof SymmetricImmutablePair)) {
      return false;
    }

    @SuppressWarnings("unchecked")
    final SymmetricImmutablePair<Type> other = (SymmetricImmutablePair<Type>) o;
    return 0 == compareTo(other);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return hashCode;
  }
}
