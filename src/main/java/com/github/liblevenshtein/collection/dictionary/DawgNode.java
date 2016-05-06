package com.github.liblevenshtein.collection.dictionary;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.chars.CharIterator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Non-final element of a DAWG structure (Directed Acyclic Word Graph).
 * Currently, this is tightly-coupled with character-node types.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@AllArgsConstructor
public class DawgNode implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Outgoing edges of this node.
   * -- GETTER --
   * Outgoing edges of this node.
   * @return Outgoing edges of this node.
   */
  @NonNull
  protected final Char2ObjectMap<DawgNode> edges;

  /**
   * Constructs a non-final {@link DawgNode}.
   */
  public DawgNode() {
    this(new Char2ObjectRBTreeMap<>());
  }

  /**
   * Specifies whether this node represents the last character of some term.
   * @return Whether this node represents the last character of some term.
   */
  public boolean isFinal() {
    return false;
  }

  /**
   * Returns the labels of the outgoing edges of this node.
   * @return Labels of the outgoing edges of this node.
   */
  public CharIterator labels() {
    return edges.keySet().iterator();
  }

  /**
   * Accepts a label and returns the outgoing transition corresponding to it.
   * @param label Identifier of the outgoing transition to return
   * @return Outgoing transition corresponding to the label
   */
  public DawgNode transition(final char label) {
    return edges.get(label);
  }

  /**
   * Adds an edge to the outgoing edges of this DAWG node.
   * @param label Identifier of the edge
   * @param target Neighbor receiving the directed edge
   * @return A DAWG node having the new edge
   */
  public DawgNode addEdge(final char label, final DawgNode target) {
    edges.put(label, target);
    return this;
  }

  /**
   * Removes all outoing-edges.
   */
  public void clear() {
    edges.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object object) {
    if (null == object) {
      return false;
    }

    if (this == object) {
      return true;
    }

    if (!(object instanceof DawgNode)) {
      return false;
    }

    final DawgNode other = (DawgNode) object;

    return new EqualsBuilder()
      .append(edges, other.edges)
      .append(isFinal(), other.isFinal())
      .isEquals();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder(419, 181)
      .append(edges)
      .append(isFinal())
      .toHashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("edges", edges)
      .append("isFinal", isFinal())
      .toString();
  }
}
