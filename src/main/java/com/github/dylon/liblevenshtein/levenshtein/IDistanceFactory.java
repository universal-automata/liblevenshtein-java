package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDistanceFactory<Term> {

	IDistance<Term> build(Algorithm algorithm);
}
