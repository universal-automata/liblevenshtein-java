package com.github.liblevenshtein.transducer;

import java.io.Serializable;
import java.util.Comparator;

import lombok.Data;

/**
 * Levenshtein state that is used by all algorithms.  The algorithm-specific
 * components are injected via setters, so {@link State} doesn't need to be
 * coupled to specific implementations.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
public class State implements Iterable<Position>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Head (first element) of the linked-list of Levenshtein positions.
   * -- GETTER --
   * Head (first element) of the linked-list of Levenshtein positions.
   * @return Head (first element) of the linked-list of Levenshtein positions.
   */
  private Position head = null;

  /**
   * Updates {@link #head} to point to the new node. The new node is inserted
   * imnmediately-before {@link #head}.
   * @param head {@link Position} to set as the new {@link #head}.
   * @return This {@link State} for fluency.
   */
  public State head(final Position head) {
    head.next(this.head);
    this.head = head;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StateIterator iterator() {
    return new StateIterator(this, head, null, null);
  }

  /**
   * Adds a new position to the linked-list of positions in this state.
   * @param next Position to add to those already in this state.
   * @return This {@link State} for fluency.
   */
  public State add(final Position next) {
    if (null == head) {
      head = next;
    }
    else {
      Position curr = head;
      while (null != curr.next()) {
        curr = curr.next();
      }
      curr.next(next);
    }
    return this;
  }

  /**
   * Inserts a position into a specific location of the linked-list of positions.
   * @param curr {@link Position} after which to insert {@code next}..
   * @param next {@link Position} to insert after {@code curr}.
   * @return This {@link State} for fluency.
   */
  public State insertAfter(final Position curr, final Position next) {
    if (null != curr) {
      next.next(curr.next());
      curr.next(next);
    }
    else {
      add(next);
    }
    return this;
  }

  /**
   * Removes a position from this state.
   * @param prev {@link Position} preceding the one to remove (useful for not
   * having to traverse the linked list to find {@code curr}).
   * @param curr {@link Position} to remove from this state.
   * @return This {@link State} for fluency.
   */
  public State remove(final Position prev, final Position curr) {
    if (null != prev) {
      prev.next(curr.next());
    }
    else {
      head = head.next();
    }
    return this;
  }

  /**
   * Merge-sorts the elements of the linked-list of position vectors, according
   * to the algorithm-specific comparator.
   * @param comparator Levenshtein algorithm-specific comparator for sorting the
   * position elements.
   * @param lhsHead First element of the sublist to sort.
   * @return The new head of the sorted sublist.
   */
  private Position mergeSort(
      final Comparator<Position> comparator,
      final Position lhsHead) {

    if (null == lhsHead || null == lhsHead.next()) {
      return lhsHead;
    }

    final Position middle = middle(lhsHead);
    final Position rhsHead = middle.next();
    middle.next(null);

    return merge(comparator,
        mergeSort(comparator, lhsHead),
        mergeSort(comparator, rhsHead));
  }

  /**
   * Merges two sublists together.
   * @param comparator Levenshtein algorithm-specific comparator for sorting the
   * position elements.
   * @param lhsHead First element of the first sublist.
   * @param rhsHead First element of the second sublist.
   * @return Head of the merged and sorted, sublist.
   */
  private Position merge(
      final Comparator<Position> comparator,
      Position lhsHead,
      Position rhsHead) {

    Position next = new Position(-1, -1);
    Position curr = next;

    while (null != lhsHead && null != rhsHead) {
      if (comparator.compare(lhsHead, rhsHead) <= 0) {
        curr.next(lhsHead);
        lhsHead = lhsHead.next();
      }
      else {
        curr.next(rhsHead);
        rhsHead = rhsHead.next();
      }
      curr = curr.next();
    }

    if (null != rhsHead) {
      curr.next(rhsHead);
    }
    else if (null != lhsHead) {
      curr.next(lhsHead);
    }

    curr = next.next();
    return curr;
  }

  /**
   * Returns the element in the middle of the sublist, begun by {@code head}.
   * @param head First element in the sublist.
   * @return Middle element of the sublist.
   */
  private Position middle(final Position head) {
    Position slow = head;
    Position fast = head;

    while (null != fast.next() && null != fast.next().next()) {
      slow = slow.next();
      fast = fast.next().next();
    }

    return slow;
  }

  /**
   * Merge-sorts the positions in this state in a fashion that makes
   * un-subsumption easy.
   * @param comparator Describes how to sort the positions (dependent on the
   * Levenshtein algorithm).
   * @return This {@link State} for fluency.
   */
  public State sort(final Comparator<Position> comparator) {
    head = mergeSort(comparator, head);
    return this;
  }
}
