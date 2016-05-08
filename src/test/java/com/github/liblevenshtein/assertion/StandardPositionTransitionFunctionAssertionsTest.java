package com.github.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.StandardPositionTransitionFunction;
import com.github.liblevenshtein.transducer.State;
import static com.github.liblevenshtein.assertion.StandardPositionTransitionFunctionAssertions.assertThat;

public class StandardPositionTransitionFunctionAssertionsTest {
  private static final int N = 2;
  private static final Position POSITION = new Position(0, 0);
  private static final boolean[] CHARACTERISTIC_VECTOR = {true, false};
  private static final int OFFSET = 0;

  private final ThreadLocal<StandardPositionTransitionFunction> transition = new ThreadLocal<>();
  private final State output = mock(State.class);

  @BeforeMethod
  public void setUp() {
    transition.set(mock(StandardPositionTransitionFunction.class));
  }

  @Test
  public void testTransitionsTo() {
    when(transition.get().of(N, POSITION, CHARACTERISTIC_VECTOR, OFFSET))
      .thenReturn(output);
    assertThat(transition.get())
      .transitionsTo(output, N, POSITION, CHARACTERISTIC_VECTOR, OFFSET);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testTransitionsToAgainstNonNullTransition() {
    when(transition.get().of(N, POSITION, CHARACTERISTIC_VECTOR, OFFSET))
      .thenReturn(null);
    assertThat(transition.get())
      .transitionsTo(output, N, POSITION, CHARACTERISTIC_VECTOR, OFFSET);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testTransitionsToAgainstInvalidTransition() {
    final State invalid = mock(State.class);
    when(transition.get().of(N, POSITION, CHARACTERISTIC_VECTOR, OFFSET))
      .thenReturn(invalid);
    assertThat(transition.get())
      .transitionsTo(output, N, POSITION, CHARACTERISTIC_VECTOR, OFFSET);
  }
}
