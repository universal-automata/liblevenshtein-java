package com.github.dylon.liblevenshtein.collection;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;

/**
 * Takes the first {@link #elementsToTake} elements from some {@link #iterator}.
 * @param <Type> Kind of elements returned from {@link #iterator}
 * @author Dylon Edwards
 * @since 2.1.2
 */
@RequiredArgsConstructor
public class TakeIterator<Type> extends AbstractIterator<Type> {

  /**
   * Naximum number of elements to take from the composed iterator.
   * @see #iterator
   * @see #numElementsTaken
   */
  private final int elementsToTake;

  /**
   * Iterator whose elements should be taken.
   * @see #numElementsTaken
   * @see #elementsToTake
   */
  private final Iterator<Type> iterator;

  /**
   * Number of elements taken from {@link #iterator}
   * @see #iterator
   * @see #elementsToTake
   */
  private int numElementsTaken = 0;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void advance() {
    if (null == next && iterator.hasNext() && numElementsTaken < elementsToTake) {
      this.next = iterator.next();
      this.numElementsTaken += 1;
    }
  }
}
