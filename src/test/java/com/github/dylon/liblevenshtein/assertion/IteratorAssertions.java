package com.github.dylon.liblevenshtein.assertion;

import java.util.Iterator;

import org.assertj.core.api.AbstractAssert;

/**
 * AssertJ-style assertions for {@link Iterator}s.
 */
public class IteratorAssertions<Type>
		extends AbstractAssert<IteratorAssertions<Type>, Iterator<Type>> {

	/**
	 * Constructs a new {@link IteratorAssertions} to assert-against.
	 * @param actual {@link Iterator} to assert-against.
	 */
	public IteratorAssertions(final Iterator<Type> actual) {
		super(actual, IteratorAssertions.class);
	}

	/**
	 * Constructs a new {@link IteratorAssertions} to assert-against.
	 * @param actual {@link Iterator} to assert-against.
	 * @return A new {@link IteratorAssertions} to assert-against.
	 */
	public static <Type> IteratorAssertions<Type> assertThat(
			final Iterator<Type> actual) {
		return new IteratorAssertions<>(actual);
	}

	/**
	 * Asserts that the iterator has another element.
	 * @return This {@link IteratorAssertions} for fluency.
	 * @throws AssertionError When the iterator does not have another element.
	 */
	public IteratorAssertions<Type> hasNext() {
		isNotNull();

		if (!actual.hasNext()) {
			failWithMessage("Expected Iterator#hasNext() to be [true]");
		}

		return this;
	}

	/**
	 * Asserts that the iterator does not have another element.
	 * @return This {@link IteratorAssertions} for fluency.
	 * @throws AssertionError When the iterator has another element.
	 */
	public IteratorAssertions<Type> doesNotHaveNext() {
		isNotNull();

		if (actual.hasNext()) {
			failWithMessage("Did not expected Iterator#hasNext() to be [true]");
		}

		return this;
	}

	/**
	 * Asserts that the iterator has another element, and that the element is
	 * expected.
	 * @param expectedValue Next, expected value of the iterator.
	 * @return This {@link IteratorAssertions} for fluency.
	 * @throws AssertionError When the iterator does not have another element or
	 * when its next element is unexpected.
	 */
	public IteratorAssertions<Type> hasNext(final Type expectedValue) {
		isNotNull();
		hasNext();

		final Type actualValue = actual.next();

		if (null == expectedValue) {
			if (null != actualValue) {
				failWithMessage("Expected Iterator#next() to be [null], but was [%s]",
					actualValue);
			}
		}
		else if (!expectedValue.equals(actualValue)) {
			failWithMessage("Expected Iterator#next() to be [%s], but was [%s]",
				expectedValue, actualValue);
		}

		return this;
	}

	/**
	 * Asserts that the elements of the actual iterator are expected.
	 * @param expected {@link Iterator} containing the expected elements.
	 * @return This {@link IteratorAssertions} for fluency.
	 * @throws AssertionError When the values of the actual iterator are
	 * unexpected or it does not have the expected number of values.
	 */
	public IteratorAssertions<Type> isEqualTo(final Iterator<Type> expected) {
		isNotNull();

		while (actual.hasNext() && expected.hasNext()) {
			hasNext(expected.next());
		}

		if (expected.hasNext()) {
			failWithMessage("Expected iterator to have another element");
		}

		if (actual.hasNext()) {
			failWithMessage("Did not expect iterator to have another element");
		}

		return this;
	}
}
