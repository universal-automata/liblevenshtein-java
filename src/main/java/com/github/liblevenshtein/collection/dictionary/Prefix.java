package com.github.liblevenshtein.collection.dictionary;

import java.io.Serializable;

import lombok.Data;

/**
 * Creates a linked list that can be used to traverse an {@link IDawg}
 * structure.
 * @param <DictionaryNode> Kind of node accepted by this {@link Prefix}.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
public class Prefix<DictionaryNode> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Current node in the trie.
   * -- GETTER --
   * Current node in the trie.
   * @return Current node in the trie.
   * -- SETTER --
   * Current node in the trie.
   * @param node Current node in the trie.
   * @return This {@link Prefix} for fluency.
   */
  private DictionaryNode node;

  /**
   * Value of the string built by traversing the DAWG from its root node to this
   * one, and accumulating the character values of the nodes along the way.
   * -- GETTER --
   * Value of the string built by traversing the DAWG from its root node to this
   * one, and accumulating the character values of the nodes along the way.
   * @return Value of the string built by traversing the DAWG from its root node
   * to this one, and accumulating the character values of the nodes along the
   * way.
   * -- SETTER --
   * Value of the string built by traversing the DAWG from its root node to this
   * one, and accumulating the character values of the nodes along the way.
   * @param value Value of the string built by traversing the DAWG from its root
   * node to this one, and accumulating the character values of the nodes along
   * the way.
   * @return This {@link Prefix} for fluency.
   */
  private String value;
}
