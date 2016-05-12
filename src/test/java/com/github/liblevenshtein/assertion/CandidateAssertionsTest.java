package com.github.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.distance.IDistance;
import com.github.liblevenshtein.transducer.Candidate;

import static com.github.liblevenshtein.assertion.CandidateAssertions.assertThat;

public class CandidateAssertionsTest {

  private static final String FOO = "foo";

  private static final String BAR = "bar";

  private final ThreadLocal<IDistance<String>> distance = new ThreadLocal<>();

  @BeforeMethod
  @SuppressWarnings("unchecked")
  public void setUp() {
    distance.set(mock(IDistance.class));
  }

  @Test
  public void testHasDistance() {
    final Candidate candidate = new Candidate(BAR, 3);
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    assertThat(candidate).hasDistance(distance.get(), FOO);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasDistanceAgainstViolation() {
    final Candidate candidate = new Candidate(BAR, 2);
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    assertThat(candidate).hasDistance(distance.get(), FOO);
  }
}
