package com.github.dylon.liblevenshtein.levenshtein.factory;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
import com.github.dylon.liblevenshtein.levenshtein.ICandidateCollection;

/**
 * Builds collections of spelling candidates.
 * @param <Type> Kind of spelling candidates in the collections (e.g.
 * {@link Candidate} or {@link String}).
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class CandidateCollectionBuilder<Type>
  implements ICandidateCollectionBuilder<Type> {

  /**
   * Maximum number of candidates to hold in the collection.
   * -- SETTER --
   * Maximum number of candidates to hold in the collection.
   * @param maxCandidates Maximum number of candidates to hold in the
   * collection.
   * @return This {@link CandidateCollectionBuilder} for fluency.
   */
  @Setter(onMethod=@_({@Override}))
  int maxCandidates = Integer.MAX_VALUE;

  /**
   * Implementation of {@link CandidateCollectionBuilder} when spelling
   * candidate distances from the query term are desired.  Spelling candidates
   * are returned as instances of {@link Candidate}.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class WithDistance extends CandidateCollectionBuilder<Candidate> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ICandidateCollection<Candidate> build() {
      return new CandidateCollection.WithDistance(maxCandidates);
    }
  }

  /**
   * Implementation of {@link CandidateCollectionBuilder} when spelling
   * candidate distances from the query term are not wanted.  Spelling
   * candidates are returned as instances of {@link String}.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class WithoutDistance extends CandidateCollectionBuilder<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ICandidateCollection<String> build() {
      return new CandidateCollection.WithoutDistance(maxCandidates);
    }
  }
}
