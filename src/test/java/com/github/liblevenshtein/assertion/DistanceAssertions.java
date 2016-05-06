package com.github.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.liblevenshtein.distance.IDistance;

/**
 * AssertJ-style assertions for distance functions.  To be considered a distance
 * function, a function must satisfy the following:
 * 1. Equal Self-Similarity
 * 2. Minimality
 * 3. Symmetry
 * 4. Triangle Inequality
 * @param <Type> Generic type whose elements have a distance function.
 */
public class DistanceAssertions<Type>
    extends AbstractAssert<DistanceAssertions<Type>, IDistance<Type>> {

  /**
   * Constructs a new {@link DistanceAssertions}.
   * @param actual The distance function being asserted-against.
   */
  public DistanceAssertions(final IDistance<Type> actual) {
    super(actual, DistanceAssertions.class);
  }

  /**
   * Builds a new {@link DistanceAssertions}.
   * @param actual The distance function being asserted-against.
   * @param <Type> Generic type whose elements have a distance function.
   * @return A new {@link DistanceAssertions} that asserts-against the distance
   * function
   */
  public static <Type> DistanceAssertions<Type> assertThat(
      final IDistance<Type> actual) {
    return new DistanceAssertions<>(actual);
  }

  /**
   * Asserts that the distance function satisfies equal self-similarity.
   * @param term1 First term for the comparison.
   * @param term2 Second term for the comparison.
   * @return This {@link DistanceAssertions} for fluency.
   * @throws AssertionError When {@code d(term1, term2) != d(term2, term1)}
   */
  public DistanceAssertions<Type> satisfiesEqualSelfSimilarity(
      final Type term1,
      final Type term2) {

    isNotNull();

    final int d1 = actual.between(term1, term1);
    final int d2 = actual.between(term2, term2);

    if (d1 != d2) {
      failWithMessage(
        "Expected d(%s, %s) = [%d] to be d(%s, %s) = [%d]",
        term1, term2, d1, term2, term1, d2);
    }

    return this;
  }

  /**
   * Asserts that the distance function satisfies minimality, such that for all
   * terms {@code term1 != term2}, we have that
   * {@code d(term1, term2) > d(term1, term1)} and that
   * {@code d(term2, term1) > d(term1, term1)}.
   * @param term1 First term for the comparison.
   * @param term2 Second term for the comparison.
   * @return This {@link DistanceAssertions} for fluency.
   * @throws AssertionError When {@code d(term1, term2) <= d(term1, term1)}
   * or when {@code d(term2, term1) <= d(term1, term1)}.
   */
  public DistanceAssertions<Type> satisfiesMinimality(
      final Type term1,
      final Type term2) {

    isNotNull();

    if (null != term1 && term1.equals(term2)) {
      failWithMessage("Did not expect term1 [%s] to be equal-to term2 [%s]",
        term1, term2);
    }

    final int d11 = actual.between(term1, term1);
    final int d12 = actual.between(term1, term2);
    final int d21 = actual.between(term2, term1);

    if (d12 <= d11) {
      failWithMessage(
        "Expected d(%s, %s) = [%d] > d(%s, %s) = [%d]",
        term1, term2, d12, term1, term1, d11);
    }

    if (d21 <= d11) {
      failWithMessage(
        "Expected d(%s, %s) = [%d] > d(%s, %s) = [%d]",
        term2, term1, d21, term1, term1, d11);
    }

    return this;
  }

  /**
   * Asserts that the distance function satisfies symmetry, such that for all
   * terms term1, term2 we have that
   * {@code d(term1, term2) = d(term2, term1)}.
   * @param term1 First term for the comparison.
   * @param term2 Second term for the comparison.
   * @return This {@link DistanceAssertions} for fluency.
   * @throws AssertionError When {@code d(term1, term2) != d(term2, term1)}
   */
  public DistanceAssertions<Type> satisfiesSymmetry(
      final Type term1,
      final Type term2) {

    isNotNull();

    final int d12 = actual.between(term1, term2);
    final int d21 = actual.between(term2, term1);

    if (d12 != d21) {
      failWithMessage("Expected d(%s, %s) = [%d] to be equal-to d(%s, %s) = [%d]",
        term1, term2, d12, term2, term1, d21);
    }

    return this;
  }

  /**
   * Asserts that the distance function satisfies the triangle inequality, such that for all
   * terms term1, term2, term3 we have that
   * {@code d(term1, term2) + d(term1, term3) < d(term2, term3)}
   * when terms term1, term2, term3 are not colinear, and only
   * {@code d(term1, term2) + d(term1, term3) = d(term2, term3)}
   * when they are colinear.
   * @param term1 First term for the comparison.
   * @param term2 Second term for the comparison.
   * @param term3 Third term for the comparison.
   * @return This {@link DistanceAssertions} for fluency.
   * @throws AssertionError When any of the following hold:
   * <ol>
   *   <li>{@code d(term1, term2) + d(term1, term3) < d(term2, term3)}</li>
   *   <li>{@code d(term1, term2) + d(term2, term3) < d(term1, term3)}</li>
   *   <li>{@code d(term1, term3) + d(term2, term3) < d(term1, term2)}</li>
   * </ol>
   */
  public DistanceAssertions<Type> satisfiesTriangleInequality(
      final Type term1,
      final Type term2,
      final Type term3) {

    isNotNull();

    final int d12 = actual.between(term1, term2);
    final int d13 = actual.between(term1, term3);
    final int d23 = actual.between(term2, term3);

    if (d12 + d13 < d23) {
      failWithMessage(
        "Expected (d(%s, %s) = [%d] + d(%s, %s) = [%d]) = [%d] >= d(%s, %s) = [%d]",
        term1, term2, d12, term1, term3, d13, (d12 + d13), term2, term3, d23);
    }

    if (d12 + d23 < d13) {
      failWithMessage(
        "Expected (d(%s, %s) = [%d] + d(%s, %s) = [%d]) = [%d] >= d(%s, %s) = [%d]",
        term1, term2, d12, term2, term3, d23, (d12 + d23), term1, term3, d13);
    }

    if (d13 + d23 < d12) {
      failWithMessage(
        "Expected (d(%s, %s) = [%d] + d(%s, %s) = [%d]) = [%d] >= d(%s, %s) = [%d]",
        term1, term3, d13, term2, term3, d23, (d13 + d23), term1, term2, d12);
    }

    return this;
  }

  /**
   * Asserts that the distance between terms term1, term2 is expected.
   * @param expectedDistance Distance asserted-against
   * @param term1 First term for the comparison.
   * @param term2 Second term for the comparison.
   * @return This {@link DistanceAssertions} for fluency.
   * @throws AssertionError When {@code d(term1, term2) != expectedDistance}
   */
  public DistanceAssertions<Type> hasDistance(
      final int expectedDistance,
      final Type term1,
      final Type term2) {

    isNotNull();

    final int actualDistance = actual.between(term1, term2);

    if (expectedDistance != actualDistance) {
      failWithMessage("Expected d(%s, %s) = [%d], but was [%d]",
        term1, term2, expectedDistance, actualDistance);
    }

    return this;
  }
}
