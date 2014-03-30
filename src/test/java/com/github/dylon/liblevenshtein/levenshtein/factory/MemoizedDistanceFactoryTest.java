package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.IDistance;
import com.github.dylon.liblevenshtein.levenshtein.IDistanceFactory;

public class MemoizedDistanceFactoryTest {
	private List<String> terms;
	private IDistanceFactory<String> factory;

	@BeforeClass
	public void readLoremIpsum() {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(
					new InputStreamReader(
						getClass().getResourceAsStream(
							"/resources/top-10-most-common-english-words.txt")));

			final List<String> terms = new ArrayList<String>();

			String term;
			while ((term = reader.readLine()) != null) {
				terms.add(term); }

			this.terms = terms;
			this.factory = new MemoizedDistanceFactory();
		}
		catch (final IOException exception) {
			System.err.println(exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}
	}

	@DataProvider(name = "equalSelfSimilarityData")
	public Iterator<Object[]> equalSelfSimilarityData() {
		return new EqualSelfSimilarityDataIterator(factory, terms);
	}

	@DataProvider(name = "minimalityData")
	public Iterator<Object[]> minimalityData() {
		return new MinimalityDataIterator(factory, terms);
	}

	@DataProvider(name = "symmetryData")
	public Iterator<Object[]> symmetryData() {
		return new SymmetryDataIterator(factory, terms);
	}

	@DataProvider(name = "triangleInequalityData")
	public Iterator<Object[]> triangleInequalityData() {
		return new TriangleInequalityDataIterator(factory, terms);
	}

	@DataProvider(name = "penaltyData")
	public Object[][] penaltyData() {
		return new Object[][] {
			{Algorithm.STANDARD, factory.build(Algorithm.STANDARD), 2, 2, 2},
			{Algorithm.TRANSPOSITION, factory.build(Algorithm.TRANSPOSITION), 1, 2, 2},
			{Algorithm.MERGE_AND_SPLIT, factory.build(Algorithm.MERGE_AND_SPLIT), 2, 1, 1}
		};
	}

	@Test(dataProvider = "equalSelfSimilarityData")
	public void testEqualSelfSimilarity(
			final Algorithm algorithm,
			final IDistance<String> distance,
			final String term_1,
			final String term_2) {
		final int d_1 = distance.between(term_1, term_1);
		final int d_2 = distance.between(term_2, term_2);
		assertEquals(d_1, d_2);
	}

	@Test(dataProvider = "minimalityData")
	public void testSatisfyMinimality(
			final Algorithm algorithm,
			final IDistance<String> distance,
			final String term_1,
			final String term_2) {

		final int d_11 = distance.between(term_1, term_1);
		final int d_12 = distance.between(term_1, term_2);
		final int d_21 = distance.between(term_2, term_1);

		try {
			assertTrue(d_12 > d_11);
			assertTrue(d_21 > d_11);
		}
		catch (final AssertionError exception) {
			System.err.printf("d_11=%d, d_12=%d, d_21=%d%n", d_11, d_12, d_21);
			throw exception;
		}
	}

	@Test(dataProvider = "symmetryData")
	public void testSymmetry(
			final Algorithm algorithm,
			final IDistance<String> distance,
			final String term_1,
			final String term_2) {
		final int d_12 = distance.between(term_1, term_2);
		final int d_21 = distance.between(term_2, term_1);
		assertEquals(d_12, d_21);
	}

	@Test(dataProvider = "triangleInequalityData")
	public void testTriangleInequality(
			final Algorithm algorithm,
			final IDistance<String> distance,
			final String term_1,
			final String term_2,
			final String term_3) {

		final int d_12 = distance.between(term_1, term_2);
		final int d_13 = distance.between(term_1, term_3);
		final int d_23 = distance.between(term_2, term_3);

		try {
			assertTrue(d_12 + d_13 >= d_23);
			assertTrue(d_12 + d_23 >= d_13);
			assertTrue(d_13 + d_23 >= d_12);
		}
		catch (final AssertionError exception) {
			System.err.printf("d_12=%d, d_13=%d, d_23=%d%n", d_12, d_13, d_23);
			throw exception;
		}
	}

	@Test(dataProvider = "penaltyData")
	public void testPenalties(
			final Algorithm algorithm,
			final IDistance<String> distance,
			final int transpositionPenalty,
			final int mergePenalty,
			final int splitPenalty) {
		assertEquals(distance.between("foo", "foo"), 0);
		assertEquals(distance.between("foo", "food"), 1);
		assertEquals(distance.between("foo", "fodo"), 1);
		assertEquals(distance.between("foo", "fdoo"), 1);
		assertEquals(distance.between("foo", "dfoo"), 1);
		assertEquals(distance.between("foo", "oo"), 1);
		assertEquals(distance.between("foo", "fo"), 1);
		assertEquals(distance.between("foo", "boo"), 1);
		assertEquals(distance.between("foo", "fbo"), 1);
		assertEquals(distance.between("foo", "fob"), 1);
		assertEquals(distance.between("foo", "ofo"), transpositionPenalty);
		assertEquals(distance.between("clog", "dog"), mergePenalty);
		assertEquals(distance.between("dog", "clog"), splitPenalty);
	}

