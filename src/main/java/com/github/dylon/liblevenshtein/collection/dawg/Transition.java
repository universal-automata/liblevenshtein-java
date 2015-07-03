package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Links two nodes together, under a character label.
 * @param <NodeType> Kind of the nodes being linked together.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@NoArgsConstructor
public class Transition<NodeType extends IDawgNode<NodeType>> {

  /**
   * Node from which the transition is leaving.
   * -- GETTER --
   * Node from which the transition is leaving.
   * @return Node from which the transition is leaving.
   * -- SETTER --
   * Node from which the transition is leaving.
   * @param source Node from which the transition is leaving.
   * @return This {@link Transition} for fluency.
   */
  NodeType source;

  /**
   * Label mapping {@link #source} to {@link #target}
   * -- GETTER --
   * Label mapping {@link #source} to {@link #target}
   * @return Label mapping {@link #source} to {@link #target}
   * -- SETTER --
   * Label mapping {@link #source} to {@link #target}
   * @param label Label mapping {@link #source} to {@link #target}
   * @return This {@link Transition} for fluency.
   */
  char label;

  /**
   * Node to which the transition is going.
   * -- GETTER --
   * Node to which the transition is going.
   * @return This {@link Transition} for fluency.
   * -- SETTER --
   * Node to which the transition is going.
   * @param target Node to which the transition is going.
   * @return Node to which the transition is going.
   */
  NodeType target;
}
