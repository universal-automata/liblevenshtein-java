package com.github.dylon.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.IDistance;
import static com.github.dylon.liblevenshtein.assertion.CandidateAssertions.assertThat;

public class CandidateAssertionsTest {

	private IDistance<String> distance = null;

	@BeforeMethod
	@SuppressWarnings("unchecked")
	public void setUp() {
		this.distance = mock(IDistance.class);
	}

	@Test
	public void testHasDistance() {
		final Candidate candidate = new Candidate("bar", 3);
		when(distance.between("foo", "bar")).thenReturn(3);
		assertThat(candidate).hasDistance(distance, "foo");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasDistanceAgainstViolation() {
		final Candidate candidate = new Candidate("bar", 2);
		when(distance.between("foo", "bar")).thenReturn(3);
		assertThat(candidate).hasDistance(distance, "foo");
	}
}
