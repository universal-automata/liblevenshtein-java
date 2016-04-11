package com.github.dylon.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.IState;
import com.github.dylon.liblevenshtein.levenshtein.StateTransitionFunction;
import static com.github.dylon.liblevenshtein.assertion.StateTransitionFunctionAssertions.assertThat;

public class StateTransitionFunctionAssertionsTest {

  private final boolean[] characteristicVector = {true, false};
  private StateTransitionFunction transition;
  private IState input;
  private IState output;

  @BeforeMethod
  public void setUp() {
    this.transition = mock(StateTransitionFunction.class);
    this.input = mock(IState.class);
    this.output = mock(IState.class);
  }

  @Test
  public void testTransitionsTo() {
    when(transition.of(input, characteristicVector)).thenReturn(output);
    assertThat(transition).transitionsTo(output, input, characteristicVector);
    assertThat(transition).transitionsTo(null, null, characteristicVector);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testTransitionsToAgainstNonNullTransition() {
    when(transition.of(input, characteristicVector)).thenReturn(output);
    assertThat(transition).transitionsTo(null, input, characteristicVector);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testTransitionsToAgainstInvalidTransition() {
    when(transition.of(input, characteristicVector)).thenReturn(output);
    assertThat(transition).transitionsTo(output, null, characteristicVector);
  }
}
