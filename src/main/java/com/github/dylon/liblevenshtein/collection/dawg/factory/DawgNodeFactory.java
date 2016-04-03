package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.util.Queue;
import java.util.ArrayDeque;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;

import com.github.dylon.liblevenshtein.collection.dawg.DawgNode;

/**
 * Builds nodes for use in DAWG structures. This implementation uses an object
 * pool to avoid unnecessary memory consumption and garbage collection.
 *
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class DawgNodeFactory implements IDawgNodeFactory<DawgNode> {

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode build() {
    return build(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode build(final boolean isFinal) {
    final Char2ObjectMap<DawgNode> edges = new Char2ObjectRBTreeMap<>();
    return new DawgNode(edges, isFinal);
  }
}
