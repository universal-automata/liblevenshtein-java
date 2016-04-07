package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;

import lombok.Data;

/**
 * State along the intersection of the dictionary automaton and the Levenshtein
 * automaton.
 * @param <DictionaryNode> Kind of node in the dictionary automaton.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
public class Intersection<DictionaryNode> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Spelling candidate from the dictionary automaton, represented as the prefix
   * of a term in the dictionary constructed by traversing from its root to
   * {@link #dictionaryNode}, and collecting the characters along the way.
   * -- GETTER --
   * Spelling candidate from the dictionary automaton, represented as the prefix
   * of a term in the dictionary constructed by traversing from its root to
   * {@link #dictionaryNode}, and collecting the characters along the way.
   * @return Spelling candidate from the dictionary automaton
   * -- SETTER --
   * Spelling candidate from the dictionary automaton, represented as the prefix
   * of a term in the dictionary constructed by traversing from its root to
   * {@link #dictionaryNode}, and collecting the characters along the way.
   * @param candidate Spelling candidate from the dictionary automaton
   * @return This {@link Intersection} for fluency.
   */
  String candidate;

  /**
   * Current node in the dictionary, along the intersection's path.
   * -- GETTER --
   * Current node in the dictionary, along the intersection's path.
   * @return Current node in the dictionary, along the intersection's path.
   * -- SETTER --
   * Current node in the dictionary, along the intersection's path.
   * @param dictionaryNode Current node in the dictionary, along the
   * intersection's path.
   * @return This {@link Intersection} for fluency.
   */
  DictionaryNode dictionaryNode;

  /**
   * Current node in the Levenshtein automaton, along the intersection's path.
   * -- GETTER --
   * Current node in the Levenshtein automaton, along the intersection's path.
   * @return Current node in the Levenshtein automaton, along the intersection's
   * path.
   * -- SETTER --
   * Current node in the Levenshtein automaton, along the intersection's path.
   * @param levenshteinState Current node in the Levenshtein automaton, along
   * the intersection's path.
   * @return This {@link Intersection} for fluency.
   */
  IState levenshteinState;
}
