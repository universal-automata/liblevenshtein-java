package com.github.dylon.liblevenshtein.collection;

import it.unimi.dsi.fastutil.chars.CharSet;

/**
 * Element of a DAWG structure (Directed Acyclic Word Graph)
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawgNode<Node extends IDawgNode<Node>> {

  /**
   * Returns the labels of the outgoing edges of this node
   * @return Labels of the outgoing edges of this node.
   */
  CharSet labels();

  /**
   * Accepts a label and returns the outgoing transition corresponding to it.
   * @param label Identifier of the outgoing transition to return
   * @return Outgoing transition corresponding to the label
   */
  Node transition(char label);

  /**
   * Adds an edge to the outgoing edges of this DAWG node.
   * @param label Identifier of the edge
   * @param target Neighbor receiving the directed edge
   * @return A DAWG node having the new edge
   */
  Node addEdge(char label, Node target);
}
