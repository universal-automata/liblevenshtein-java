package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.io.Serializable;

import com.github.dylon.liblevenshtein.collection.dawg.Prefix;

/**
 * Builds instances of {@link Prefix}.
 * @param <DictionaryNode> Kind of node enclosed by the prefixes.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IPrefixFactory<DictionaryNode> extends Serializable {

	static final long serialVersionUID = 1L;

  /**
   * Builds or recycles a {@link Prefix} instance, representing {@code node} and
   * the string built by traversing its trie from the root to itself, collecting
   * the characters along the way.
   * @param node Node that the {@link Prefix} represents.
   * @param value String representing the prefix of the term, from the root to
   * {@code node}.
   * @return A {@link Prefix} representing {@code node} and containing the
   * prefix of the term from the root to {@code node}.
   */
  Prefix<DictionaryNode> build(DictionaryNode node, String value);

  /**
   * Recycles a {@link Prefix} to be re-used in {@link #build(Object,String) build}.
   * @param prefix The {@link Prefix} to recycle.  Note that once you recycle
   * {@code prefix}, you should discard it.
   */
  default void recycle(Prefix<DictionaryNode> prefix) {
    // default behavior is to do nothing
  }
}
