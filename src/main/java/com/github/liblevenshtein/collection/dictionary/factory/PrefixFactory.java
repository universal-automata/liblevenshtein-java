package com.github.liblevenshtein.collection.dictionary.factory;

import java.io.Serializable;

import lombok.val;

import com.github.liblevenshtein.collection.dictionary.Prefix;

/**
 * Builds {@link Prefix}es that build the terms in a DAWG structure.
 * @param <DictionaryNode> Kind of nodes encapsulated by the {@link Prefix}es.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class PrefixFactory<DictionaryNode> implements IPrefixFactory<DictionaryNode>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  public Prefix<DictionaryNode> build(
      final DictionaryNode node,
      final String value) {
    val prefix = new Prefix<DictionaryNode>();
    return prefix.node(node).value(value);
  }
}
