package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ElementTest {

	@Test
	public void testEqualsAndHashCode() {
		final Element<int[]> e1 = build(1,2, 2,3, 3,4);
		final Element<int[]> e2 = build(1,2, 2,3, 3,4);
		final Element<int[]> e3 = build(1,3, 2,4, 3,5);

		assertTrue(e1.equals(e1));
		assertTrue(e1.equals(e2));
		assertFalse(e1.equals(e3));

		assertEquals(e1.hashCode(), e2.hashCode());
		assertFalse(e1.hashCode() == e3.hashCode());
	}

	private Element<int[]> build(
			final int i1, final int e1,
			final int i2, final int e2,
			final int i3, final int e3) {

		final Element<int[]> element1 = new Element<>();
		element1.value(new int[] {i1, e1});

		final Element<int[]> element2 = new Element<>();
		element2.value(new int[] {i2, e2});

		final Element<int[]> element3 = new Element<>();
		element3.value(new int[] {i3, e3});

		element1.next(element2);
		element2.prev(element1);
		element2.next(element3);
		element3.prev(element2);
		return element2;
	}
}
