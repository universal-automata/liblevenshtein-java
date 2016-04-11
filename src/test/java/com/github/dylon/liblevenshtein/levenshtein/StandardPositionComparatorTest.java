package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.Test;

import static com.github.dylon.liblevenshtein.assertion.ComparatorAssertions.assertThat;
import static com.github.dylon.liblevenshtein.utils.ArrayUtils.arr;

public class StandardPositionComparatorTest {
  private final StandardPositionComparator comparator = new StandardPositionComparator();

  @Test
  public void testCompare() {
    assertThat(comparator)
      .comparesGreaterThan(arr(1,2), arr(0,1))
      .comparesGreaterThan(arr(1,2), arr(1,0))
      .comparesEqualTo(arr(1,2), arr(1,2))
      .comparesLessThan(arr(1,2), arr(2,3))
      .comparesLessThan(arr(1,2), arr(1,3));
  }
}
