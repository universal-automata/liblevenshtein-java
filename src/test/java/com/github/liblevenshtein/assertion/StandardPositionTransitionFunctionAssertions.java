package com.github.liblevenshtein.assertion;

import java.util.Arrays;

import org.assertj.core.api.AbstractAssert;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.StandardPositionTransitionFunction;
import com.github.liblevenshtein.transducer.State;

/**
 * AssertJ-style assertions for {@link StandardPositionTransitionFunction}.
 */
public class StandardPositionTransitionFunctionAssertions
    extends AbstractAssert<StandardPositionTransitionFunctionAssertions,
                           StandardPositionTransitionFunction> {

  /**
   * Constructs a new {@link StandardPositionTransitionFunctionAssertions} to
   * assert-against.
   * @param actual {@link StandardPositionTransitionFunction} to assert-against.
   */
  public StandardPositionTransitionFunctionAssertions(
      final StandardPositionTransitionFunction actual) {
    super(actual, StandardPositionTransitionFunctionAssertions.class);
  }

  /**
   * Constructs a new {@link StandardPositionTransitionFunctionAssertions} to
   * assert-against.
   * @param actual {@link StandardPositionTransitionFunction} to assert-against.
   * @return A new {@link StandardPositionTransitionFunctionAssertions} to
   *   assert-against.
   */
  public static StandardPositionTransitionFunctionAssertions assertThat(
      final StandardPositionTransitionFunction actual) {
    return new StandardPositionTransitionFunctionAssertions(actual);
  }

  /**
   * Asserts that the function transitions to the expected state.
   * @param expectedState Expected transition of the function.
   * @param n Maximum number of errors allowed for the spelling candidate.
   * @param position Specifies such information as the index of the spelling
   *   candidate being considered and the number of errors at that index.
   * @param characteristicVector Provides the number of characters before
   *   reaching the target character.
   * @param offset Where to begin looking in the {@link characteristicVector}
   *   for a character match.
   * @return This {@link StandardPositionTransitionFunctionAssertions} for
   *   fluency.
   * @throws AssertionError When the transition is not expected.
   */
  public StandardPositionTransitionFunctionAssertions transitionsTo(
      final State expectedState,
      final int n,
      final Position position,
      final boolean[] characteristicVector,
      final int offset) {

    isNotNull();

    final State actualState =
      actual.of(n, position, characteristicVector, offset);

    if (null == expectedState && null != actualState
        || null != expectedState && !expectedState.equals(actualState)) {
      failWithMessage(
        "Expected transition.of(%d, [%s], [%s], %d) to be [%s], but was [%s]",
        n, position, Arrays.toString(characteristicVector),
        offset, expectedState, actualState);
    }

    return this;
  }
}
