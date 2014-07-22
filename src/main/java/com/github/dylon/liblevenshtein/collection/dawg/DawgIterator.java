package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.Iterator;
import java.util.Queue;
import java.util.ArrayDeque;

import lombok.NonNull;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;
import com.github.dylon.liblevenshtein.collection.AbstractIterator;

public class DawgIterator extends AbstractIterator<String> {
  private final Queue<Prefix<DawgNode>> prefixes = new ArrayDeque<>();

  private final IFinalFunction<DawgNode> isFinal;

  private final IPrefixFactory<DawgNode> prefixFactory;

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
