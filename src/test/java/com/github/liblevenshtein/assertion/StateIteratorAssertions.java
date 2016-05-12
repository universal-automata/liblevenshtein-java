package com.github.liblevenshtein.assertion;

import java.util.Iterator;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.StateIterator;

/**
 * AssertJ-style assertions for {@link StateIterator}.
 */
public class StateIteratorAssertions extends IteratorAssertions<Position> {

  /**
   * Constructs a new {@link StateIteratorAssertions} to assert-against.
   * @param actual {@link StateIterator} to assert-against.
   */
  public StateIteratorAssertions(final StateIterator actual) {
    super(actual);
  }

  /**
   * Constructs a new {@link StateIteratorAssertions} to assert-against.
   * @param actual {@link StateIterator} to assert-against.
   * @return A new {@link StateIteratorAssertions} to assert-against.
   */
  public static StateIteratorAssertions assertThat(final StateIterator actual) {
    return new StateIteratorAssertions(actual);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StateIteratorAssertions hasNext() {
    super.hasNext();
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StateIteratorAssertions hasNext(final Position expectedValue) {
    super.hasNext(expectedValue);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StateIteratorAssertions doesNotHaveNext() {
    super.doesNotHaveNext();
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StateIteratorAssertions isEqualTo(final Iterator<Position> expected) {
    super.isEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the iterator returns the expected {@link Position} from
   * {@link StateIterator#peek()}.
   * @param expectedElement {@link Position} expected to be returned from
   * {@link StateIterator#peek()}.
   * @return This {@link StateIteratorAssertions} for fluency.
   * @throws AssertionError When the actual element returned from
   * {@link StateIterator#peek()} is not the expected one.
   */
  public StateIteratorAssertions peeks(final Position expectedElement) {
    isNotNull();

    final Position actualElement = ((StateIterator) actual).peek();

    if (null == expectedElement) {
      if (null != actualElement) {
        failWithMessage("Expected actual.peek() to be [null], but was [%s]",
          actualElement);
      }
    }
    else if (!expectedElement.equals(actualElement)) {
      failWithMessage("Expected actual.peek() to be [%s], but was [%s]",
        expectedElement, actualElement);
    }

    return this;
  }

  /**
   * Removes an element from the {@link #actual} iterator.  This method calls
   * {@link StateIterator#remove()}.
   * @return This {@link StateIteratorAssertions} for fluency.
   */
  public StateIteratorAssertions remove() {
    isNotNull();
    actual.remove();
    return this;
  }

  /**
   * Returns a new {@link StateIteratorAssertions} around a copy of the
   * {@link #actual} iterator.
   * @return A new {@link StateIteratorAssertions} to assert-against.
   */
  public StateIteratorAssertions copy() {
    isNotNull();
    return new StateIteratorAssertions(((StateIterator) actual).copy());
  }

  /**
   * Inserts a {@link Position} into the {@link #actual} iterator.
   * @param position {@link Position} to insert into the {@link #actual}
   *   iterator.
   * @return This {@link StateIteratorAssertions} for fluency.
   */
  public StateIteratorAssertions insert(final Position position) {
    isNotNull();
    ((StateIterator) actual).insert(position);
    return this;
  }
}
