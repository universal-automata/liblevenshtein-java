package com.github.liblevenshtein.assertion;

import java.util.Arrays;

import org.assertj.core.api.AbstractAssert;

import com.github.liblevenshtein.transducer.State;
import com.github.liblevenshtein.transducer.StateTransitionFunction;

/**
 * AssertJ-style assertions for {@link StateTransitionFunction}.
 */
public class StateTransitionFunctionAssertions
    extends AbstractAssert<StateTransitionFunctionAssertions, StateTransitionFunction> {

  /**
   * Constructs a new {@link StateTransitionFunctionAssertions} to
   * assert-against.
   * @param actual {@link StateTransitionFunction} to assert-against.
   */
  public StateTransitionFunctionAssertions(final StateTransitionFunction actual) {
    super(actual, StateTransitionFunctionAssertions.class);
  }

  /**
   * Constructs a new {@link StateTransitionFunctionAssertions} to
   * assert-against.
   * @param actual {@link StateTransitionFunction} to assert-against.
   * @return A new {@link StateTransitionFunctionAssertions} to
   *   assert-against.
   */
  public static StateTransitionFunctionAssertions assertThat(
      final StateTransitionFunction actual) {
    return new StateTransitionFunctionAssertions(actual);
  }

  /**
   * Asserts that the transition of the input is expected.
   * @param expectedState Expected transition of the input.
   * @param input First parameter for the transition.
   * @param characteristicVector Second parameter of the transition.
   * @return This {@link StateTransitionFunctionAssertions} for fluency.
   * @throws AssertionError When the actual transition is unexpected.
   */
  public StateTransitionFunctionAssertions transitionsTo(
      final State expectedState,
      final State input,
      final boolean[] characteristicVector) {

    isNotNull();

    final State actualState = actual.of(input, characteristicVector);

    if (null == expectedState && null != actualState
        || null != expectedState && !expectedState.equals(actualState)) {
      failWithMessage(
        "Expected StateTransitionFunction#of(%s, [%s]) to be [%s], but was [%s]",
        input, Arrays.toString(characteristicVector), expectedState, actualState);
    }

    return this;
  }
}
