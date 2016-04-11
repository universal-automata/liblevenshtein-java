package com.github.dylon.liblevenshtein.assertion;

import java.util.Arrays;

import org.assertj.core.api.AbstractAssert;

import com.github.dylon.liblevenshtein.levenshtein.State;

/**
 * AssertJ-style assertions for {@link State}.
 */
public class StateAssertions extends AbstractAssert<StateAssertions, State> {

	/**
	 * Constructs a new {@link StateAssertions} to assert-against.
	 * @param actual {@link State} to assert-against.
	 */
	public StateAssertions(final State actual) {
		super(actual, StateAssertions.class);
	}

	/**
	 * Constructs a new {@link StateAssertions} to assert-against.
	 * @param actual {@link State} to assert-against.
	 * @return A new {@link StateAssertions} to assert-against. 
	 */
	public static StateAssertions assertThat(final State actual) {
		return new StateAssertions(actual);
	}

	/**
	 * Asserts that the actual size is expected.
	 * @param expectedSize Size the state is expected to have.
	 * @return This {@link StateAssertions} for fluency.
	 * @throws AssertionError When the state has an unexpected size.
	 */
	public StateAssertions hasSize(final int expectedSize) {
		isNotNull();

		if (expectedSize != actual.size()) {
			failWithMessage(
				"Expected state.size() = [%d], but was [%d]",
				expectedSize, actual.size());
		}

		return this;
	}

	/**
	 * Asserts that the inner value of the state at the given index is expected.
	 * @param innerIndex Index of the inner value to compare.
	 * @param expectedInner Expected value of the inner index.
	 * @return This {@link StateAssertions} for fluency.
	 * @throws AssertionError When the actual value is unexpected.
	 */
	public StateAssertions hasInner(final int innerIndex, final int[] expectedInner) {
		isNotNull();

		final int[] actualInner = actual.getInner(innerIndex);
		if (!Arrays.equals(actualInner, expectedInner)) {
			failWithMessage(
				"Expected state.getInner(%d) = [%s], but was [%s]",
				innerIndex, Arrays.toString(expectedInner), Arrays.toString(actualInner));
		}

		return this;
	}

	/**
	 * Asserts that the outer value of the state at the given index is expected.
	 * @param outerIndex Index of the outer value to compare.
	 * @param expectedOuter Expected value of the outer index.
	 * @return This {@link StateAssertions} for fluency.
	 * @throws AssertionError When the actual value is unexpected.
	 */
	public StateAssertions hasOuter(final int outerIndex, final int[] expectedOuter) {
		isNotNull();

		final int[] actualOuter = actual.getOuter(outerIndex);
		if (!Arrays.equals(actualOuter, expectedOuter)) {
			failWithMessage(
				"Expected state.getOuter(%d) = [%s], but was [%s]",
				outerIndex, Arrays.toString(expectedOuter), Arrays.toString(actualOuter));
		}

		return this;
	}

	/**
	 * Asserts that the inner value returned from {@link State#removeInner()} is
	 * expected.
	 * @param expectedInner Expected return value from {@link State#removeInner()}
	 * @return This {@link StateAssertions} for fluency.
	 * @throws AssertionError When the return value of {State#removeInner()} is
	 * unexpected.
	 */
	public StateAssertions removeInner(final int[] expectedInner) {
		isNotNull();

		final int[] actualInner = actual.removeInner();

		if (!Arrays.equals(expectedInner, actualInner)) {
			failWithMessage(
				"Expected state.removeInner() to return [%s], but returned [%s]",
				Arrays.toString(expectedInner), Arrays.toString(actualInner));
		}

		return this;
	}
}
