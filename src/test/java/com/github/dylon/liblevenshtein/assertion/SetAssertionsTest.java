package com.github.dylon.liblevenshtein.assertion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import static com.github.dylon.liblevenshtein.assertion.SetAssertions.assertThat;

public class SetAssertionsTest {

	@Test
	public void testContains() {
		assertThat(set(1,2,3)).contains(2);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testContainsAgainstInvalid() {
		assertThat(set(1,2,3)).contains(4);
	}

	@Test
	public void testDoesNotContain() {
		assertThat(set(1,2,3)).doesNotContain(4);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testDoesNotContainAgainstValid() {
		assertThat(set(1,2,3)).doesNotContain(2);
	}

	@Test
	public void testSize() {
		assertThat(set(1,2,3)).hasSize(3);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testSizeAgainstInvalid() {
		assertThat(set(1,2,3)).hasSize(2);
	}

	@Test
	public void testIsEmpty() {
		assertThat(set()).isEmpty();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIsEmptyAgainstNonEmpty() {
		assertThat(set(1,2,3)).isEmpty();
	}

	@Test
	public void testIsNotEmpty() {
		assertThat(set(1)).isNotEmpty();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIsNotEmptyAgainstEmpty() {
		assertThat(set()).isNotEmpty();
	}

	@Test
	public void testIsEqualTo() {
		assertThat(set(1,2,3))
			.isEqualTo(set(1,2,3))
			.isEqualTo(set(2,3,1))
			.isEqualTo(set(3,1,2))
			.isEqualTo(set(2,1,3))
			.isEqualTo(set(1,3,2))
			.isEqualTo(set(3,2,1))
			.isEqualTo(set(1,1,2,2,3,3));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIsEqualToAgainNotEqual() {
		assertThat(set(1,2,3)).isEqualTo(set(3,4,5));
	}

	private Set<Integer> set(final Integer... values) {
		return new HashSet<>(Arrays.asList(values));
	}
}
