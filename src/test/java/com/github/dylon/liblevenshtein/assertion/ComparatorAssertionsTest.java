package com.github.dylon.liblevenshtein.assertion;

import java.util.Comparator;

import org.testng.annotations.Test;

import static com.github.dylon.liblevenshtein.assertion.ComparatorAssertions.assertThat;

public class ComparatorAssertionsTest {

  private final Comparator<Integer> comparator = (i,j) -> Integer.compare(i,j);

  @Test
  public void testEqualsTo() {
    assertThat(comparator).comparesEqualTo(1,1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testEqualsToAgainstLessThan() {
    assertThat(comparator).comparesEqualTo(1,2);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testEqualsToAgainstGreaterThan() {
    assertThat(comparator).comparesEqualTo(2,1);
  }

  @Test
  public void testGreaterThan() {
    assertThat(comparator).comparesGreaterThan(2,1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testGreaterThanAgainstEqualsTo() {
    assertThat(comparator).comparesGreaterThan(1,1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testGreaterThanAgainstLessThan() {
    assertThat(comparator).comparesGreaterThan(1,2);
  }

  @Test
  public void testLessThan() {
    assertThat(comparator).comparesLessThan(1,2);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testLessThanAgainstEqualsTo() {
    assertThat(comparator).comparesLessThan(1,1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testLessThanAgainstGreaterThan() {
    assertThat(comparator).comparesLessThan(2,1);
  }

  @Test
  public void testGreaterThanOrEqualTo() {
    assertThat(comparator)
      .comparesGreaterThanOrEqualTo(2,1)
      .comparesGreaterThanOrEqualTo(1,1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testGreaterThanOrEqualToAgainstLessThan() {
    assertThat(comparator).comparesGreaterThanOrEqualTo(1,2);
  }

  @Test
  public void testLessThanOrEqualTo() {
    assertThat(comparator)
      .comparesLessThanOrEqualTo(1,2)
      .comparesLessThanOrEqualTo(1,1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testLessThanOrEqualToAgainstGreaterThan() {
    assertThat(comparator).comparesLessThanOrEqualTo(2,1);
  }
}
