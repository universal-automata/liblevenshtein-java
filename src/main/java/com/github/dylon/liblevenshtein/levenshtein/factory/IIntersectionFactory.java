package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IIntersectionFactory<DictionaryNode> {

  /**
   * Constructs a new Intersection between two states: one from the dictionary
   * and one from the Levenshtein automaton.
   * @param candidate Substring of the candidate term from the dictionary
   * (spelling candidate of the query term).
   * @param dictionaryNode Dictionary node representing the final character in
   * the candidate term.
   * @param levenshteinState Location within the Levenshtein automaton of this
   * intersection.
   * @param distance Minimum distance between the query term and the candidate
   * substring.
   */
  Intersection<DictionaryNode> build(
      String candidate,
      DictionaryNode dictionaryNode,
      int[][] levenshteinState,
      int distance);

  /**
   * [Optional Operation] Instructs this factory that the intersection will no
   * longer be used and may be recycled for reuse.
   * @param intersection Value to recycle.
   */
  void recycle(Intersection<DictionaryNode> intersection);
}
