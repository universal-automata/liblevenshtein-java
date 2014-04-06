package com.github.dylon.liblevenshtein.util;

import java.util.Iterator;

/**
 * Implements boilerplate logic I typically use in my iterators.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class AbstractIterator<Type> implements Iterator<Type> {

  /** Next value to return */
  protected Type next = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {
    advance();
    return next != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type next() {
    advance();
    final Type next = this.next;
    this.next = null;
    return next;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException(
        "java.util.Iterator.remove() is not supported");
  }

  /**
   * Advances {@link next} to the next, non-null value to return.
   */
  protected abstract void advance();
}
