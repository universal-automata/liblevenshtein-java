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
   */
  private DawgNode source;

  /**
   * Label mapping {@link #source} to {@link #target}.
   * -- GETTER --
   * Label mapping {@link #source} to {@link #target}.
   * @return Label mapping {@link #source} to {@link #target}.
   */
  private char label;

  /**
   * Node to which the transition is going.
   * -- GETTER --
   * Node to which the transition is going.
   * @return This {@link Transition} for fluency.
   */
  private DawgNode target;
}
