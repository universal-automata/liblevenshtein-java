package com.github.dylon.liblevenshtein.collection;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawgFactory
  <Node extends IDawgNode<Node>, Dawg extends IDawg<Node,Dawg>> {

  /**
   * Returns a new DAWG.
   * @return A new DAWG.
   */
  Dawg build();
}
