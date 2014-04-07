package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class CaseInsensitiveDistanceAndTermComparator
	extends AbstractDistanceAndTermComparator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(final Candidate a, final Candidate b) {
		int c = a.distance() - b.distance();
		if (0 != c) return c;
		c = Math.abs(a.lowerTerm().compareTo(lowerTerm)) - Math.abs(b.lowerTerm().compareTo(lowerTerm));
		if (0 != c) return c;
		return a.lowerTerm().compareTo(b.lowerTerm());
	}
}
