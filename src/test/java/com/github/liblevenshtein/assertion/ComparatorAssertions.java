package com.github.liblevenshtein.assertion;

import java.util.Comparator;

import org.assertj.core.api.AbstractAssert;

/**
 * AssertJ-quality assertions for Comparator objects.
 * @param <Type> Generic type of the comparator that is asserted-against.
 */
public class ComparatorAssertions<Type>
    extends AbstractAssert<ComparatorAssertions<Type>, Comparator<Type>> {

  /**
   * Constructs a new {@link ComparatorAssertions} for asserting-against
   * {@link #actual}.
   * @param actual Comparator to assert-against.
   */
  public ComparatorAssertions(final Comparator<Type> actual) {
    super(actual, ComparatorAssertions.class);
  }

  /**
   * Constructs a new {@link ComparatorAssertions} for asserting-against
   * {@link #actual}.
   * @param actual Comparator to assert-against.
   * @param <Type> Generic type of the Comparator that is asserted-against.
   * @return New instance of {@link ComparatorAssertions} to assert-against
   * {@link #param}.
   */
  public static <Type> ComparatorAssertions<Type> assertThat(
      final Comparator<Type> actual) {
    return new ComparatorAssertions<>(actual);
  }

  /**
   * Asserts that {@link #actual} specifies {@link #lhs} is equal-to
   * {@link #rhs}.
   * @param lhs Parameter for the left side of the omparison.
   * @param rhs Parameter for the right side of the comparator.
   * @return This {@link ComparatorAssertions} for fluency.
   * @throws AssertionError When the comparison is not equals-to.
   */
  public ComparatorAssertions<Type> comparesEqualTo(
      final Type lhs,
      final Type rhs) {

    isNotNull();

    final int comparison = actual.compare(lhs, rhs);

    if (comparison != 0) {
      failWithMessage(
        "Expected lhs [%s] to be [equal-to] rhs [%s], but was [%d]",
        lhs, rhs, comparison);
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} specifies {@link #lhs} is greater-than
   * {@link #rhs}.
   * @param lhs Parameter for the left side of the omparison.
   * @param rhs Parameter for the right side of the comparator.
   * @return This {@link ComparatorAssertions} for fluency.
   * @throws AssertionError When the comparison is not greater-than.
   */
  public ComparatorAssertions<Type> comparesGreaterThan(
      final Type lhs,
      final Type rhs) {

    isNotNull();

    final int comparison = actual.compare(lhs, rhs);

    if (comparison <= 0) {
      failWithMessage(
        "Expected lhs [%s] to be [greater-than] rhs [%s], but was [%s]",
        lhs, rhs, comparison < 0 ? "less-than" : "equal-to");
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} specifies {@link #lhs} is less-than
   * {@link #rhs}.
   * @param lhs Parameter for the left side of the omparison.
   * @param rhs Parameter for the right side of the comparator.
   * @return This {@link ComparatorAssertions} for fluency.
   * @throws AssertionError When the comparison is not less-than.
   */
  public ComparatorAssertions<Type> comparesLessThan(
      final Type lhs,
      final Type rhs) {

    isNotNull();

    final int comparison = actual.compare(lhs, rhs);

    if (comparison >= 0) {
      failWithMessage(
        "Expected lhs [%s] to be [less-than] rhs [%s], but was [%s]",
        lhs, rhs, comparison > 0 ? "greater-than" : "equal-to");
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} specifies {@link #lhs} is
   * greater-than-or-equal-to {@link #rhs}.
   * @param lhs Parameter for the left side of the omparison.
   * @param rhs Parameter for the right side of the comparator.
   * @return This {@link ComparatorAssertions} for fluency.
   * @throws AssertionError When the comparison is not greater-than-or-equal-to.
   */
  public ComparatorAssertions<Type> comparesGreaterThanOrEqualTo(
      final Type lhs,
      final Type rhs) {

    isNotNull();

    final int comparison = actual.compare(lhs, rhs);

    if (comparison < 0) {
      failWithMessage(
        "Expected lhs [%s] to be [greater-than-or-equal-to] rhs [%s], "
        + "but was [less than]", lhs, rhs);
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} specifies {@link #lhs} is
   * less-than-or-equal-to {@link #rhs}.
   * @param lhs Parameter for the left side of the omparison.
   * @param rhs Parameter for the right side of the comparator.
   * @return This {@link ComparatorAssertions} for fluency.
   * @throws AssertionError When the comparison is not less-than-or-equal-to.
   */
  public ComparatorAssertions<Type> comparesLessThanOrEqualTo(
      final Type lhs,
      final Type rhs) {

    isNotNull();

    final int comparison = actual.compare(lhs, rhs);

    if (comparison > 0) {
      failWithMessage(
        "Expected lhs [%s] to be [less-than-or-equal-to] rhs [%s], "
        + "but was [greater-than]", lhs, rhs);
    }

    return this;
  }
}
