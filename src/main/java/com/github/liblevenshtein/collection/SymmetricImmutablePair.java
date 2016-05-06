package com.github.liblevenshtein.collection;

import java.io.Serializable;

import lombok.NonNull;
import lombok.Value;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Symmetric, immutable pairs are equivalent if they contain equivalent
 * elements, regardless the order of the elements.
 * @param <Type> Kind of the elements stored in this pair.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
public class SymmetricImmutablePair<Type extends Comparable<Type>>
    implements Comparable<SymmetricImmutablePair<Type>>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * First element of this pair.
   * -- GETTER --
   * First element of this pair.
   * @return First element of this pair.
   */
  private final Type first;

  /**
   * Second element of this pair.
   * -- GETTER --
   * Second element of this pair.
   * @return Second element of this pair.
   */
  private final Type second;

  /**
   * Returned from {@link #hashCode()}.
   */
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

    if (first.compareTo(second) < 0) {
      this.first = first;
      this.second = second;
    }
    else {
      this.first = second;
      this.second = first;
    }

    this.hashCode = new HashCodeBuilder(541, 7873)
      .append(this.first)
      .append(this.second)
      .toHashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(final SymmetricImmutablePair<Type> other) {
    final int c = first.compareTo(other.first());
    if (0 == c) {
      return second.compareTo(other.second());
    }
    return c;
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
