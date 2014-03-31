package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.NonNull;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;

import com.github.dylon.liblevenshtein.collection.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.IFinalFunction;

/**
 * Builds nodes for use in DAWG structures. This implementation uses an object
 * pool to avoid unnecessary memory consumption and garbage collection.
 *
 * WARNING: This implementation is not thread-safe.
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
    return build(new Char2ObjectOpenHashMap<DawgNode>());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode build(@NonNull final Char2ObjectMap<DawgNode> edges) {
    return new DawgNode(edges);
  }
}
