package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Iterator;

import lombok.val;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CandidateCollectionTest {

	@Test
	public void testWithoutDistance() {
		val candidates = new CandidateCollection.WithoutDistance(3);
		enqueueCandidates(candidates);

		final Iterator<String> iter = candidates.iterator();
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), "foo");
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), "bar");
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), "baz");
		assertFalse(iter.hasNext());
	}

	@Test
	public void testWithDistance() {
		val candidates = new CandidateCollection.WithDistance(3);
		enqueueCandidates(candidates);

		final Iterator<Candidate> iter = candidates.iterator();
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), new Candidate("foo", 1));
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), new Candidate("bar", 2));
		assertTrue(iter.hasNext());
		assertEquals(iter.next(), new Candidate("baz", 3));
		assertFalse(iter.hasNext());
	}

	private void enqueueCandidates(final CandidateCollection<?> candidates) {
		assertTrue(candidates.offer("foo", 1));
		assertTrue(candidates.offer("bar", 2));
		assertTrue(candidates.offer("baz", 3));
		assertFalse(candidates.offer("qux", 4));
	}
}
