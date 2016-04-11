package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;

import com.github.dylon.liblevenshtein.levenshtein.Candidate;

/**
 * Builds spelling candidates of the requested type, optionally including the
 * distance of the candidate from the query term.
 * @param <CandidateType> Kind of spelling candidate built by this factory.
 * @author Dylon Edwards
 * @since 2.1.2
 */
@RequiredArgsConstructor
public abstract class CandidateFactory<CandidateType>
    implements ICandidateFactory<CandidateType>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds instances of {@link Candidate}, with the dictionary term and its
   * Levenshtein distance from the query term.
   * @author Dylon Edwards
   * @since 2.1.2
   */
  public static class WithDistance extends CandidateFactory<Candidate> {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public Candidate build(final String term, final int distance) {
      return new Candidate(term, distance);
    }
  }

  /**
   * Does not include the Levenshtein distance, but returns the dictionary term
   * alone.
   * @author Dylon Edwards
   * @since 2.1.2
   */
  public static class WithoutDistance extends CandidateFactory<String> {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public String build(final String term, final int distance) {
      return term;
    }
  }
}
