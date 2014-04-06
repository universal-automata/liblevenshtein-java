package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawg <Node extends IDawgNode<Node>> extends Set<String> {

  /**
   * [Optional Operation] Replaces the String, current, with another.
   * @param current String in this DAWG to replace
   * @param replacement String to replace the current one with
   */
  boolean replace(String current, String replacement);

  /**
   * [Optional Operation] Replaces the String, current, with another.
   * @param current String in this DAWG to replace
   * @param replacement String to replace the current one with
   */
  boolean replaceAll(Collection<? extends Map.Entry<String,String>> c);

  /**
   * Returns the root node of this DAWG.
   * @return The root node of this DAWG.
   */
  Node root();
}
