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

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.IDawg;
import com.github.dylon.liblevenshtein.collection.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.IFinalFunction;
import com.github.dylon.liblevenshtein.collection.IPrefixFactory;
import com.github.dylon.liblevenshtein.collection.ITransitionFunction;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
public class Dawg
    extends AbstractSet<String>
    implements IDawg<DawgNode>,
               IFinalFunction<DawgNode>,
               ITransitionFunction<DawgNode> {

  /** Manages instances of DAWG nodes */
  private IDawgNodeFactory<DawgNode> factory;

  /** Transitions that have not been checked for redundancy */
  private Deque<Transition> uncheckedTransitions = new ArrayDeque<>();

  /** Nodes that have been checked for redundancy */
  private Map<DawgNode,DawgNode> minimizedNodes = new HashMap<>();

  /**
   * Builds and recycles prefix objects, which are used to generate terms from
   * the dictionary's root.
   */
  private final IPrefixFactory<DawgNode> prefixFactory;

  /**
   * Maintains which nodes represent the final characters of strings in the
   * dictionary.
   */
  private final Set<DawgNode> finalNodes = Sets.<DawgNode> newIdentityHashSet();

  @Getter(onMethod=@_({@Override}))
  private final DawgNode root;

  @Getter(onMethod=@_({@Override}))
  private int size = 0;

  /** References the term that was last added */
  private String previousTerm = "";

  /**
   * Constructs a new Dawg instance.
   * @param factory Manages instances of DAWG nodes
   * @param terms Collection of terms to add to this dictionary. This is assumed
   * to be sorted ascendingly, because the behavior of the current DAWG
   * implementation is unstable if it is not.
   */
  public Dawg(
      @NonNull final IPrefixFactory<DawgNode> prefixFactory,
      @NonNull final IDawgNodeFactory<DawgNode> factory,
      @NonNull Collection<String> terms) {
    this.prefixFactory = prefixFactory;
    this.factory = factory;
    this.root = factory.build();
    if (!addAll(terms)) {
      throw new IllegalStateException("Failed to add all terms");
    }
    finish();
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
  public boolean add(final String term) {
    if (null == minimizedNodes) {
      throw new IllegalStateException(
          "Cannot insert terms once this DAWG has been finalized");
    }

    if (term.compareTo(previousTerm) < 0) {
      throw new IllegalArgumentException(
          "Due to caveats with the current DAWG implementation, terms must be "+
          "inserted in ascending order");
    }

    final int upperBound = (term.length() < previousTerm.length())
      ? term.length()
      : previousTerm.length();

    // Find the length of the longest, common prefix between term and
    // previousTerm
    int i = 0;
    while (i < upperBound && term.charAt(i) == previousTerm.charAt(i)) {
      i += 1;
    }

    // Check the unchecked nodes for redundancy, proceeding from the last one
    // down to the common prefix size. Then, truncate the list at that point.
    minimize(i);

    // Add the suffix, starting from the correct node, mid-way through the graph
    DawgNode node = uncheckedTransitions.isEmpty()
      ? root
      : uncheckedTransitions.peek().target();

    while (i < term.length()) {
      final char label = term.charAt(i);
      final DawgNode nextNode = factory.build();
      uncheckedTransitions.push(new Transition(node, label, nextNode));
      node = nextNode;
      i += 1;
    }

    finalNodes.add(node);
    previousTerm = term;
    size += 1;
    return true;
  }

  private void finish() {
    minimize(0);
    factory = null;
    uncheckedTransitions = null;
    minimizedNodes = null;
    previousTerm = null;
  }

  private void minimize(final int lowerBound) {
    // Proceed from the leaf up to a certain point
    for (int j = uncheckedTransitions.size(); j > lowerBound; --j) {
      final Transition transition = uncheckedTransitions.pop();
      final DawgNode source = transition.source();
      final char label = transition.label();
      final DawgNode target = transition.target();
      final DawgNode existing = minimizedNodes.get(target);
      if (null != existing) {
        source.addEdge(label, existing);
        finalNodes.remove(target);
        factory.recycle(target);
      }
      else {
        source.addEdge(label, target);
        minimizedNodes.put(target, target);
      }
    }
  }

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
    if (!(o instanceof Dawg)) return false;
    @SuppressWarnings("unchecked")
    final Dawg other = (Dawg) o;
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

  @Value
  @Accessors(fluent=true)
  private static class Transition {
    DawgNode source;
    char label;
    DawgNode target;
  }
}
