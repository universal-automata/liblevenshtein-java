package com.github.dylon.liblevenshtein.collection.dawg;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharIterator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Element of a DAWG structure (Directed Acyclic Word Graph).  Currently, this
 * is tightly-coupled with character-node types.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
public class DawgNode implements IDawgNode<DawgNode>, Serializable {

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
   * {@inheritDoc}
   */
  @Override
  public boolean isFinal() {
    return false;
  }

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
