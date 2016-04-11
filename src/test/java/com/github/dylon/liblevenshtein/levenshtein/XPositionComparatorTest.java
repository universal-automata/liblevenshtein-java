package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.Test;

import static com.github.dylon.liblevenshtein.assertion.ComparatorAssertions.assertThat;
import static com.github.dylon.liblevenshtein.utils.ArrayUtils.arr;

public class XPositionComparatorTest {
  private final XPositionComparator comparator = new XPositionComparator();

  @Test
  public void testCompare() {
    assertThat(comparator)
      .comparesGreaterThan(arr(1,2,2), arr(0,2,2))
      .comparesGreaterThan(arr(1,3,2), arr(1,2,2))
      .comparesGreaterThan(arr(1,3,3), arr(1,3,2))
      .comparesEqualTo(arr(1,3,3), arr(1,3,3))
      .comparesLessThan(arr(1,3,3), arr(2,3,3))
      .comparesLessThan(arr(2,3,3), arr(2,4,3))
      .comparesLessThan(arr(2,4,3), arr(2,4,4));
  }
}
