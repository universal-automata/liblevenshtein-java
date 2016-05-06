package com.github.liblevenshtein.collection.dawg;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Describes the contract accepted by all DAWG implementations.
 * @param <Node> Kind of {@link IDawgNode} to build this trie with.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawg<Node extends IDawgNode<Node>> extends Set<String>, Serializable {

  /**
   * [Optional Operation] Replaces the String, current, with another.
   * @param current String in this DAWG to replace
   * @param replacement String to replace the current one with
   * @return Whether the replacement was successful.
   */
  boolean replace(String current, String replacement);

  /**
   * [Optional Operation] Replaces all instances of the term keys with their
   * values.
   * @param c Replacment mappings.
   * @return Whether all replacements were successful.
   */
  boolean replaceAll(Collection<? extends Map.Entry<String, String>> c);

  /**
   * Returns the root node of this DAWG.
   * @return The root node of this DAWG.
   */
  Node root();
}
