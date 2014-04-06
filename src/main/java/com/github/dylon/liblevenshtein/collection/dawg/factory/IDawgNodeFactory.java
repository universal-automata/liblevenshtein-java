package com.github.dylon.liblevenshtein.collection.dawg.factory;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

import com.github.dylon.liblevenshtein.collection.dawg.IDawgNode;

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

  /**
   * [Green Operation] In an effort to fight global warming and environmental
   * polution, you may invoke this method when you no longer have need of a node
   * so it may be recycled for future use.
   * @param node Node to recycle
   * @return This factory, for chaining method calls.
   */
  IDawgNodeFactory<NodeType> recycle(NodeType node);
}
