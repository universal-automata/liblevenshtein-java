package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Creates a linked list that can be used to traverse an {@link IDawg}
 * structure.
 * @param <DictionaryNode> Kind of node accepted by this {@link Prefix}.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Prefix<DictionaryNode> {

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
  DictionaryNode node;

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
  String value;
}
