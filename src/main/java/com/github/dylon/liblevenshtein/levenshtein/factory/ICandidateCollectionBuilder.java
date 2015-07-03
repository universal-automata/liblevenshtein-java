package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.ICandidateCollection;

/**
 * Builds collections that hold spelling candidates for some query term.
 * @param <Type> Kind of spelling candidates held by the collections.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ICandidateCollectionBuilder<Type> {

  /**
   * Specifies the maximum number of spelling candidates to hold in a
   * collection.
   * @param maxCandidates maximum number of spelling candidates to hold in a
   * collection
   * @return This {@link ICandidateCollectionBuilder} for fluent setters.
   */
  ICandidateCollectionBuilder<Type> maxCandidates(int maxCandidates);

  /**
   * Builds a new collection of spelling candidate.
   * @return New instance of {@link ICandidateCollectionBuilder}.
   */
  ICandidateCollection<Type> build();
}
