package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.util.Queue;
import java.util.ArrayDeque;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.collection.dawg.Prefix;

/**
 * Builds {@link Prefix}es that build the terms in a DAWG structure.
 * @param <DictionaryNode> Kind of nodes encapsulated by the {@link Prefix}es.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class PrefixFactory<DictionaryNode> implements IPrefixFactory<DictionaryNode> {

  /**
   * Object pool of recycled {@link Prefix}es.
   */
  Queue<Prefix<DictionaryNode>> prefixes = new ArrayDeque<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public Prefix<DictionaryNode> build(DictionaryNode node, String value) {
    Prefix<DictionaryNode> prefix = prefixes.poll();

    if (null == prefix) {
      prefix = new Prefix<DictionaryNode>();
    }

    return prefix.node(node).value(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recycle(Prefix<DictionaryNode> prefix) {
    prefix.node(null).value(null);
    prefixes.offer(prefix);
  }
}
