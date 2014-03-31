package com.github.dylon.liblevenshtein.collection;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

/**
 * Builds nodes for use in DAWG structures. Implementations may do such things
 * as manage object pools, etc.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawgNodeFactory<NodeType extends IDawgNode<NodeType>> {

  /**
   * Builds a DAWG node
   * @return A DAWG node
   * @throws NullPointerException When edges is null
   */
  NodeType build();
}
