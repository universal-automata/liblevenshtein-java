package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;

/**
 * Builds spelling candidates of the requested type, optionally including the
 * distance of the candidate from the query term.
 * @param <CandidateType> Kind of spelling candidate built by this factory.
 * @author Dylon Edwards
 * @since 2.1.2
 */
public interface ICandidateFactory<CandidateType> extends Serializable {

  /**
   * Builds a new spelling candidate from the dictionary term and its
   * Levenshtein distance from the query term.
   * @param term Candidate term from the dictionary.
   * @param distance Levenshtein distance of the dictionary term from the query
   * term.
   * @return A new spelling candidate, optionally with the distance included.
   */
  CandidateType build(String term, int distance);
}
