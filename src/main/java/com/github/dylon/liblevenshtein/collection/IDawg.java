package com.github.dylon.liblevenshtein.collection;

import java.util.Iterator;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IDawg
  <Node extends IDawgNode<Node>, Dawg extends IDawg<Node,Dawg>> {

  /**
   * Inserts a term into this DAWG
   * @param term The term to insert
   * @return A DAWG containing term
   */
  Dawg insert(String term);

  /**
   * Inserts all of a set of terms into this DAWG
   * @param terms The terms to insert into this DAWG
   * @return A DAWG containing terms
   */
  Dawg insertAll(Iterator<String> terms);

  /**
   * [Optional Operation] Removes a term from this DAWG
   * @param term The term to remove
   * @return A DAWG with the term removed
   */
  Dawg remove(String term);

  /**
   * [Optional Operation] Removes all of a set of terms from this DAWG
   * @param terms Set of terms to remove
   * @return A DAWG with the terms removed
   */
  Dawg removeAll(Iterator<String> terms);

  /**
   * [Optional Operation] Replaces the String, current, with another.
   * @param current String in this DAWG to replace
   * @param replacement String to replace the current one with
   * @return A DAWG with the current string replaced
   */
  Dawg replace(String current, String replacement);

  /**
   * Returns the number of terms in this DAWG.
   * @return The number of terms in this DAWG.
   */
  int size();

  /**
   * Returns the root node of this DAWG.
   * @return The root node of this DAWG.
   */
  Node root();

  /**
   * Determines whether term is contained, explicitly, within this DAWG.
   * @param term Term to examine for existence within this DAWG
   * @return Whether term is in this DAWG
   */
  boolean contains(String term);
}
