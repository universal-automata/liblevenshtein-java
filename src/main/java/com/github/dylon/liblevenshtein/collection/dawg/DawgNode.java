package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharIterator;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Element of a DAWG structure (Directed Acyclic Word Graph)
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

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof DawgNode)) {
      return false;
    }

    @SuppressWarnings("unchecked")
    final DawgNode other = (DawgNode) o;
    if (edges.size() != other.edges.size()) return false;
    for (val entry : edges.char2ObjectEntrySet()) {
      final char label = entry.getCharKey();
      final DawgNode target = entry.getValue();
      final DawgNode otherTarget = other.transition(label);
      if (null == otherTarget) return false;
      if (target != otherTarget) return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    // NOTE: It looks like this gets called twice (consecutively) during
    // construction of the DAWG dictionary.
    // NOTE: An assumption is made that edges is sorted.
    final HashCodeBuilder builder = new HashCodeBuilder(8777, 4343);
    for (val entry : edges.char2ObjectEntrySet()) {
      final char label = entry.getCharKey();
      final DawgNode target = entry.getValue();
      builder.append(label);
      builder.append(target);
    }
    return builder.toHashCode();
  }

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
