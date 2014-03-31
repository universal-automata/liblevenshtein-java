package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.Iterator;
import java.util.Queue;
import java.util.ArrayDeque;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.AbstractIterator;

public class DawgIterator extends AbstractIterator<String> {
  private final Queue<Prefix> prefixes = new ArrayDeque<>();

  public DawgIterator(@NonNull final Dawg dawg) {
    prefixes.offer(Prefix.of(dawg.root(), ""));
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
        final Prefix prefix = prefixes.poll();
        node = prefix.node();
        value = prefix.value();
        final CharIterator iter = node.labels().iterator();
        while (iter.hasNext()) {
          final char label = iter.nextChar();
          final DawgNode nextNode = node.transition(label);
          final String nextValue = value + label;
          if (!prefixes.offer(Prefix.of(nextNode, nextValue))) {
            throw new IllegalStateException(
                "Failed to enqueue prefix value: " + nextValue);
          }
        }
      }
      while (!node.isFinal());
      this.next = value;
    }
  }

  @Accessors(fluent=true)
  @Value(staticConstructor="of")
  private static final class Prefix {
    DawgNode node;
    String value;
  }
}
