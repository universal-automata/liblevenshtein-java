package com.github.liblevenshtein.collection.dictionary;

import java.io.Serializable;

import lombok.Value;

/**
 * Links two nodes together, under a character label.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Value
public class Transition implements Serializable {

  private static final long serialVersionUID = 1L;

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
  private DawgNode source;

  /**
   * Label mapping {@link #source} to {@link #target}.
   * -- GETTER --
   * Label mapping {@link #source} to {@link #target}.
   * @return Label mapping {@link #source} to {@link #target}.
   * -- SETTER --
   * Label mapping {@link #source} to {@link #target}.
   * @param label Label mapping {@link #source} to {@link #target}.
   * @return This {@link Transition} for fluency.
   */
  private char label;

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
  private DawgNode target;
}
