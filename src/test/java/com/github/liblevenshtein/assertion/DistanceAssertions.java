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
   *   function
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
   *   or when {@code d(term2, term1) <= d(term1, term1)}.
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
    final int d22 = actual.between(term2, term2);

    assertMinimality(term1, term2, d12, term1, d11);
    assertMinimality(term2, term1, d21, term1, d11);
    assertMinimality(term1, term2, d12, term2, d22);
    assertMinimality(term2, term1, d21, term2, d22);

    return this;
  }


  /**
   * Asserts that the distances satisfy minimality.
   * @param t1 Term to compare with {@code t2}.
   * @param t2 Term to compare with {@code t1}.
   * @param d12 Distance between {@code t1} and {@code t2}.
   * @param t3 Term to compare with itself.
   * @param d33 Distance between {@code t3} and itself.
   * @throws AssertionError When minimality is not satisfied.
   */
  private void assertMinimality(
      final Type t1, final Type t2, final int d12,
      final Type t3, final int d33) {
    if (d12 <= d33) {
      failWithMessage(
        "Expected d(%s, %s) = [%d] > d(%s, %s) = [%d]",
        t1, t2, d12, t3, t3, d33);
    }
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

    assertTriangleInequality(
      term1, term2, d12,
      term1, term3, d13,
      term2, term3, d23);

    assertTriangleInequality(
      term1, term2, d12,
      term2, term3, d23,
      term1, term3, d13);

    assertTriangleInequality(
      term1, term3, d13,
      term2, term3, d23,
      term1, term2, d12);

    return this;
  }

  /**
   * Asserts that the distances satisfy the triangle inequality.
   * @param t1 Term to compare with {@code t2}.
   * @param t2 Term to compare with {@code t1}.
   * @param d12 Distance between {@code t1} and {@code t2}
   * @param t3 Term to compare with {@code t4}.
   * @param t4 Term to compare with {@code t3}.
   * @param d34 Distance between {@code t3} and {@code t4}
   * @param t5 Term to compare with {@code t6}.
   * @param t6 Term to compare with {@code t5}.
   * @param d56 Distance between {@code t5} and {@code t6}
   * @throws AssertionError When the triangle inequality does not hold.
   */
  @SuppressWarnings("checkstyle:parameternumber")
  private void assertTriangleInequality(
      final Type t1, final Type t2, final int d12,
      final Type t3, final Type t4, final int d34,
      final Type t5, final Type t6, final int d56) {
    if (d12 + d34 < d56) {
      failWithMessage(
        "Expected (d(%s, %s) = [%d] + d(%s, %s) = [%d]) = [%d] >= d(%s, %s) = [%d]",
        t1, t2, d12, t3, t4, d34, d12 + d34, t5, t6, d56);
    }
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
