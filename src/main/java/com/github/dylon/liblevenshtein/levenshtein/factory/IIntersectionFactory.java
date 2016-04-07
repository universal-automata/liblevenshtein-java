package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;

import com.github.dylon.liblevenshtein.levenshtein.Intersection;
import com.github.dylon.liblevenshtein.levenshtein.IState;

/**
 * Builds nodes that help intersect a dictionary automaton with a Levenshtein
 * automaton.
 * @param <DictionaryNode> Kind of the nodes in the dictionary automaton.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IIntersectionFactory<DictionaryNode> extends Serializable {

  static final long serialVersionUID = 1L;

  /**
   * Constructs a new Intersection between two states: one from the dictionary
   * and one from the Levenshtein automaton.
   * @param candidate Substring of the candidate term from the dictionary
   * (spelling candidate of the query term).
   * @param dictionaryNode Dictionary node representing the final character in
   * the candidate term.
   * @param levenshteinState Location within the Levenshtein automaton of this
   * intersection.
   * @return New intersection node between {@code dictionaryNode} and
   * {@code levenshteinState}, having the term-prefix, {@code candidate}.
   */
  Intersection<DictionaryNode> build(
      String candidate,
      DictionaryNode dictionaryNode,
      IState levenshteinState);

  /**
   * [Optional Operation] Instructs this factory that the intersection will no
   * longer be used and may be recycled for reuse.
   * @param intersection Value to recycle.
   */
  default void recycle(Intersection<DictionaryNode> intersection) {
    // default behavior is to do nothing
  }
}
