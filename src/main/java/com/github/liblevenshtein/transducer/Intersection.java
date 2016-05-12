package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * State along the intersection of the dictionary automaton and the Levenshtein
 * automaton.
 * @param <DictionaryNode> Kind of node in the dictionary automaton.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
@AllArgsConstructor
public class Intersection<DictionaryNode> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Intersection along the prefix from the root, dictionary node to the one
   * prior to {@link dictionaryNode}.
   */
  private final Intersection<DictionaryNode> prevIntersection;

  /**
   * Label annotating the edge between the previous dictionary node and
   * {@link #dictionaryNode}.
   */
  private final char label;

  /**
   * Current node in the dictionary, along the intersection's path.
   */
  private final DictionaryNode dictionaryNode;

  /**
   * Current node in the Levenshtein automaton, along the intersection's path.
   */
  private final State levenshteinState;

  /**
   * Constructs an intersection representing the start states of both the
   * dictionary and Levenshtein automata.
   * @param dictionaryRoot Root node of the dictionary automaton.
   * @param initialState Initial state of the Levenshtein automaton.
   */
  public Intersection(
      final DictionaryNode dictionaryRoot,
      final State initialState) {
    this(null, '\0', dictionaryRoot, initialState);
  }

  /**
   * Spelling candidate from the dictionary automaton, represented as the prefix
   * of a term in the dictionary constructed by traversing from its root to
   * dictionaryNode, and collecting the characters along the way.
   * @return Spelling candidate from the dictionary automaton.
   */
  public String candidate() {
    return buffer().toString();
  }

  /**
   * Buffers the prefix built by traversion the path from the root node to
   * {@link dictionaryNode}.
   * @return The prefix from the root node to {@link dictionaryNode}.
   */
  private StringBuilder buffer() {
    final StringBuilder buffer;

    if (prevIntersection != null) {
      buffer = prevIntersection.buffer();
      buffer.append(label);
    }
    else {
      buffer = new StringBuilder();
    }

    return buffer;
  }
}
