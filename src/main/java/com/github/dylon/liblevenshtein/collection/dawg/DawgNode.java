package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharSet;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.github.dylon.liblevenshtein.collection.IDawgNode;

/**
 * Element of a DAWG structure (Directed Acyclic Word Graph)
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@RequiredArgsConstructor
public class DawgNode implements IDawgNode<DawgNode> {

  /** Uniquely-identifies this node within its context */
  @Getter private final long id;

  /** Outgoing edges of this node */
  @NonNull protected final Char2ObjectMap<DawgNode> edges;

  /** Whether this node represents the final character in a term */
  @Getter @Setter private boolean isFinal = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public CharSet labels() {
    return edges.keySet();
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
    if (target.id() == id) {
      throw new IllegalStateException("Cycle detected");
    }
    edges.put(label, target);
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof DawgNode)) {
      return false;
    }

    @SuppressWarnings("unchecked")
    final DawgNode other = (DawgNode) o;
    if (isFinal != other.isFinal) return false;
    if (edges.size() != other.edges.size()) return false;
    for (val entry : edges.char2ObjectEntrySet()) {
      final char label = entry.getCharKey();
      final DawgNode target = entry.getValue();
      final DawgNode otherTarget = other.transition(label);
      if (null == otherTarget) return false;
      if (target.id() != otherTarget.id()) return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    // NOTE: It looks like this gets called twice (consecutively) during
    // construction of the DAWG dictionary.
    // NOTE: An assumption is made that edges is sorted.
    final HashCodeBuilder builder = new HashCodeBuilder(8777, 4343);
    builder.append(isFinal);
    for (val entry : edges.char2ObjectEntrySet()) {
      final char label = entry.getCharKey();
      final DawgNode target = entry.getValue();
      builder.append(label);
      builder.append(target.id());
    }
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final StringBuilder buffer = new StringBuilder();
    buffer.append("DawgNode{");
    buffer.append("id=");
    buffer.append(id);
    buffer.append(',');
    buffer.append("isFinal=");
    buffer.append(isFinal);
    buffer.append(',');
    buffer.append("edges={");
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
    buffer.append("}}");
    return buffer.toString();
  }
}
