package com.github.dylon.liblevenshtein.collection.dawg;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharIterator;

/**
 * Element of a DAWG structure (Directed Acyclic Word Graph).  Currently, this
 * is tightly-coupled with character-node types.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@AllArgsConstructor
public class DawgNode implements IDawgNode<DawgNode>, Serializable {

  private static final long serialVersionUID = 1L;

  /** Outgoing edges of this node. */
  @NonNull private final Char2ObjectMap<DawgNode> edges;

  /**
   * Specifies whether this node represents the last character of some term.
   */
  @Getter(onMethod = @_(@Override))
  private boolean isFinal = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public CharIterator labels() {
    return edges.keySet().iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode transition(final char label) {
    return edges.get(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode addEdge(final char label, final DawgNode target) {
    edges.put(label, target);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    edges.clear();
  }
}
