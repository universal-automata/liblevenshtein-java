package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.util.Collection;

import com.github.dylon.liblevenshtein.collection.dawg.IDawg;
import com.github.dylon.liblevenshtein.collection.dawg.IDawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.SortedDawg;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawgFactory
  <Node extends IDawgNode<Node>, SortedDawg extends IDawg<Node>> {

  /**
   * Returns a new DAWG.
   * @param terms Terms to insert into the DAWG
   * @return A new DAWG, containing the terms.
   */
  SortedDawg build(Collection<String> terms);
}
