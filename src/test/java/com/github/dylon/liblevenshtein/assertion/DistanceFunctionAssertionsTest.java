package com.github.dylon.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.IDistanceFunction;
import com.github.dylon.liblevenshtein.levenshtein.IState;
import static com.github.dylon.liblevenshtein.assertion.DistanceFunctionAssertions.assertThat;

public class DistanceFunctionAssertionsTest {

  private IDistanceFunction distance = null;
  private IState state = null;

  @BeforeMethod
  public void setUp() {
    this.distance = mock(IDistanceFunction.class);
    this.state = mock(IState.class);
  }

  @Test
  public void testHasDistance() {
    when(distance.at(state, 4)).thenReturn(2);
    assertThat(distance).hasDistance(state, 4, 2);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasDistanceAgainstViolation() {
    when(distance.at(state, 4)).thenReturn(2);
    assertThat(distance).hasDistance(state, 4, 3);
  }
}
