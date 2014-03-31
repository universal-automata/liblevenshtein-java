package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharSet;

import com.github.dylon.liblevenshtein.collection.IDawgNode;

/**
 * Element of a DAWG structure (Directed Acyclic Word Graph)
 * @author Dylon Edwards
 * @since 2.1.0
 */
@RequiredArgsConstructor
public class DawgNode implements IDawgNode<DawgNode> {

  /** Outgoing edges of this node */
  @NonNull private final Char2ObjectMap<DawgNode> edges;

  /**
   * {@inheritDoc}
   */
  @Override
  public CharSet labels() {
    return edges.keySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode transition(final char label) {
    return edges.get(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode addEdge(final char label, final DawgNode target) {
    edges.put(label, target);
    return this;
  }
}
