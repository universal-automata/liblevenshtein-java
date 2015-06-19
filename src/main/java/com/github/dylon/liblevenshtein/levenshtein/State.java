package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.factory.IElementFactory;

@RequiredArgsConstructor
@ToString(of={"size", "head", "tail"})
@EqualsAndHashCode(of={"size", "head", "tail"})
@FieldDefaults(level=AccessLevel.PRIVATE)
public class State implements IState {

  @Getter(onMethod=@_({@Override}))
  int size = 0;

  final IElementFactory<int[]> factory;

  int outerIndex = 0;
  Element<int[]> outer = null;

  int innerIndex = 0;
  Element<int[]> inner = null;

  Element<int[]> head = null;
  Element<int[]> tail = null;

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

  @Override
  public int[] removeInner() {
    if (innerIndex >= size) {
      throw new ArrayIndexOutOfBoundsException(
          "No elements at index: " + innerIndex);
    }

    final Element<int[]> inner = this.inner;

    if (null != inner.next()) {
    	this.inner = inner.next();

    	if (null != inner.prev()) {
      	inner.prev().next(inner.next());
    	}

      this.inner.prev(inner.prev());
    }
    else {
    	this.inner = inner.prev();

    	if (null != this.inner) {
    		this.inner.next(null);
    	}

    	innerIndex -= 1;
    }

    if (head == inner) {
      head = head.next();
    }

    if (tail == inner) {
      tail = tail.prev();
    }

    final int[] position = inner.value();
    factory.recycle(inner);
    size -= 1;

    return position;
  }

  @Override
  public void clear() {
    Element<int[]> tail = this.tail;

    while (null != tail) {
      final Element<int[]> prev = tail.prev();
      factory.recycle(tail);
      tail = prev;
    }

    this.size = 0;
    this.outerIndex = 0;
    this.outer = null;
    this.innerIndex = 0;
    this.inner = null;
    this.tail = null;
    this.head = null;
  }

  private Element<int[]> mergeSort(
      final Comparator<int[]> comparator,
      final Element<int[]> head) {
    if (null == head || null == head.next()) return head;
    final Element<int[]> middle = middle(head);
    final Element<int[]> tail = middle.next();
    middle.next(null);
    return merge(comparator,
        mergeSort(comparator, head),
        mergeSort(comparator, tail));
  }

  private Element<int[]> merge(
      final Comparator<int[]> comparator,
      Element<int[]> head,
      Element<int[]> tail) {

    Element<int[]> next = factory.build(null);
    Element<int[]> curr = next;

    while (null != head && null != tail) {
      if (comparator.compare(head.value(), tail.value()) <= 0) {
        curr.next(head);
        head.prev(curr);
        head = head.next();
      }
      else {
        curr.next(tail);
        tail.prev(curr);
        tail = tail.next();
      }
      curr = curr.next();
    }

    if (null != tail) {
    	curr.next(tail);
    	tail.prev(curr);
    }
    else if (null != head) {
    	curr.next(head);
    	head.prev(curr);
    }

    curr = next.next();
    if (null != curr) {
    	curr.prev(null);
    }

    factory.recycle(next);
    return curr;
  }

  private Element<int[]> middle(final Element<int[]> head) {
    if (null == head) return null;
    Element<int[]> slow = head;
    Element<int[]> fast = head;
    while (null != fast.next() && null != fast.next().next()) {
      slow = slow.next();
      fast = fast.next().next();
    }
    return slow;
  }

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