	private static abstract class AbstractDataIterator implements Iterator<Object[]> {
		protected Object[] params;

		@Override
		public boolean hasNext() {
			advance();
			return null != params;
		}

		@Override
		public Object[] next() {
			advance();
			final Object[] params = this.params;
			this.params = null;
			return params;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		public abstract void advance();
	}

	private static class EqualSelfSimilarityDataIterator extends AbstractDataIterator {
		private final IDistanceFactory<String> factory;
		private final List<String> terms;
		private final Object[] buffer = new Object[4];
		private int i = 0;
		private int j = 0;
		private int k = 0;

		public EqualSelfSimilarityDataIterator(
				final IDistanceFactory<String> factory,
				final List<String> terms) {
			this.factory = factory;
			this.terms = terms;
			final Algorithm algorithm = Algorithm.values()[k ++];
			buffer[0] = algorithm;
			buffer[1] = factory.build(algorithm);
		}

		@Override
		public void advance() {
			if (null == params) {
				if (j < terms.size()) {
					buffer[2] = terms.get(i);
					buffer[3] = terms.get(j);
					params = buffer;
					j += 1;
				}
				else if (i + 1 < terms.size()) {
					i += 1;
					j = 0;
					advance();
				}
				else if (k + 1 < Algorithm.values().length) {
					i = 0;
					j = 0;
					k += 1;
					final Algorithm algorithm = Algorithm.values()[k];
					buffer[0] = algorithm;
					buffer[1] = factory.build(algorithm);
					advance();
				}
			}
		}
	}

	private static class MinimalityDataIterator extends AbstractDataIterator {
		private final IDistanceFactory<String> factory;
		private final List<String> terms;
		private final Object[] buffer = new Object[4];
		private int i = 0;
		private int j = 0;
		private int k = 0;

		public MinimalityDataIterator(
				final IDistanceFactory<String> factory,
				final List<String> terms) {
			this.factory = factory;
			this.terms = terms;
			final Algorithm algorithm = Algorithm.values()[k ++];
			buffer[0] = algorithm;
			buffer[1] = factory.build(algorithm);
		}

		@Override
		public void advance() {
			if (null == params) {
				if (j == i) {
					j += 1;
					advance();
				}
				else if (j < terms.size()) {
					buffer[2] = terms.get(i);
					buffer[3] = terms.get(j);
					params = buffer;
					j += 1;
				}
				else if (i + 1 < terms.size()) {
					i += 1;
					j = 0;
					advance();
				}
				else if (k + 1 < Algorithm.values().length) {
					i = 0;
					j = 0;
					k += 1;
					final Algorithm algorithm = Algorithm.values()[k];
					buffer[0] = algorithm;
					buffer[1] = factory.build(algorithm);
					advance();
				}
			}
		}
	}

	private static class SymmetryDataIterator extends AbstractDataIterator {
		private final IDistanceFactory<String> factory;
		private final List<String> terms;
		private Object[] buffer = new Object[4];
		int i = 0;
		int j = 0;
		int k = 0;

		public SymmetryDataIterator(
				final IDistanceFactory<String> factory,
				final List<String> terms) {
			this.factory = factory;
			this.terms = terms;
			final Algorithm algorithm = Algorithm.values()[k ++];
			buffer[0] = algorithm;
			buffer[1] = factory.build(algorithm);
		}

		@Override
		public void advance() {
			if (null == params) {
				if (j < terms.size()) {
					buffer[2] = terms.get(i);
					buffer[3] = terms.get(j);
					params = buffer;
					j += 1;
				}
				else if (i + 1 < terms.size()) {
					i += 1;
					j = 0;
					advance();
				}
				else if (k + 1 < Algorithm.values().length) {
					i = 0;
					j = 0;
					k += 1;
					final Algorithm algorithm = Algorithm.values()[k];
					buffer[0] = algorithm;
					buffer[1] = factory.build(algorithm);
					advance();
				}
			}
		}
	}

	private static class TriangleInequalityDataIterator extends AbstractDataIterator {
		private final IDistanceFactory<String> factory;
		private final List<String> terms;
		private Object[] buffer = new Object[5];
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;

		public TriangleInequalityDataIterator(
				final IDistanceFactory<String> factory,
				final List<String> terms) {
			this.factory = factory;
			this.terms = terms;
			final Algorithm algorithm = Algorithm.values()[l ++];
			buffer[0] = algorithm;
			buffer[1] = factory.build(algorithm);
		}

		@Override
		public void advance() {
			if (null == params) {
				if (j < terms.size()) {
					buffer[2] = terms.get(i);
					buffer[3] = terms.get(j);
					buffer[4] = terms.get(k);
					params = buffer;
					j += 1;
				}
				else if (i + 1 < terms.size()) {
					i += 1;
					j = 0;
					advance();
				}
				else if (k + 1 < terms.size()) {
					i = 0;
					j = 0;
					k += 1;
					advance();
				}
				else if (l + 1 < Algorithm.values().length) {
					i = 0;
					j = 0;
					k = 0;
					l += 1;
					final Algorithm algorithm = Algorithm.values()[l];
					buffer[0] = algorithm;
					buffer[1] = factory.build(algorithm);
					advance();
				}
			}
		}
	}
}
