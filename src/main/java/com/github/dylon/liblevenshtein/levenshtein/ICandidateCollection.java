package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Describes the operations necessary for all collections that hold spelling
 * candidates for query terms.
 * @param <Type> Kind of the spelling candidates of this collection.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ICandidateCollection<Type> extends Iterable<Type> {

  /**
   * Adds a spelling candidate to this collection if it may be added.  Some
   * reasons it might not be added include if the collection has reached its
   * maximum size, or if the collection encapsulates a heap of candidates with
   * the shortest distance, whether the collection has reached its maximum size
   * and the term's distance is too great to replace one of the terms within.
   * @param term Spelling candidate to offer to this collection.
   * @param distance Levenshtein distance of the spelling candidate with the
   * query term.
   * @return Whether {@code term} was accepted into this collection.
   */
  boolean offer(String term, int distance);
}
