package com.github.dylon.liblevenshtein.collection.dawg;

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
	private long id = 1;

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode build() {
    //final Char2ObjectMap<DawgNode> edges = new Char2ObjectOpenHashMap<>();
    //final Char2ObjectMap<DawgNode> edges = new Char2ObjectArrayMap<>();
		final Char2ObjectMap<DawgNode> edges = new Char2ObjectRBTreeMap<>();
    final DawgNode node = new DawgNode(id, edges);
    this.id += 1;
    return node;
  }
}
