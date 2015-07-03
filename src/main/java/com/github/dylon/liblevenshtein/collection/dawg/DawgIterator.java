package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.Queue;
import java.util.ArrayDeque;

import lombok.NonNull;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;
import com.github.dylon.liblevenshtein.collection.AbstractIterator;

/**
 * Iterates over the terms within an {@link AbstractDawg}.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class DawgIterator extends AbstractIterator<String> {

  /**
   * Queue for traversing the terms in the {@link AbstractDawg} in a
   * depth-first search manner.
   */
  private final Queue<Prefix<DawgNode>> prefixes = new ArrayDeque<>();

  /**
   * Returns whether the current {@link DawgNode} represents the last character
   * in some term.
   */
  private final IFinalFunction<DawgNode> isFinal;

  /** Creates and caches {@link Prefix} instances. */
  private final IPrefixFactory<DawgNode> prefixFactory;

  /**
   * Initializes a new {@link DawgIterator} with a {@link IPrefixFactory} and an
   * {@link AbstractDawg}.
   * @param prefixFactory Creates and caches {@link Prefix} instances, which are
   * used to traverse the {@code dawg}.
   * @param dawg {@link AbstractDawg} to iterate over.
   */
  public DawgIterator(
      @NonNull final IPrefixFactory<DawgNode> prefixFactory,
      @NonNull final AbstractDawg dawg) {
    this.isFinal = dawg;
    this.prefixFactory = prefixFactory;
    prefixes.offer(prefixFactory.build(dawg.root(), ""));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void advance() {
    if (null == next && !prefixes.isEmpty()) {
      DawgNode node = null;
      String value = null;

      do {
        final Prefix<DawgNode> prefix = prefixes.poll();
        node = prefix.node();
        value = prefix.value();
        final CharIterator iter = node.labels();
        while (iter.hasNext()) {
          final char label = iter.nextChar();
          final DawgNode nextNode = node.transition(label);
          final String nextValue = value + label;
          if (!prefixes.offer(prefixFactory.build(nextNode, nextValue))) {
            throw new IllegalStateException(
                "Failed to enqueue prefix value: " + nextValue);
          }
        }
        prefixFactory.recycle(prefix);
      }
      while (!isFinal.at(node));
      this.next = value;
    }
  }
}
