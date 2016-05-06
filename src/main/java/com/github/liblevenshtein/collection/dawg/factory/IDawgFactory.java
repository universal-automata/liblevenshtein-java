package com.github.liblevenshtein.collection.dawg.factory;

import java.io.Serializable;
import java.util.Collection;

import com.github.liblevenshtein.collection.dawg.IDawg;
import com.github.liblevenshtein.collection.dawg.IDawgNode;
import com.github.liblevenshtein.collection.dawg.IFinalFunction;
import com.github.liblevenshtein.collection.dawg.ITransitionFunction;

/**
 * Constructs instances of {@link IDawgNode}, optionally using an object pool.
 * @param <Node> Kind of nodes that the DAWG requires.
 * @param <Dawg> Kind of DAWG that is built in this factory.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawgFactory
  <Node extends IDawgNode<Node>, Dawg extends IDawg<Node>>
  extends Serializable {

  /**
   * Returns a new DAWG.
   * @param terms Terms to insert into the DAWG
   * @return A new DAWG, containing the terms.
   */
  Dawg build(Collection<String> terms);

  /**
   * Returns a new DAWG.
   * @param terms Terms to insert into the DAWG
   * @param isSorted Whether terms has been sorted
   * @return A new DAWG, containing the terms.
   */
  Dawg build(Collection<String> terms, boolean isSorted);

  /**
   * Returns the final function of the dictionary.
   * @param dictionary Dawg whose final function should be returned
   * @return The final function of the dictionary
   */
  IFinalFunction<Node> isFinal(Dawg dictionary);

  /**
   * Returns the transition function of the dictionary.
   * @param dictionary Dawg whose transition function should be returned
   * @return The transition function of the dictionary
   */
  ITransitionFunction<Node> transition(Dawg dictionary);
}
