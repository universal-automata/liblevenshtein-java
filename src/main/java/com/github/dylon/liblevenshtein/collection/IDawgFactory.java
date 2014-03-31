package com.github.dylon.liblevenshtein.collection;

import java.util.Iterator;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawgFactory
  <Node extends IDawgNode<Node>, Dawg extends IDawg<Node,Dawg>> {

  /**
   * Returns a new DAWG.
   * @param terms Terms to insert into the DAWG
   * @return A new DAWG, containing the terms.
   */
  Dawg build(Iterator<String> terms);
}
