package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import com.github.dylon.liblevenshtein.levenshtein.Element;

/**
 * Builds elements which are used to link position vectors together in
 * Levenshtein states.
 * @param <Type> Kind of position vectors that will be linked together.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class ElementFactory<Type> implements IElementFactory<Type> {

  /**
   * Object pool of recycled {@link Element}s.
   */
  private final Queue<Element<Type>> elements = new ArrayDeque<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public Element<Type> build(final Type value) {
    Element<Type> element = elements.poll();

    if (null == element) {
      element = new Element<Type>();
    }

    element.value(value);
    return element;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recycle(Element<Type> element) {
    element.prev(null);
    element.next(null);
    element.value(null);
    elements.offer(element);
  }
}
