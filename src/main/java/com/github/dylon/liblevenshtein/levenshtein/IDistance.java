package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Specifies the interface that all distance functions must implement.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDistance<Term> {

	/**
	 * Finds the distance between two terms, v and w. The distance between two
	 * terms is complemented by their similarity, which is determined by
	 * subtracting their distance from the maximum distance they may be apart (in
	 * most cases, this is probably 1.0).
	 * @param v Term to compare with w
	 * @param w Term to compare with v
	 * @return Distance between v and w
	 */
	int between(Term v, Term w);
}
