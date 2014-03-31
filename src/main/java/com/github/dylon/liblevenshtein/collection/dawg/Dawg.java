package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import com.github.dylon.liblevenshtein.collection.IDawg;
import com.github.dylon.liblevenshtein.collection.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.IFinalFunction;
import com.github.dylon.liblevenshtein.collection.ITransitionFunction;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
public class Dawg
    implements IDawg<DawgNode,Dawg>,
               IFinalFunction<DawgNode>,
               ITransitionFunction<DawgNode> {

  private IDawgNodeFactory<DawgNode> factory;

  /** Transitions that have not been checked for redundancy */
  private Deque<Transition> uncheckedTransitions = new ArrayDeque<>();

  /** Nodes that have been checked for redundancy */
  private Map<DawgNode,DawgNode> minimizedNodes = new HashMap<>();

  @Getter(onMethod=@_({@Override}))
  private final DawgNode root;

  @Getter(onMethod=@_({@Override}))
  private int size = 0;

  private String previousTerm = "";

  public Dawg(
      @NonNull final IDawgNodeFactory<DawgNode> factory,
      @NonNull Iterator<String> terms) {
    this.factory = factory;
    this.root = factory.build();
    insertAll(terms);
    finish();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFinal(final DawgNode node) {
    return node.isFinal();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DawgNode transition(final DawgNode node, final char label) {
    return node.transition(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dawg insert(final String term) {
    if (null == minimizedNodes) {
      throw new IllegalStateException(
          "Cannot insert terms once this DAWG has been finalized");
    }

    if (term.compareTo(previousTerm) < 0) {
    	throw new IllegalArgumentException(
    			"Due to caveats with the current DAWG implementation, terms must be "+
    			"inserted in ascending order");
    }

    int upperBound = (term.length() < previousTerm.length())
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
      : uncheckedTransitions.peekFirst().target();

    while (i < term.length()) {
      final char label = term.charAt(i);
      final DawgNode nextNode = factory.build();
      uncheckedTransitions.addFirst(new Transition(node, label, nextNode));
      node = nextNode;
      i += 1;
    }

    node.isFinal(true);
    previousTerm = term;
    size += 1;
    return this;
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
      final Transition transition = uncheckedTransitions.removeFirst();
      final DawgNode source = transition.source();
      final char label = transition.label();
      final DawgNode target = transition.target();
      if (minimizedNodes.containsKey(target)) {
        source.addEdge(label, minimizedNodes.get(target));
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
  public Dawg insertAll(final Iterator<String> terms) {
    while (terms.hasNext()) {
      insert(terms.next());
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dawg remove(final String term) {
    throw new UnsupportedOperationException("remove is not supported");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dawg removeAll(final Iterator<String> terms) {
    throw new UnsupportedOperationException("removeAll is not supported");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dawg replace(final String current, final String replacement) {
    throw new UnsupportedOperationException("replace is not supported");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final String term) {
    DawgNode node = root;
    for (int i = 0; i < term.length() && null != node; ++i) {
      final char label = term.charAt(i);
      node = node.transition(label);
    }
    return null != node && isFinal(node);
  }

  @Value
  @Accessors(fluent=true)
  private static class Transition {
    DawgNode source;
    char label;
    DawgNode target;
  }
}
