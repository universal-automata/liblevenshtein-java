package com.github.dylon.liblevenshtein.assertion;

import java.util.Arrays;
import java.util.Iterator;

import org.testng.annotations.Test;

import static com.github.dylon.liblevenshtein.assertion.IteratorAssertions.assertThat;

public class IteratorAssertionsTest {

	@Test
	public void testOperations() {
		assertThat(iter("foo", "bar", "baz"))
			.hasNext("foo")
			.hasNext("bar")
			.hasNext("baz")
			.doesNotHaveNext();

		assertThat(iter("foo", "bar", "baz"))
			.isEqualTo(iter("foo", "bar", "baz"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasNextAgainstViolation() {
		assertThat(iter()).hasNext();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasNextValueAgainstViolation() {
		assertThat(iter("foo")).hasNext("bar");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testDoesNotHaveNextAgainstViolation() {
		assertThat(iter("foo")).doesNotHaveNext();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIsEqualsToAgainstDifferingValues() {
		assertThat(iter("foo", "bar", "baz"))
			.isEqualTo(iter("foo", "bar", "qux"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIsEqualToAgainstTooFewValues() {
		assertThat(iter("foo", "bar"))
			.isEqualTo(iter("foo", "bar", "baz"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIsEqualToAgainstTooManyValues() {
		assertThat(iter("foo", "bar", "baz"))
			.isEqualTo(iter("foo", "bar"));
	}

	private Iterator<String> iter(final String... values) {
		return Arrays.asList(values).iterator();
	}
}
