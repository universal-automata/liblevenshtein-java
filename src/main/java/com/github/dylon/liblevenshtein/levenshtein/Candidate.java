package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Value;

/**
 * POJO returned when the distances are requested of the candidate terms from
 * the query term, that are returned from the transducer.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
public class Candidate {

	/**
	 * Candidate term from the dictionary automaton.
	 * @return Candidate term from the dictionary automaton.
	 */
  String term;

	/**
	 * Distance between the candidate term and the query term.
	 * @return Distance between the candidate term and the query term.
	 */
  int distance;
}
