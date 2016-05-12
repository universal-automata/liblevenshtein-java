package com.github.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.SubsumesFunction;

/**
 * AssertJ-style assertions for {@link SubsumesFunction}.
 */
public class SubsumesFunctionAssertions
    extends AbstractAssert<SubsumesFunctionAssertions, SubsumesFunction> {

  /**
   * Constructs a new {@link SubsumesFunctionAssertions} to assert-against a
   * {@link SubsumesFunction}.
   * @param actual {@link SubsumesFunction} to assert-against.
   */
  public SubsumesFunctionAssertions(final SubsumesFunction actual) {
    super(actual, SubsumesFunctionAssertions.class);
  }

  /**
   * Builds a new {@link SubsumesFunctionAssertions} to assert-against a
   * {@link SubsumesFunction}.
   * @param actual {@link SubsumesFunction} to assert-against.
   * @return New instance of {@link SubsumesFunctionAssertions}, that
   *   asserts-against {@link #actual}.
   */
  public static SubsumesFunctionAssertions assertThat(
      final SubsumesFunction actual) {
    return new SubsumesFunctionAssertions(actual);
  }

  /**
   * Asserts-that the standard, Levenshtein state represented by {@code lhs}
   * subsumes the other, represented by {@code rhs}.
   * @param lhs First position
   * @param rhs Second position
   * @param n Length of the query term.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   * @throws AssertionError When {@code lhs} does not subsume {@code rhs}.
   */
  public SubsumesFunctionAssertions subsumesAt(
      final Position lhs,
      final Position rhs,
      final int n) {

    isNotNull();

    if (!actual.at(lhs, rhs, n)) {
      failWithMessage(
        "Expected position [%s] to subsume position [%s], under term length [%d]",
        lhs, rhs, n);
    }

    return this;
  }

  /**
   * Asserts-that the standard, Levenshtein state represented by
   * {@code lhs} subsumes the other, represented by {@code rhs}.
   * @param lhs First position
   * @param rhs Second position
   * @param n Length of the query term.
   * @param shouldSubsume Whether {@code lhs} should subsume {@code rhs}.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   */
  public SubsumesFunctionAssertions subsumesAt(
      final Position lhs,
      final Position rhs,
      final int n,
      final boolean shouldSubsume) {

    if (shouldSubsume) {
      return subsumesAt(lhs, rhs, n);
    }

    return doesNotSubsumeAt(lhs, rhs, n);
  }

  /**
   * Asserts-that the standard, Levenshtein state represented by {@code lhs}
   * does not subsume the other, represented by {@code rhs}.
   * @param lhs First position
   * @param rhs Second position
   * @param n Length of the query term.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   * @throws AssertionError When {@code lhs} subsumes {@code rhs}.
   */
  public SubsumesFunctionAssertions doesNotSubsumeAt(
      final Position lhs,
      final Position rhs,
      final int n) {

    isNotNull();

    if (actual.at(lhs, rhs, n)) {
      failWithMessage(
        "Did not expect position [%s] to subsume position [%s], under term length [%d]",
        lhs, rhs, n);
    }

    return this;
  }
}
