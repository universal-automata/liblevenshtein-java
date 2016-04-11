package com.github.dylon.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.State;
import static com.github.dylon.liblevenshtein.assertion.StateAssertions.assertThat;
import static com.github.dylon.liblevenshtein.utils.ArrayUtils.arr;

public class StateAssertionsTest {

  private State state = null;

  @BeforeMethod
  public void setUp() {
    this.state = mock(State.class);
  }

  @Test
  public void testHasSize() {
    when(state.size()).thenReturn(1);
    assertThat(state).hasSize(1);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasSizeAgainstViolation() {
    when(state.size()).thenReturn(2);
    assertThat(state).hasSize(1);
  }

  @Test
  public void testHasInner() {
    when(state.getInner(0)).thenReturn(arr(1,2,3));
    assertThat(state).hasInner(0, arr(1,2,3));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasInnerAgainstViolation() {
    when(state.getInner(0)).thenReturn(arr(1,2,3));
    assertThat(state).hasInner(0, arr(3,4,5));
  }

  @Test
  public void testHasOuter() {
    when(state.getOuter(0)).thenReturn(arr(1,2,3));
    assertThat(state).hasOuter(0, arr(1,2,3));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasOuterAgainstViolation() {
    when(state.getOuter(0)).thenReturn(arr(1,2,3));
    assertThat(state).hasOuter(0, arr(3,4,5));
  }

  @Test
  public void testRemoveInner() {
    when(state.removeInner()).thenReturn(arr(1,2,3));
    assertThat(state).removeInner(arr(1,2,3));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testRemoveInnerAgainstViolation() {
    when(state.removeInner()).thenReturn(arr(1,2,3));
    assertThat(state).removeInner(arr(3,4,5));
  }
}
