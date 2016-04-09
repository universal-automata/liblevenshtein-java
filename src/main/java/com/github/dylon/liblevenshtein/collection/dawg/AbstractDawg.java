package com.github.dylon.liblevenshtein.collection.dawg;

import java.io.Serializable;
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
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.factory.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;

/**
 * Provides common logic for all my Dawg implementations.  Currently, there is
 * only the {@link SortedDawg} implementation, but I have plans for other kinds.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Slf4j
@ToString(of={"size", "root"}, callSuper=false)
@EqualsAndHashCode(of={"size", "root"}, callSuper=false)
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class AbstractDawg
    extends AbstractSet<String>
    implements IDawg<DawgNode>,
               IFinalFunction<DawgNode>,
               ITransitionFunction<DawgNode>,
               Serializable {

  private static final long serialVersionUID = 1L;

  /** Manages instances of DAWG nodes */
  volatile IDawgNodeFactory<DawgNode> factory;

  /**
   * Builds and recycles prefix objects, which are used to generate terms from
   * the dictionary's root.
   */
  volatile IPrefixFactory<DawgNode> prefixFactory;

  /**
   * Root node of this trie.
   * @return Root node of this trie.
   */
  @Getter(onMethod=@_({@Override}))
  final DawgNode root;

  /**
   * Number of terms in this trie.
   * @return Number of terms in this trie.
   */
  @Getter(onMethod=@_({@Override}))
  int size = 0;

  /**
   * Initializes an {@link AbstractDawg}.
   * @param prefixFactory Builds/Caches instances of {@link DawgNode} paths.
   * @param factory Builds/Caches {@link DawgNode} nodes.
   * @param root Root node of this DAWG.
   */
  protected AbstractDawg(
      final IPrefixFactory<DawgNode> prefixFactory,
      final IDawgNodeFactory<DawgNode> factory,
      final DawgNode root,
      final int size) {
    this.prefixFactory = prefixFactory;
    this.factory = factory;
    this.root = root;
    this.size = size;
  }

  /**
   * Initializes an {@link AbstractDawg}.
   * @param prefixFactory Builds/Caches instances of {@link DawgNode} paths.
   * @param factory Builds/Caches {@link DawgNode} nodes.
   */
  public AbstractDawg(
      final IPrefixFactory<DawgNode> prefixFactory,
      final IDawgNodeFactory<DawgNode> factory) {
    this(prefixFactory, factory, factory.build(), 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean at(final DawgNode node) {
    return node.isFinal();
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
    int counter = 0;
    for (final String term : terms) {
      if (++counter % 10_000 == 0) {
      	log.info("Added [{}] of [{}] terms", counter, terms.size());
      }
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
    return null != node && node.isFinal();
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
