package com.github.liblevenshtein.transducer;

import lombok.AllArgsConstructor;

import com.github.liblevenshtein.collection.AbstractIterator;

/**
 * Iterates over the positions of a {@link State}.
 * @since 3.0.0
 */
@AllArgsConstructor
public class StateIterator extends AbstractIterator<Position> {

  /**
   * {@link State} whose {@link Position}s should be iterated-over.
   */
  private final State state;

  /**
   * {@link Position} that follows immediately-after {@link #curr}. The ability
   * to "lookahead" to the next {@link Position} is useful for some operations.
   */
  private Position lookAhead;

  /**
   * Current {@link Position} in the {@link #state} being considered.
   */
  private Position curr;

  /**
   * {@link Position} that precedes immediately-before {@link #curr}. The
   * ability to look behind {@link #curr} is useful for some operations.
   */
  private Position prev;

  /**
   * Inserts a new {@link Position} into {@link #state} immediately after the
   * {@link #curr} {@link Position}.
   * @param position {@link Position} to insert into {@link #state}.
   */
  public void insert(final Position position) {
    if (null != curr) {
      state.insertAfter(curr, position);
    }
    else {
      state.head(position);
    }
    lookAhead = position;
  }

  /**
   * Returns the {@link #curr} {@link Position} without iterating to the next
   * one.
   * @return {@link #curr}.
   */
  public Position peek() {
    advance();
    return next;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void remove() {
    if (null != curr) {
      state.remove(prev, curr);
      curr = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void advance() {
    if (null == next && null != lookAhead) {
      next = lookAhead;
      if (null != curr) {
        prev = curr;
      }
      curr = next;
      lookAhead = lookAhead.next();
    }
  }

  /**
   * Copies this {@link StateIterator} to another, whose state at the point of
   * copying will be equivalent to the state of this one.
   * @return Copy of this {@link StateIterator}.
   */
  public StateIterator copy() {
    final StateIterator copy = new StateIterator(state, lookAhead, curr, prev);
    copy.next = next;
    return copy;
  }
}
