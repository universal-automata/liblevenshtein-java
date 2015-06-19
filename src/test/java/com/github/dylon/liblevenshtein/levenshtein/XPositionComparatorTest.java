package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class XPositionComparatorTest {
  private final XPositionComparator comparator = new XPositionComparator();

  @Test
  public void testCompare() {
    assertTrue(comparator.compare(new int[] {1,2,2}, new int[] {0,2,2}) > 0);
    assertTrue(comparator.compare(new int[] {1,3,2}, new int[] {1,2,2}) > 0);
    assertTrue(comparator.compare(new int[] {1,3,3}, new int[] {1,3,2}) > 0);
    assertTrue(comparator.compare(new int[] {1,3,3}, new int[] {1,3,3}) == 0);
    assertTrue(comparator.compare(new int[] {1,3,3}, new int[] {2,3,3}) < 0);
    assertTrue(comparator.compare(new int[] {2,3,3}, new int[] {2,4,3}) < 0);
    assertTrue(comparator.compare(new int[] {2,4,3}, new int[] {2,4,4}) < 0);
  }
}
