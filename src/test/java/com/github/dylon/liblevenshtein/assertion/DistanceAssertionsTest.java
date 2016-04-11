package com.github.dylon.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.IDistance;

import static com.github.dylon.liblevenshtein.assertion.DistanceAssertions.assertThat;

public class DistanceAssertionsTest {

	private IDistance<String> distance = null;

	@BeforeMethod
	@SuppressWarnings("unchecked")
	public void setUp() {
		distance = mock(IDistance.class);
	}

	@Test
	public void testEqualSelfSimilarity() {
		when(distance.between("foo", "foo")).thenReturn(0);
		when(distance.between("bar", "bar")).thenReturn(0);
		assertThat(distance).satisfiesEqualSelfSimilarity("foo", "bar");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testEqualSelfSimilarityAgainstDifferingDistances() {
		when(distance.between("foo", "foo")).thenReturn(0).thenReturn(1);
		assertThat(distance).satisfiesEqualSelfSimilarity("foo", "foo");
	}

	@Test
	public void testMinimality() {
		when(distance.between("foo", "foo")).thenReturn(0);
		when(distance.between("foo", "bar")).thenReturn(3);
		when(distance.between("bar", "foo")).thenReturn(3);
		assertThat(distance).satisfiesMinimality("foo", "bar");
	}

	@DataProvider(name = "minimalityViolations")
	public Object[][] minimalityViolations() {
		return new Object[][] {
			{1, 0, 2},
			{1, 2, 0}
		};
	}

	@Test(dataProvider = "minimalityViolations",
			  expectedExceptions = AssertionError.class)
	public void testMinimalityAgainstViolations(
			final int d_11,
			final int d_12,
			final int d_21) {
		when(distance.between("foo", "foo")).thenReturn(d_11);
		when(distance.between("foo", "bar")).thenReturn(d_12);
		when(distance.between("bar", "foo")).thenReturn(d_21);
		assertThat(distance).satisfiesMinimality("foo", "bar");
	}

	@Test
	public void testSymmetry() {
		when(distance.between("foo", "bar")).thenReturn(3);
		when(distance.between("bar", "foo")).thenReturn(3);
		assertThat(distance).satisfiesSymmetry("foo", "bar");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testSymmetryAgainstAsymmetricDistances() {
		when(distance.between("foo", "bar")).thenReturn(3);
		when(distance.between("bar", "foo")).thenReturn(2);
		assertThat(distance).satisfiesSymmetry("foo", "bar");
	}

	@Test
	public void testTriangleInequality() {
		when(distance.between("foo", "bar")).thenReturn(3);
		when(distance.between("foo", "baz")).thenReturn(3);
		when(distance.between("bar", "baz")).thenReturn(1);
		assertThat(distance).satisfiesTriangleInequality("foo", "bar", "baz");
	}

	@DataProvider(name = "triangleInequalityViolations")
	public Object[][] triangleInequalityViolations() {
		return new Object[][] {
			{1, 1, 3},
			{1, 3, 1},
			{3, 1, 1}
		};
	}

	@Test(dataProvider = "triangleInequalityViolations",
			  expectedExceptions = AssertionError.class)
	public void testTriangleInequalityAgainstViolations(
			final int d_12,
			final int d_13,
			final int d_23) {
		when(distance.between("foo", "bar")).thenReturn(d_12);
		when(distance.between("foo", "baz")).thenReturn(d_13);
		when(distance.between("bar", "baz")).thenReturn(d_23);
		assertThat(distance).satisfiesTriangleInequality("foo", "bar", "baz");
	}

	@Test
	public void testHasDistance() {
		when(distance.between("foo", "bar")).thenReturn(3);
		assertThat(distance).hasDistance(3, "foo", "bar");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasDistanceAgainstViolation() {
		when(distance.between("foo", "bar")).thenReturn(3);
		assertThat(distance).hasDistance(2, "foo", "bar");
	}
}
