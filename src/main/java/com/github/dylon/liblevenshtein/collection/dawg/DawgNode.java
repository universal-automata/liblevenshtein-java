package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharIterator;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Element of a DAWG structure (Directed Acyclic Word Graph).  Currently, this
 * is tightly-coupled with character-node types.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@RequiredArgsConstructor
public class DawgNode implements IDawgNode<DawgNode> {

  /** Outgoing edges of this node */
  @NonNull private final Char2ObjectMap<DawgNode> edges;

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

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof DawgNode)) return false;

    final DawgNode other = (DawgNode) o;

    if (edges.size() != other.edges.size()) return false;

    for (val entry : edges.char2ObjectEntrySet()) {
      final char label = entry.getCharKey();
      final DawgNode target = entry.getValue();
      final DawgNode otherTarget = other.transition(label);
      if (null == otherTarget) return false;
      if (!target.equals(otherTarget)) return false;
    }

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    final StringBuilder buffer = new StringBuilder();
    buffer.append("DawgNode{");
    boolean first = true;
    for (val entry : edges.char2ObjectEntrySet()) {
      final char label = entry.getCharKey();
      final DawgNode target = entry.getValue();
      if (!first) buffer.append(',');
      else first = false;
      buffer.append(label);
      buffer.append("->");
      buffer.append(target);
    }
    buffer.append("}");
    return buffer.toString();
  }
}
