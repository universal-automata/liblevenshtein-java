package com.github.dylon.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.dylon.liblevenshtein.levenshtein.IDistance;

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
	 * @return A new {@link DistanceAssertions} that asserts-against the distance
	 * function
	 */
	public static <Type> DistanceAssertions<Type> assertThat(
			final IDistance<Type> actual) {
		return new DistanceAssertions<>(actual);
	}

	/**
	 * Asserts that the distance function satisfies equal self-similarity.
	 * @param term_1 First term for the comparison.
	 * @param term_2 Second term for the comparison.
	 * @return This {@link DistanceAssertions} for fluency.
	 * @throws AssertionError When {@code d(term_1, term_2) != d(term_2, term_1)}
	 */
	public DistanceAssertions<Type> satisfiesEqualSelfSimilarity(
			final Type term_1,
			final Type term_2) {

		isNotNull();

    final int d_1 = actual.between(term_1, term_1);
    final int d_2 = actual.between(term_2, term_2);

    if (d_1 != d_2) {
    	failWithMessage(
    		"Expected d(%s, %s) = [%d] to be d(%s, %s) = [%d]",
    		term_1, term_2, d_1, term_2, term_1, d_2);
    }

		return this;
	}

	/**
	 * Asserts that the distance function satisfies minimality, such that for all
	 * terms {@code term_1 != term_2}, we have that
	 * {@code d(term_1, term_2) > d(term_1, term_1)} and that
	 * {@code d(term_2, term_1) > d(term_1, term_1)}.
	 * @param term_1 First term for the comparison.
	 * @param term_2 Second term for the comparison.
	 * @return This {@link DistanceAssertions} for fluency.
	 * @throws AssertionError When {@code d(term_1, term_2) <= d(term_1, term_1)}
	 * or when {@code d(term_2, term_1) <= d(term_1, term_1)}.
	 */
	public DistanceAssertions<Type> satisfiesMinimality(
			final Type term_1,
			final Type term_2) {

		isNotNull();

		if (null != term_1 && term_1.equals(term_2)) {
			failWithMessage("Did not expect term_1 [%s] to be equal-to term_2 [%s]",
				term_1, term_2);
		}

    final int d_11 = actual.between(term_1, term_1);
    final int d_12 = actual.between(term_1, term_2);
    final int d_21 = actual.between(term_2, term_1);

    if (d_12 <= d_11) {
    	failWithMessage(
    		"Expected d(%s, %s) = [%d] > d(%s, %s) = [%d]",
    		term_1, term_2, d_12, term_1, term_1, d_11);
    }

    if (d_21 <= d_11) {
    	failWithMessage(
    		"Expected d(%s, %s) = [%d] > d(%s, %s) = [%d]",
    		term_2, term_1, d_21, term_1, term_1, d_11);
    }

    return this;
	}

	/**
	 * Asserts that the distance function satisfies symmetry, such that for all
	 * terms term_1, term_2 we have that
	 * {@code d(term_1, term_2) = d(term_2, term_1)}.
	 * @param term_1 First term for the comparison.
	 * @param term_2 Second term for the comparison.
	 * @return This {@link DistanceAssertions} for fluency.
	 * @throws AssertionError When {@code d(term_1, term_2) != d(term_2, term_1)}
	 */
	public DistanceAssertions<Type> satisfiesSymmetry(
			final Type term_1,
			final Type term_2) {

		isNotNull();

    final int d_12 = actual.between(term_1, term_2);
    final int d_21 = actual.between(term_2, term_1);

    if (d_12 != d_21) {
    	failWithMessage("Expected d(%s, %s) = [%d] to be equal-to d(%s, %s) = [%d]",
    		term_1, term_2, d_12, term_2, term_1, d_21);
    }

		return this;
	}

	/**
	 * Asserts that the distance function satisfies the triangle inequality, such that for all
	 * terms term_1, term_2, term_3 we have that
	 * {@code d(term_1, term_2) + d(term_1, term_3) < d(term_2, term_3)}
	 * when terms term_1, term_2, term_3 are not colinear, and only
	 * {@code d(term_1, term_2) + d(term_1, term_3) = d(term_2, term_3)}
	 * when they are colinear.
	 * @param term_1 First term for the comparison.
	 * @param term_2 Second term for the comparison.
	 * @param term_3 Third term for the comparison.
	 * @return This {@link DistanceAssertions} for fluency.
	 * @throws AssertionError When any of the following hold:
	 * <ol>
	 *   <li>{@code d(term_1, term_2) + d(term_1, term_3) < d(term_2, term_3)}</li>
	 *   <li>{@code d(term_1, term_2) + d(term_2, term_3) < d(term_1, term_3)}</li>
	 *   <li>{@code d(term_1, term_3) + d(term_2, term_3) < d(term_1, term_2)}</li>
	 * </ol>
	 */
	public DistanceAssertions<Type> satisfiesTriangleInequality(
			final Type term_1,
			final Type term_2,
			final Type term_3) {

		isNotNull();

    final int d_12 = actual.between(term_1, term_2);
    final int d_13 = actual.between(term_1, term_3);
    final int d_23 = actual.between(term_2, term_3);

    if (d_12 + d_13 < d_23) {
    	failWithMessage(
    		"Expected (d(%s, %s) = [%d] + d(%s, %s) = [%d]) = [%d] >= d(%s, %s) = [%d]",
    		term_1, term_2, d_12, term_1, term_3, d_13, (d_12 + d_13), term_2, term_3, d_23);
    }

    if (d_12 + d_23 < d_13) {
    	failWithMessage(
    		"Expected (d(%s, %s) = [%d] + d(%s, %s) = [%d]) = [%d] >= d(%s, %s) = [%d]",
    		term_1, term_2, d_12, term_2, term_3, d_23, (d_12 + d_23), term_1, term_3, d_13);
    }

    if (d_13 + d_23 < d_12) {
    	failWithMessage(
    		"Expected (d(%s, %s) = [%d] + d(%s, %s) = [%d]) = [%d] >= d(%s, %s) = [%d]",
    		term_1, term_3, d_13, term_2, term_3, d_23, (d_13 + d_23), term_1, term_2, d_12);
    }

    return this;
	}

	/**
	 * Asserts that the distance between terms term_1, term_2 is expected.
	 * @param expectedDistance Distance asserted-against
	 * @param term_1 First term for the comparison.
	 * @param term_2 Second term for the comparison.
	 * @return This {@link DistanceAssertions} for fluency.
	 * @throws AssertionError When {@code d(term_1, term_2) != expectedDistance}
	 */
	public DistanceAssertions<Type> hasDistance(
			final int expectedDistance,
			final Type term_1,
			final Type term_2) {

		isNotNull();

		final int actualDistance = actual.between(term_1, term_2);

		if (expectedDistance != actualDistance) {
			failWithMessage("Expected d(%s, %s) = [%d], but was [%d]",
				term_1, term_2, expectedDistance, actualDistance);
		}

		return this;
	}
}
