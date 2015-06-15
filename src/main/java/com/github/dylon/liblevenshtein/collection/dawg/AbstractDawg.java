package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.factory.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class AbstractDawg
    extends AbstractSet<String>
    implements IDawg<DawgNode>,
               IFinalFunction<DawgNode>,
               ITransitionFunction<DawgNode> {

  /** Manages instances of DAWG nodes */
  IDawgNodeFactory<DawgNode> factory;

  /**
   * Builds and recycles prefix objects, which are used to generate terms from
   * the dictionary's root.
   */
  final IPrefixFactory<DawgNode> prefixFactory;

  /**
   * Maintains which nodes represent the final characters of strings in the
   * dictionary.
   */
  final Set<DawgNode> finalNodes = Sets.<DawgNode> newIdentityHashSet();

  @Getter(onMethod=@_({@Override}))
  final DawgNode root;

  @Getter(onMethod=@_({@Override}))
  int size = 0;

  /**
   * Constructs a new AbstractDawg instance.
   * @param factory Manages instances of DAWG nodes
   * @param terms Collection of terms to add to this dictionary. This is assumed
   * to be sorted ascendingly, because the behavior of the current DAWG
   * implementation is unstable if it is not.
   */
  public AbstractDawg(
      @NonNull final IPrefixFactory<DawgNode> prefixFactory,
      @NonNull final IDawgNodeFactory<DawgNode> factory) {
    this.prefixFactory = prefixFactory;
    this.factory = factory;
    this.root = factory.build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean at(final DawgNode node) {
    return finalNodes.contains(node);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode of(final DawgNode node, final char label) {
    return node.transition(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharIterator of(final DawgNode node) {
    return node.labels();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract boolean add(final String term);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final Collection<? extends String> terms) {
    for (final String term : terms) {
      if (!add(term)) return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract boolean remove(final Object term);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final Object o) {
    if (!(o instanceof String)) return false;
    @SuppressWarnings("unchecked")
    final String term = (String) o;
    DawgNode node = root;
    for (int i = 0; i < term.length() && null != node; ++i) {
      final char label = term.charAt(i);
      node = node.transition(label);
    }
    return null != node && finalNodes.contains(node);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof AbstractDawg)) return false;
    @SuppressWarnings("unchecked")
    final AbstractDawg other = (AbstractDawg) o;
    final Queue<Pair<DawgNode,DawgNode>> nodes = new ArrayDeque<>();
    nodes.offer(ImmutablePair.of(root, other.root));

    while (!nodes.isEmpty()) {
      final Pair<DawgNode,DawgNode> pair = nodes.poll();
      final DawgNode node = pair.getLeft();
      final DawgNode otherNode = pair.getRight();

      if (finalNodes.contains(node) != other.finalNodes.contains(otherNode)) {
        return false;
      }

      final CharIterator labels = node.labels();
      final CharIterator otherLabels = otherNode.labels();

      while (labels.hasNext() && otherLabels.hasNext()) {
        final char label = labels.nextChar();
        final char otherLabel = otherLabels.nextChar();

        if (label != otherLabel) {
          return false;
        }

        nodes.offer(
            ImmutablePair.of(
              node.transition(label),
              otherNode.transition(label)));
      }

      if (labels.hasNext() || otherLabels.hasNext()) {
        return false;
      }
    }

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final HashCodeBuilder builder = new HashCodeBuilder(3013, 8225);
    builder.append(finalNodes.contains(root));
    final Queue<DawgNode> nodes = new ArrayDeque<>();
    nodes.offer(root);
    while (!nodes.isEmpty()) {
      final DawgNode node = nodes.poll();
      final CharIterator iter = node.labels();
      while (iter.hasNext()) {
        final char label = iter.nextChar();
        final DawgNode target = node.transition(label);
        builder.append(label);
        builder.append(finalNodes.contains(target));
        nodes.offer(target);
      }
    }
    return builder.toHashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<String> iterator() {
    return new DawgIterator(prefixFactory, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean replace(String current, final String replacement) {
    throw new UnsupportedOperationException("replace is not supported");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean replaceAll(Collection<? extends Map.Entry<String,String>> c) {
    throw new UnsupportedOperationException("replaceAll is not supported");
  }
}
