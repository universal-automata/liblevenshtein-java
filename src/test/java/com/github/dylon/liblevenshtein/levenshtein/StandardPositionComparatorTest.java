package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class StandardPositionComparatorTest {
	private final StandardPositionComparator comparator = new StandardPositionComparator();

	@Test
	public void testCompare() {
		assertTrue(comparator.compare(new int[] {1,2}, new int[] {0,1}) > 0);
		assertTrue(comparator.compare(new int[] {1,2}, new int[] {1,0}) > 0);
		assertTrue(comparator.compare(new int[] {1,2}, new int[] {1,2}) == 0);
		assertTrue(comparator.compare(new int[] {1,2}, new int[] {2,3}) < 0);
		assertTrue(comparator.compare(new int[] {1,2}, new int[] {1,3}) < 0);
	}
}
