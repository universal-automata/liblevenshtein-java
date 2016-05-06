package com.github.liblevenshtein.collection.dictionary;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

import com.github.liblevenshtein.collection.dictionary.factory.DawgNodeFactory;
import com.github.liblevenshtein.collection.dictionary.factory.IDawgNodeFactory;
import com.github.liblevenshtein.collection.dictionary.factory.IPrefixFactory;
import com.github.liblevenshtein.collection.dictionary.factory.ITransitionFactory;
import com.github.liblevenshtein.collection.dictionary.factory.PrefixFactory;
import com.github.liblevenshtein.collection.dictionary.factory.TransitionFactory;

/**
 * <p>
 * Node reference-based DAWG implementation that requires the input collection
 * to be sorted before it can be built.  The sortation is required for space and
 * time efficiency.
 * </p>
 * <p>
 * The algorithm for constructing the DAWG (Direct Acyclic Word Graph) from the
 * input dictionary of words (DAWGs are otherwise known as an MA-FSA, or Minimal
 * Acyclic Finite-State Automata), was taken and modified from the following
 * blog from Steve Hanov:
 * </p>
 * <ul>
 *   <li>http://stevehanov.ca/blog/index.php?id=115</li>
 * </ul>
 * <p>
 * The algorithm therein was taken from the following paper:
 * </p>
 * <pre>
 * <code>
 * {@literal @}MISC {Daciuk00incrementalconstruction,
 *   author = {Jan Daciuk and
 *     Bruce W. Watson and
 *     Richard E. Watson and
 *     Stoyan Mihov},
 *   title = {Incremental Construction of Minimal Acyclic Finite-State Automata},
 *   year = {2000}
 * }
 * </code>
 * </pre>
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class SortedDawg extends AbstractDawg {

  private static final long serialVersionUID = 1L;

  /** Transitions that have not been checked for redundancy. */
  private Deque<Transition<DawgNode>> uncheckedTransitions = new ArrayDeque<>();

  /** Nodes that have been checked for redundancy. */
  private Map<DawgNode, DawgNode> minimizedNodes = new HashMap<>();

  /** References the term that was last added. */
  private String previousTerm = "";

  /** Builds (and recycles for memory efficiency) Transition objects. */
  private ITransitionFactory<DawgNode> transitionFactory;

  /**
   * Constructs a new SortedDawg instance.
   * @param prefixFactory Builds {@link DawgNode} prefixes for traversing the
   * trie.
   * @param factory Manages instances of DAWG nodes
   * @param transitionFactory Builds transitions that link {@link DawgNode}s
   * together.
   */
  public SortedDawg(
      final IPrefixFactory<DawgNode> prefixFactory,
      final IDawgNodeFactory<DawgNode> factory,
      @NonNull final ITransitionFactory<DawgNode> transitionFactory) {
    super(prefixFactory, factory);
    this.transitionFactory = transitionFactory;
  }

  /**
   * Constructs a new SortedDawg instance.
   * @param prefixFactory Builds {@link DawgNode} prefixes for traversing the
   * trie.
   * @param factory Manages instances of DAWG nodes
   * @param transitionFactory Builds transitions that link {@link DawgNode}s
   * together.
   * @param terms Collection of terms to add to this dictionary. This is assumed
   * to be sorted ascendingly, in lexicographical order (case-sensitive),
   * because the behavior of the current DAWG implementation is unstable if it
   * is not.
   */
  public SortedDawg(
      final IPrefixFactory<DawgNode> prefixFactory,
      final IDawgNodeFactory<DawgNode> factory,
      @NonNull final ITransitionFactory<DawgNode> transitionFactory,
      @NonNull final Collection<String> terms) {
    this(prefixFactory, factory, transitionFactory);
    if (!addAll(terms)) {
      throw new IllegalStateException("Failed to add all terms");
    }
    finish();
  }

  /**
   * Constructs a new SortedDawg instance.
   * @param size Number of terms in this dictionary.
   * @param root Root node of this dictionary.
   */
  public SortedDawg(
      final int size,
      @NonNull final DawgNode root) {
    super(new PrefixFactory<DawgNode>(), new DawgNodeFactory(), root, size);
    this.transitionFactory = new TransitionFactory<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized boolean add(@NonNull final String term) {
    if (term.compareTo(previousTerm) < 0) {
      throw new IllegalArgumentException(
          "Due to caveats with the current DAWG implementation, terms must be "
          + "inserted in ascending order");
    }

    // Special Case: Empty String
    if (term.isEmpty()) {
      root = factory.build(true);
      return true;
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
    DawgNode node = (null == uncheckedTransitions.peekFirst())
      ? root
      : uncheckedTransitions.peekFirst().target();

    for (int k = term.length() - 1; i < k; i += 1) {
      final char label = term.charAt(i);
      final DawgNode nextNode = factory.build();
      uncheckedTransitions.addFirst(transitionFactory.build(node, label, nextNode));
      node = nextNode;
    }

    if (i < term.length()) {
      final char label = term.charAt(i);
      final DawgNode nextNode = factory.build(true);
      uncheckedTransitions.addFirst(transitionFactory.build(node, label, nextNode));
      //node = nextNode;
      //i += 1;
    }

    previousTerm = term;
    size += 1;
    return true;
  }

  /**
   * Finishes processing the pending transitions.
   */
  public void finish() {
    minimize(0);
  }

  /**
   * Builds this DAWG in such a way that it remains a minimal trie.
   * @param lowerBound Number of pending transitions to leave for the next
   * round (they will be the most-recent transitions).
   */
  private void minimize(final int lowerBound) {
    // Proceed from the leaf up to a certain point
    for (int j = uncheckedTransitions.size(); j > lowerBound; j -= 1) {
      final Transition<DawgNode> transition = uncheckedTransitions.removeFirst();
      final DawgNode source = transition.source();
      final char label = transition.label();
      final DawgNode target = transition.target();
      final boolean isFinal = target.isFinal();

      final DawgNode existing = minimizedNodes.get(target);

      if (null != existing) {
        source.addEdge(label, existing);
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
  public boolean remove(final Object object) {
    throw new UnsupportedOperationException(
        "SortedDawg does not support removing terms");
  }
}
