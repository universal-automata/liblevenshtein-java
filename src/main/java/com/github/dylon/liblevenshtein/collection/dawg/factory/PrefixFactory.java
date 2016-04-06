package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.io.Serializable;
import java.util.Queue;
import java.util.ArrayDeque;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;

import com.github.dylon.liblevenshtein.collection.dawg.Prefix;

/**
 * Builds {@link Prefix}es that build the terms in a DAWG structure.
 * @param <DictionaryNode> Kind of nodes encapsulated by the {@link Prefix}es.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class PrefixFactory<DictionaryNode> implements IPrefixFactory<DictionaryNode>, Serializable {

	private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  public Prefix<DictionaryNode> build(DictionaryNode node, String value) {
    val prefix = new Prefix<DictionaryNode>();
    return prefix.node(node).value(value);
  }
}
