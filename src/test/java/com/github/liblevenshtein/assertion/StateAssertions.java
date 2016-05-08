package com.github.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.State;

/**
 * AssertJ-style assertions for {@link State}.
 */
public class StateAssertions extends AbstractAssert<StateAssertions, State> {

  /**
   * Constructs a new {@link StateAssertions} to assert-against.
   * @param actual {@link State} to assert-against.
   */
  public StateAssertions(final State actual) {
    super(actual, StateAssertions.class);
  }

  /**
   * Constructs a new {@link StateAssertions} to assert-against.
   * @param actual {@link State} to assert-against.
   * @return A new {@link StateAssertions} to assert-against.
   */
  public static StateAssertions assertThat(final State actual) {
    return new StateAssertions(actual);
  }

  /**
   * Returns a new {@link StateIteratorAssertions} to assert against the
   * {@link #actual} iterator.
   * @return New {@link StateIteratorAssertions} to assert-against the iterator
   * of the {@link #actual} {@link State}.
   */
  public StateIteratorAssertions iterator() {
    isNotNull();
    return new StateIteratorAssertions(actual.iterator());
  }

  /**
   * Adds a number of positions to the state.
   * @param positions {@link Position}s to add to the {@link #actual} state.
   * @return This {@link StateAssertions} for fluency.
   */
  public StateAssertions add(final Position... positions) {
    isNotNull();
    for (int i = 0; i < positions.length; i += 1) {
      final Position position = positions[i];
      if (null == position) {
        failWithMessage("Position at index [%d] is null", i);
      }
      actual.add(position);
    }
    return this;
  }

  /**
   * Asserts that {@link #actual} has the expected head element.
   * @param expectedHead {@link Position} expected to be the head element.
   * @return This {@link StateAssertions} for fluency.
   * @throws AssertionError When the head element is unexpected.
   */
  public StateAssertions hasHead(final Position expectedHead) {
    isNotNull();

    final Position actualHead = actual.head();

    if (null == expectedHead) {
      if (null != actualHead) {
        failWithMessage("Expected actual.head() to be [null], but was [%s]",
          actualHead);
      }
    }
    else if (!expectedHead.equals(actualHead)) {
      failWithMessage("Expected actual.head() to be [%s], but was [%s]",
        expectedHead, actualHead);
    }

    return this;
  }

  /**
   * Inserts the element into the head of the {@link #actual} state.
   * @param head Head element for the state.
   * @return This {@link StateAssertions} for fluency.
   */
  public StateAssertions head(final Position head) {
    isNotNull();
    actual.head(head);
    return this;
  }

  /**
   * Inserts an element after another in the state.
   * @param curr {@link Position} after which to insert {@link next}..
   * @param next {@link Position} to insert after {@link curr}.
   * @return This {@link StateAssertions} for fluency.
   */
  public StateAssertions insertAfter(final Position curr, final Position next) {
    isNotNull();
    actual.insertAfter(curr, next);
    return this;
  }
}
