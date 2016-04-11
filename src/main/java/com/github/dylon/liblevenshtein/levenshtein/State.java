package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;
import java.util.Comparator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import com.github.dylon.liblevenshtein.levenshtein.factory.IElementFactory;

/**
 * Levenshtein state that is used by all algorithms.  The algorithm-specific
 * components are injected via setters, so {@link State} doesn't need to be
 * coupled to specific implementations.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@RequiredArgsConstructor
@ToString(of = {"size", "head", "tail"})
@EqualsAndHashCode(of = {"size", "head", "tail"})
public class State implements IState, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Number of positions in this state.  This statistic is useful when
   * requesting inner and outer positions, or when inserting new positions into
   * specific locations.
   * -- GETTER --
   * Number of positions in this state.  This statistic is useful when
   * requesting inner and outer positions, or when inserting new positions into
   * specific locations.
   * @return Number of positions in this state.
   */
  @Getter(onMethod = @_({@Override}))
  private int size = 0;

  /**
   * Builds and recycles the linked-list nodes that hold this state's
   * Levenshtein positions.
   */
  private final IElementFactory<int[]> factory;

  /**
   * Index of the cursor for the outer loop of the merge function.
   */
  private int outerIndex = 0;

  /**
   * Cursor of the outer loop of the merge function.
   */
  private Element<int[]> outer = null;

  /**
   * Index of the cursor for the inner loop of the merge function.
   */
  private int innerIndex = 0;

  /**
   * Cursor of the inner loop of the merge function.
   */
  private Element<int[]> inner = null;

  /**
   * Head (first element) of the linked-list of Levenshtein positions.
   */
  private Element<int[]> head = null;

  /**
   * Tail (last element) of the linked-list of Levenshtein positions.
   */
  private Element<int[]> tail = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(final int[] position) {
    final Element<int[]> next = factory.build(position);

    if (null == head) {
      head = next;
    }
    else {
      next.prev(tail);
      tail.next(next);
    }

    tail = next;
    size += 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void insert(final int index, final int[] position) {
    if (index < 0 || index > size) {
      throw new ArrayIndexOutOfBoundsException(
          "Expected 0 <= index <= size, but received: " + index);
    }

    Element<int[]> curr = head;
    for (int i = 0; i < index && i < size; ++i) {
      curr = curr.next();
    }

    final Element<int[]> next = factory.build(position);

    if (null != curr.prev()) {
      curr.prev().next(next);
    }

    next.prev(curr.prev());
    next.next(curr);
    curr.prev(next);

    if (index < innerIndex) {
      innerIndex += 1;
    }

    if (index < outerIndex) {
      outerIndex += 1;
    }

    if (0 == index) {
      head = next;
    }
    else if (size == index) {
      tail = next;
    }

    size += 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] getOuter(final int index) {
    if (index < 0 || index >= size) {
      throw new ArrayIndexOutOfBoundsException(
          "Expected 0 <= index < size, but received: " + index);
    }

    if (0 == index || null == outer) {
      outerIndex = 0;
      outer = head;
    }

    while (outerIndex > index) {
      outerIndex -= 1;
      outer = outer.prev();
    }

    while (outerIndex < index) {
      outerIndex += 1;
      outer = outer.next();
    }

    return outer.value();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] getInner(final int index) {
    if (index < 0 || index >= size) {
      throw new ArrayIndexOutOfBoundsException(
          "Expected 0 <= index < size, but received: " + index);
    }

    if (0 == index || null == inner) {
      innerIndex = 0;
      inner = head;
    }

    while (innerIndex > index) {
      innerIndex -= 1;
      inner = inner.prev();
    }

    while (innerIndex < index) {
      innerIndex += 1;
      inner = inner.next();
    }

    return inner.value();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] removeInner() {
    if (innerIndex >= size) {
      throw new ArrayIndexOutOfBoundsException(
          "No elements at index: " + innerIndex);
    }

    final Element<int[]> innerLocal = this.inner;

    if (null != innerLocal.next()) {
      this.inner = innerLocal.next();

      if (null != innerLocal.prev()) {
        innerLocal.prev().next(innerLocal.next());
      }

      this.inner.prev(innerLocal.prev());
    }
    else {
      this.inner = innerLocal.prev();

      if (null != this.inner) {
        this.inner.next(null);
      }

      innerIndex -= 1;
    }

    if (head == innerLocal) {
      head = head.next();
    }

    if (tail == innerLocal) {
      tail = tail.prev();
    }

    final int[] position = innerLocal.value();
    size -= 1;

    return position;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    Element<int[]> tailLocal = this.tail;

    while (null != tailLocal) {
      final Element<int[]> prev = tailLocal.prev();
      tailLocal = prev;
    }

    this.size = 0;
    this.outerIndex = 0;
    this.outer = null;
    this.innerIndex = 0;
    this.inner = null;
    this.tail = null;
    this.head = null;
  }

  /**
   * Merge-sorts the elements of the linked-list of position vectors, according
   * to the algorithm-specific comparator.
   * @param comparator Levenshtein algorithm-specific comparator for sorting the
   * position elements.
   * @param lhsHead First element of the sublist to sort.
   * @return The new head of the sorted sublist.
   */
  private Element<int[]> mergeSort(
      final Comparator<int[]> comparator,
      final Element<int[]> lhsHead) {
    if (null == lhsHead || null == lhsHead.next()) {
      return lhsHead;
    }
    final Element<int[]> middle = middle(lhsHead);
    final Element<int[]> rhsHead = middle.next();
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
  @SuppressWarnings("checkstyle:finalparameters")
  private Element<int[]> merge(
      final Comparator<int[]> comparator,
      Element<int[]> lhsHead,
      Element<int[]> rhsHead) {

    Element<int[]> next = factory.build(null);
    Element<int[]> curr = next;

    while (null != lhsHead && null != rhsHead) {
      if (comparator.compare(lhsHead.value(), rhsHead.value()) <= 0) {
        curr.next(lhsHead);
        lhsHead.prev(curr);
        lhsHead = lhsHead.next();
      }
      else {
        curr.next(rhsHead);
        rhsHead.prev(curr);
        rhsHead = rhsHead.next();
      }
      curr = curr.next();
    }

    if (null != rhsHead) {
      curr.next(rhsHead);
      rhsHead.prev(curr);
    }
    else if (null != lhsHead) {
      curr.next(lhsHead);
      lhsHead.prev(curr);
    }

    curr = next.next();
    if (null != curr) {
      curr.prev(null);
    }

    return curr;
  }

  /**
   * Returns the element in the middle of the sublist, begun by {@code head}.
   * @param head First element in the sublist.
   * @return Middle element of the sublist.
   */
  private Element<int[]> middle(final Element<int[]> head) {
    if (null == head) {
      return null;
    }
    Element<int[]> slow = head;
    Element<int[]> fast = head;
    while (null != fast.next() && null != fast.next().next()) {
      slow = slow.next();
      fast = fast.next().next();
    }
    return slow;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sort(final Comparator<int[]> comparator) {
    this.head = mergeSort(comparator, head);
    Element<int[]> curr = head;
    while (null != curr.next()) {
      curr = curr.next();
    }
    this.tail = curr;
  }
}
