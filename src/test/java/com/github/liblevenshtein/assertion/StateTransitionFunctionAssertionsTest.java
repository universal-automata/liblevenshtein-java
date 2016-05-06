package com.github.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.transducer.IState;
import com.github.liblevenshtein.transducer.StateTransitionFunction;
import static com.github.liblevenshtein.assertion.StateTransitionFunctionAssertions.assertThat;

public class StateTransitionFunctionAssertionsTest {

  private final boolean[] characteristicVector = {true, false};
  private final ThreadLocal<StateTransitionFunction> transition = new ThreadLocal<>();
  private final IState input = mock(IState.class);
  private final IState output = mock(IState.class);

  @BeforeMethod
  public void setUp() {
    transition.set(mock(StateTransitionFunction.class));
  }

  @Test
  public void testTransitionsTo() {
    when(transition.get().of(input, characteristicVector)).thenReturn(output);
    assertThat(transition.get()).transitionsTo(output, input, characteristicVector);
    assertThat(transition.get()).transitionsTo(null, null, characteristicVector);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testTransitionsToAgainstNonNullTransition() {
    when(transition.get().of(input, characteristicVector)).thenReturn(output);
    assertThat(transition.get()).transitionsTo(null, input, characteristicVector);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testTransitionsToAgainstInvalidTransition() {
    when(transition.get().of(input, characteristicVector)).thenReturn(output);
    assertThat(transition.get()).transitionsTo(output, null, characteristicVector);
  }
}
