package com.github.dylon.liblevenshtein.levenshtein;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.factory.IElementFactory;

@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class State implements IState {

  @Getter(onMethod=@_({@Override}))
  int size = 0;

  IElementFactory<int[]> factory;

  int outerIndex = 0;
  Element<int[]> outer = null;

  int innerIndex = 0;
  Element<int[]> inner = null;

  Element<int[]> tail;
  Element<int[]> head;

  public State(final IElementFactory<int[]> factory) {
    this.factory = factory;
    this.tail = factory.build(null);
    this.head = tail;
  }

  @Override
  public void add(final int[] position) {
    final Element<int[]> next = factory.build(position);

    if (null == tail) {
      tail = next;
    }
    else {
      head.next(next);
    }

    head = next;
    size += 1;
  }

  @Override
  public int[] getOuter(final int index) {
    if (index < 0 || index >= size) {
      throw new ArrayIndexOutOfBoundsException(
          "Expected 0 <= index < size, but received: " + index);
    }

    if (0 == index) {
      outerIndex = 0;
      outer = tail;
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

    if (0 == index) {
      innerIndex = 0;
      inner = tail;
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
    this.inner = inner.next();

    if (null != inner.prev()) {
      inner.prev().next(inner.next());
    }

    if (null != inner.next()) {
      inner.next().prev(inner.prev());
    }

    if (tail == inner) {
      tail = null;
    }

    if (head == inner) {
      head = head.prev();
    }

    final int[] position = inner.value();
    factory.recycle(inner);
    size -= 1;

    return position;
  }

  @Override
  public void clear() {
    Element<int[]> head = this.head;

    while (null != head && null != head.prev()) {
      final Element<int[]> prev = head.prev();
      prev.next(null);
      factory.recycle(head);
      head = prev;
    }

    if (null != head) {
      factory.recycle(head);
    }

    this.size = 0;
    this.outerIndex = 0;
    this.outer = null;
    this.innerIndex = 0;
    this.inner = null;
    this.head = null;
    this.tail = null;
  }
}
