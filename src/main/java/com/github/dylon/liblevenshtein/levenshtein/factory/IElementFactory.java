package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;

import com.github.dylon.liblevenshtein.levenshtein.Element;

/**
 * Builds elements that are used to link position vectors in Levenshtein states.
 * @param <Type> Kind of position vectors that will be linked.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IElementFactory<Type> extends Serializable {

  static final long serialVersionUID = 1L;

  /**
   * Builds a new linked-list node with the given value.
   * @param value Value to assign the linked-list node.
   * @return New linked-list node with the specified value.
   */
  Element<Type> build(Type value);

  /**
   * Recycles an {@link Element} for re-use in {@link #build(Object) build}.
   * @param element {@link Element} to recycle.  Once you've recycled an element
   * you should discard its reference.
   */
  default void recycle(Element<Type> element) {
    // default behavior is to do nothing
  }
}
