package com.github.dylon.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.IDistance;

/**
 * AssertJ-style assertions for {@link Candidate}s.
 */
public class CandidateAssertions
		extends AbstractAssert<CandidateAssertions, Candidate> {

	/**
	 * Constructs a new {@link Candidate} to assert-against.
	 * @param actual {@link Candidate} to assert-against.
	 */
	public CandidateAssertions(final Candidate actual) {
		super(actual, CandidateAssertions.class);
	}

	/**
	 * Builds a new {@link Candidate} to assert-against.
	 * @param actual {@link Candidate} to assert-against.
	 * @return A new {@link Candidate} to assert-against.
	 */
	public static CandidateAssertions assertThat(final Candidate actual) {
		return new CandidateAssertions(actual);
	}

	/**
	 * Asserts that the actual distance is the expected distance from the query
	 * term, as specified by the distance function.
	 * @param distance Distance function for validation.
	 * @param queryTerm Term whose distance from the spelling candidate is to be
	 * determined.
	 * @return This {@link CandidateAssertions} for fluency.
	 * @throws AssertionError When the actual distance differs from what's
	 * expected.
	 */
	public CandidateAssertions hasDistance(
			final IDistance<String> distance,
			final String queryTerm) {

		isNotNull();

		final int expectedDistance = distance.between(queryTerm, actual.term());

		if (expectedDistance != actual.distance()) {
			failWithMessage(
				"Expected d(%s, %s) = [%d], but was [%d]",
				queryTerm, actual.term(), expectedDistance, actual.distance());
		}

		return this;
	}
}
