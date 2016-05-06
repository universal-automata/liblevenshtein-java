package com.github.liblevenshtein.collection.dictionary;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

/**
 * Final element of a DAWG structure (Directed Acyclic Word Graph).
 * Currently, this is tightly-coupled with character-node types.
 */
public class FinalDawgNode extends DawgNode {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new {@link FinalDawgNode}, which acts just like a
   * {@link DawgNode} except that {@link #isFinal()} returns true.
   * @param edges Outgoing edges of this node.
   */
  public FinalDawgNode(final Char2ObjectMap<DawgNode> edges) {
    super(edges);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFinal() {
    return true;
  }
}
