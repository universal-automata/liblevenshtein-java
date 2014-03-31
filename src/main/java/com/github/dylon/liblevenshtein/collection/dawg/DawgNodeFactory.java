package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.Queue;
import java.util.ArrayDeque;

import lombok.NonNull;

import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;

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
  private final Queue<DawgNode> queue = new ArrayDeque<>();
  private long id = 1;

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode build() {
    DawgNode node = queue.poll();
    if (null == node) {
      final Char2ObjectMap<DawgNode> edges = new Char2ObjectRBTreeMap<>();
      node = new DawgNode(id, edges);
      this.id += 1;
    }
    return node;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNodeFactory recycle(final DawgNode node) {
    node.edges.clear();
    node.isFinal(false);
    queue.offer(node);
    return this;
  }
}
