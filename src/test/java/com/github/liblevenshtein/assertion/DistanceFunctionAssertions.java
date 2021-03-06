package com.github.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.liblevenshtein.transducer.DistanceFunction;
import com.github.liblevenshtein.transducer.State;

/**
 * AssertJ-style assertions for {@link DistanceFunction}s.
 */
public class DistanceFunctionAssertions
    extends AbstractAssert<DistanceFunctionAssertions, DistanceFunction> {

  /**
   * Constructs a new {@link DistanceFunctionAssertions} to assert-against.
   * @param actual {@link DistanceFunctionAssertions} to assert-against.
   */
  public DistanceFunctionAssertions(final DistanceFunction actual) {
    super(actual, DistanceFunctionAssertions.class);
  }

  /**
   * Builds a new {@link DistanceFunctionAssertions} to assert-against.
   * @param actual {@link DistanceFunctionAssertions} to assert-against.
   * @return A new {@link DistanceFunctionAssertions} to assert-against.
   */
  public static DistanceFunctionAssertions assertThat(final DistanceFunction actual) {
    return new DistanceFunctionAssertions(actual);
  }

  /**
   * Asserts that the distance function determines the expected distance among
   * the positions of some state.
   * @param state Set of positions from which to extract the distance.
   * @param w Length of the query term.
   * @param expectedDistance Distance that is expected to be extracted from the
   *   state.
   * @return This {@link DistanceFunctionAssertions} for fluency.
   * @throws AssertionError When the actual distance is not expected.
   */
  public DistanceFunctionAssertions hasDistance(
      final State state,
      final int w,
      final int expectedDistance) {

    isNotNull();

    final int actualDistance = actual.at(state, w);

    if (actualDistance != expectedDistance) {
      failWithMessage(
        "Expected d(%s, %d) = [%d], but was [%d]",
        state, w, expectedDistance, actualDistance);
    }

    return this;
  }
}
