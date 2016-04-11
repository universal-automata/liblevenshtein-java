package com.github.dylon.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.dylon.liblevenshtein.levenshtein.SubsumesFunction;

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
   * asserts-against {@link #actual}.
   */
  public static SubsumesFunctionAssertions assertThat(
      final SubsumesFunction actual) {
    return new SubsumesFunctionAssertions(actual);
  }

  /**
   * Asserts-that the standard, Levenshtein state represented by
   * {@code (i,e)} subsumes the other, represented by {@code (j,f)}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   * @throws AssertionError When {@code (i,e)} does not subsume {@code (j,f)}.
   */
  public SubsumesFunctionAssertions subsumesAt(
      final int i, final int e,
      final int j, final int f) {

    isNotNull();

    if (!actual.at(i, e, j, f)) {
      failWithMessage(
        "Expected position [%d,%d] to subsume position [%d,%d]",
        i, e, j, f);
    }

    return this;
  }

  /**
   * Asserts-that the standard, Levenshtein state represented by
   * {@code (i,e)} does not subsume the other, represented by {@code (j,f)}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   * @throws AssertionError When {@code (i,e)} subsumes {@code (j,f)}.
   */
  public SubsumesFunctionAssertions doesNotSubsumeAt(
      final int i, final int e,
      final int j, final int f) {

    isNotNull();

    if (actual.at(i, e, j, f)) {
      failWithMessage(
        "Did not expect position [%d,%d] to subsume position [%d,%d]",
        i, e, j, f);
    }

    return this;
  }

  /**
   * Asserts-that the standard, Levenshtein state represented by
   * {@code (i,e)} subsumes the other, represented by {@code (j,f)}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @param shouldSubsume Whether {@code (i,e)} should subsume {@code (j,f)}.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   */
  public SubsumesFunctionAssertions subsumesAt(
      final int i, final int e,
      final int j, final int f,
      final boolean shouldSubsume) {

    if (shouldSubsume) {
      return subsumesAt(i, e, j, f);
    }

    return doesNotSubsumeAt(i, e, j, f);
  }

  /**
   * Asserts-that the transposition or merge-and-split, Levenshtein state
   * represented by {@code (i,e,s)} subsumes the other, represented by
   * {@code (j,f,t)}, under the maximum number of errors, {@code n}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param s Either {@code 0} or {@code 1}, depending on whether the first
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @param t Either {@code 0} or {@code 1}, depending on whether the second
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param n Maximum number of errors allowed in spelling candidates.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   * @throws AssertionError When {@code (i,e,s)} does not subsume {@code (j,f,t)}.
   */
  public SubsumesFunctionAssertions subsumesAt(
      final int i, final int e, final int s,
      final int j, final int f, final int t,
      final int n) {

    isNotNull();

    if (!actual.at(i, e, s, j, f, t, n)) {
      failWithMessage(
        "Expected position [%d,%d,%d] to subsume position [%d,%d,%d], "+
        "under the constaint [n <= %d]",
        i, e, s, j, f, t, n);
    }

    return this;
  }

  /**
   * Asserts-that the transposition or merge-and-split, Levenshtein state
   * represented by {@code (i,e,s)} does not subsume the other, represented by
   * {@code (j,f,t)}, under the maximum number of errors, {@code n}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param s Either {@code 0} or {@code 1}, depending on whether the first
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @param t Either {@code 0} or {@code 1}, depending on whether the second
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param n Maximum number of errors allowed in spelling candidates.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   * @throws AssertionError When {@code (i,e,s)} subsumes {@code (j,f,t)}.
   */
  public SubsumesFunctionAssertions doesNotSubsumeAt(
      final int i, final int e, final int s,
      final int j, final int f, final int t,
      final int n) {

    isNotNull();

    if (actual.at(i, e, s, j, f, t, n)) {
      failWithMessage(
        "Did not expect position [%d,%d,%d] to subsume position [%d,%d,%d], "+
        "under the constaint [n <= %d]",
        i, e, s, j, f, t, n);
    }

    return this;
  }

  /**
   * Asserts-that the transposition or merge-and-split, Levenshtein state
   * represented by {@code (i,e,s)} subsumes the other, represented by
   * {@code (j,f,t)}, under the maximum number of errors, {@code n}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param s Either {@code 0} or {@code 1}, depending on whether the first
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @param t Either {@code 0} or {@code 1}, depending on whether the second
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param n Maximum number of errors allowed in spelling candidates.
   * @param shouldSubsume Whether {@code (i,e,s)} should subsume {@code (j,f,t)}.
   * @return This {@link SubsumesFunctionAssertions} for fluency.
   */
  public SubsumesFunctionAssertions subsumesAt(
      final int i, final int e, final int s,
      final int j, final int f, final int t,
      final int n,
      final boolean shouldSubsume) {

    if (shouldSubsume) {
      return subsumesAt(i, e, s, j, f, t, n);
    }

    return doesNotSubsumeAt(i, e, s, j, f, t, n);
  }
}
