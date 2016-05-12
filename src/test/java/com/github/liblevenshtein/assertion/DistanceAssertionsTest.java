package com.github.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.distance.IDistance;

import static com.github.liblevenshtein.assertion.DistanceAssertions.assertThat;

public class DistanceAssertionsTest {

  private static final String FOO = "foo";

  private static final String BAR = "bar";

  private static final String BAZ = "baz";

  private final ThreadLocal<IDistance<String>> distance = new ThreadLocal<>();

  @BeforeMethod
  @SuppressWarnings("unchecked")
  public void setUp() {
    distance.set(mock(IDistance.class));
  }

  @Test
  public void testEqualSelfSimilarity() {
    when(distance.get().between(FOO, FOO)).thenReturn(0);
    when(distance.get().between(BAR, BAR)).thenReturn(0);
    assertThat(distance.get()).satisfiesEqualSelfSimilarity(FOO, BAR);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testEqualSelfSimilarityAgainstDifferingDistances() {
    when(distance.get().between(FOO, FOO)).thenReturn(0).thenReturn(1);
    assertThat(distance.get()).satisfiesEqualSelfSimilarity(FOO, FOO);
  }

  @Test
  public void testMinimality() {
    when(distance.get().between(FOO, FOO)).thenReturn(0);
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    when(distance.get().between(BAR, FOO)).thenReturn(3);
    assertThat(distance.get()).satisfiesMinimality(FOO, BAR);
  }

  @DataProvider(name = "minimalityViolations")
  public Object[][] minimalityViolations() {
    return new Object[][] {
      {1, 0, 2},
      {1, 2, 0},
    };
  }

  @Test(dataProvider = "minimalityViolations",
        expectedExceptions = AssertionError.class)
  public void testMinimalityAgainstViolations(
      final int d11,
      final int d12,
      final int d21) {
    when(distance.get().between(FOO, FOO)).thenReturn(d11);
    when(distance.get().between(FOO, BAR)).thenReturn(d12);
    when(distance.get().between(BAR, FOO)).thenReturn(d21);
    assertThat(distance.get()).satisfiesMinimality(FOO, BAR);
  }

  @Test
  public void testSymmetry() {
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    when(distance.get().between(BAR, FOO)).thenReturn(3);
    assertThat(distance.get()).satisfiesSymmetry(FOO, BAR);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testSymmetryAgainstAsymmetricDistances() {
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    when(distance.get().between(BAR, FOO)).thenReturn(2);
    assertThat(distance.get()).satisfiesSymmetry(FOO, BAR);
  }

  @Test
  public void testTriangleInequality() {
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    when(distance.get().between(FOO, BAZ)).thenReturn(3);
    when(distance.get().between(BAR, BAZ)).thenReturn(1);
    assertThat(distance.get()).satisfiesTriangleInequality(FOO, BAR, BAZ);
  }

  @DataProvider(name = "triangleInequalityViolations")
  public Object[][] triangleInequalityViolations() {
    return new Object[][] {
      {1, 1, 3},
      {1, 3, 1},
      {3, 1, 1},
    };
  }

  @Test(dataProvider = "triangleInequalityViolations",
        expectedExceptions = AssertionError.class)
  public void testTriangleInequalityAgainstViolations(
      final int d12,
      final int d13,
      final int d23) {
    when(distance.get().between(FOO, BAR)).thenReturn(d12);
    when(distance.get().between(FOO, BAZ)).thenReturn(d13);
    when(distance.get().between(BAR, BAZ)).thenReturn(d23);
    assertThat(distance.get()).satisfiesTriangleInequality(FOO, BAR, BAZ);
  }

  @Test
  public void testHasDistance() {
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    assertThat(distance.get()).hasDistance(3, FOO, BAR);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasDistanceAgainstViolation() {
    when(distance.get().between(FOO, BAR)).thenReturn(3);
    assertThat(distance.get()).hasDistance(2, FOO, BAR);
  }
}
