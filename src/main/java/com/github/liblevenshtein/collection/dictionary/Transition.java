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
   */
  private DawgNode source;

  /**
   * Label mapping {@link #source} to {@link #target}.
   */
  private char label;

  /**
   * Node to which the transition is going.
   */
  private DawgNode target;
}
