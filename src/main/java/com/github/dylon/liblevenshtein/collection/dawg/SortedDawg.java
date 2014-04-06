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
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.factory.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.ITransitionFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class SortedDawg extends AbstractDawg {

  /** Transitions that have not been checked for redundancy */
  Deque<Transition<DawgNode>> uncheckedTransitions = new ArrayDeque<>();

  /** Nodes that have been checked for redundancy */
  Map<DawgNode,DawgNode> minimizedNodes = new HashMap<>();

  /** References the term that was last added */
  String previousTerm = "";

  /** Builds (and recycles for memory efficiency) Transition objects */
  ITransitionFactory<DawgNode> transitionFactory;

  /**
   * Constructs a new SortedDawg instance.
   * @param factory Manages instances of DAWG nodes
   * @param terms Collection of terms to add to this dictionary. This is assumed
   * to be sorted ascendingly, because the behavior of the current DAWG
   * implementation is unstable if it is not.
   */
  public SortedDawg(
      @NonNull final IPrefixFactory<DawgNode> prefixFactory,
      @NonNull final IDawgNodeFactory<DawgNode> factory,
      @NonNull final ITransitionFactory<DawgNode> transitionFactory,
      @NonNull Collection<String> terms) {
    super(prefixFactory, factory);
    this.transitionFactory = transitionFactory;
    if (!addAll(terms)) {
      throw new IllegalStateException("Failed to add all terms");
    }
    finish();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(@NonNull final String term) {
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
      uncheckedTransitions.push(transitionFactory.build(node, label, nextNode));
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
  }

  private void minimize(final int lowerBound) {
    // Proceed from the leaf up to a certain point
    for (int j = uncheckedTransitions.size(); j > lowerBound; --j) {
      final Transition<DawgNode> transition = uncheckedTransitions.pop();
      final DawgNode source = transition.source();
      final char label = transition.label();
      final DawgNode target = transition.target();
      final DawgNode existing = minimizedNodes.get(target);
      if (null != existing) {
        if (finalNodes.contains(target)) {
          finalNodes.add(existing);
          finalNodes.remove(target);
        }
        source.addEdge(label, existing);
        factory.recycle(target);
      }
      else {
        source.addEdge(label, target);
        minimizedNodes.put(target, target);
      }
      transitionFactory.recycle(transition);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(final Object object) {
    throw new UnsupportedOperationException(
        "SortedDawg does not support removing terms");
  }
}
