package com.github.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.transducer.State;
import static com.github.liblevenshtein.assertion.StateAssertions.assertThat;
import static com.github.liblevenshtein.utils.ArrayUtils.arr;

public class StateAssertionsTest {

  private final ThreadLocal<State> state = new ThreadLocal<>();

  @BeforeMethod
  public void setUp() {
    state.set(mock(State.class));
  }

  @Test
  public void testHasSize() {
    when(state.get().size()).thenReturn(1);
    assertThat(state.get()).hasSize(1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasSizeAgainstViolation() {
    when(state.get().size()).thenReturn(2);
    assertThat(state.get()).hasSize(1);
  }

  @Test
  public void testHasInner() {
    when(state.get().getInner(0)).thenReturn(arr(1, 2, 3));
    assertThat(state.get()).hasInner(0, arr(1, 2, 3));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasInnerAgainstViolation() {
    when(state.get().getInner(0)).thenReturn(arr(1, 2, 3));
    assertThat(state.get()).hasInner(0, arr(3, 4, 5));
  }

  @Test
  public void testHasOuter() {
    when(state.get().getOuter(0)).thenReturn(arr(1, 2, 3));
    assertThat(state.get()).hasOuter(0, arr(1, 2, 3));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasOuterAgainstViolation() {
    when(state.get().getOuter(0)).thenReturn(arr(1, 2, 3));
    assertThat(state.get()).hasOuter(0, arr(3, 4, 5));
  }

  @Test
  public void testRemoveInner() {
    when(state.get().removeInner()).thenReturn(arr(1, 2, 3));
    assertThat(state.get()).removeInner(arr(1, 2, 3));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testRemoveInnerAgainstViolation() {
    when(state.get().removeInner()).thenReturn(arr(1, 2, 3));
    assertThat(state.get()).removeInner(arr(3, 4, 5));
  }
}
