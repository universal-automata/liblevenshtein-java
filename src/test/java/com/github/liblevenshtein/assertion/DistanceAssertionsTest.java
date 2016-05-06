package com.github.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.distance.IDistance;

import static com.github.liblevenshtein.assertion.DistanceAssertions.assertThat;

public class DistanceAssertionsTest {

  private final ThreadLocal<IDistance<String>> distance = new ThreadLocal<>();

  @BeforeMethod
  @SuppressWarnings("unchecked")
  public void setUp() {
    distance.set(mock(IDistance.class));
  }

  @Test
  public void testEqualSelfSimilarity() {
    when(distance.get().between("foo", "foo")).thenReturn(0);
    when(distance.get().between("bar", "bar")).thenReturn(0);
    assertThat(distance.get()).satisfiesEqualSelfSimilarity("foo", "bar");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testEqualSelfSimilarityAgainstDifferingDistances() {
    when(distance.get().between("foo", "foo")).thenReturn(0).thenReturn(1);
    assertThat(distance.get()).satisfiesEqualSelfSimilarity("foo", "foo");
  }

  @Test
  public void testMinimality() {
    when(distance.get().between("foo", "foo")).thenReturn(0);
    when(distance.get().between("foo", "bar")).thenReturn(3);
    when(distance.get().between("bar", "foo")).thenReturn(3);
    assertThat(distance.get()).satisfiesMinimality("foo", "bar");
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
    when(distance.get().between("foo", "foo")).thenReturn(d11);
    when(distance.get().between("foo", "bar")).thenReturn(d12);
    when(distance.get().between("bar", "foo")).thenReturn(d21);
    assertThat(distance.get()).satisfiesMinimality("foo", "bar");
  }

  @Test
  public void testSymmetry() {
    when(distance.get().between("foo", "bar")).thenReturn(3);
    when(distance.get().between("bar", "foo")).thenReturn(3);
    assertThat(distance.get()).satisfiesSymmetry("foo", "bar");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testSymmetryAgainstAsymmetricDistances() {
    when(distance.get().between("foo", "bar")).thenReturn(3);
    when(distance.get().between("bar", "foo")).thenReturn(2);
    assertThat(distance.get()).satisfiesSymmetry("foo", "bar");
  }

  @Test
  public void testTriangleInequality() {
    when(distance.get().between("foo", "bar")).thenReturn(3);
    when(distance.get().between("foo", "baz")).thenReturn(3);
    when(distance.get().between("bar", "baz")).thenReturn(1);
    assertThat(distance.get()).satisfiesTriangleInequality("foo", "bar", "baz");
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
    when(distance.get().between("foo", "bar")).thenReturn(d12);
    when(distance.get().between("foo", "baz")).thenReturn(d13);
    when(distance.get().between("bar", "baz")).thenReturn(d23);
    assertThat(distance.get()).satisfiesTriangleInequality("foo", "bar", "baz");
  }

  @Test
  public void testHasDistance() {
    when(distance.get().between("foo", "bar")).thenReturn(3);
    assertThat(distance.get()).hasDistance(3, "foo", "bar");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasDistanceAgainstViolation() {
    when(distance.get().between("foo", "bar")).thenReturn(3);
    assertThat(distance.get()).hasDistance(2, "foo", "bar");
  }
}
