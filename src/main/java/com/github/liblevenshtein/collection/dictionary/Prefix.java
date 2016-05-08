package com.github.liblevenshtein.collection.dictionary;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Creates a linked list that can be used to traverse an
 * {@link com.github.liblevenshtein.collection.dictionary.Dawg} structure.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
@AllArgsConstructor
public class Prefix implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Current node in the trie.
   * -- GETTER --
   * Current node in the trie.
   * @return Current node in the trie.
   */
  private DawgNode node;

  /**
   * Sub-term prefix leading up to this one.  This is used to build the
   * {@link #value()} of the term once a final node is reached.
   * -- GETTER --
   * Sub-term prefix leading up to this one.  This is used to build the
   * {@link #value()} of the term once a final node is reached.
   * @return Sub-term prefix leading up to this one.
   */
  private Prefix prevNode;

  /**
   * Label along the edge from the predecessor to {@link #node} up to itself.
   * -- GETTER --
   * Label along the edge from the predecessor to {@link #node} up to itself.
   * @return Label along the edge from the predecessor to {@link #node} up to
   * itself.
   */
  private char label;

  /**
   * Builds a new {@link Prefix} with the initial node of a DAWG structure.  All
   * {@link Prefix}es should branch from this one.
   * @param rootNode Root of the dictionary automaton.
   */
  public Prefix(final DawgNode rootNode) {
    this(rootNode, null, '\0');
  }

  /**
   * Value of the string built by traversing the DAWG from its root node to this
   * one, and accumulating the character values of the nodes along the way.
   * @return Value of the string built by traversing the DAWG from its root node
   * to this one, and accumulating the character values of the nodes along the
   * way.
   */
  public String value() {
    return buffer().toString();
  }

  /**
   * Buffers the labels along the edges of the previous {@link Prefix}es,
   * beginning from the root node.
   * @return A buffer of the labels along the edges of the previous
   * {@link Prefix}es.
   */
  private StringBuilder buffer() {
    StringBuilder buffer;

    if (null != prevNode) {
      buffer = prevNode.buffer();
      buffer.append(label);
    }
    else {
      buffer = new StringBuilder();
    }

    return buffer;
  }
}
