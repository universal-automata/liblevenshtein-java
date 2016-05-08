package com.github.liblevenshtein.transducer;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;
import static com.github.liblevenshtein.assertion.StandardPositionTransitionFunctionAssertions.assertThat;

public class StandardPositionTransitionFunctionTest {
  private static final int N = 2; // max number of errors
  private static final int W = 4; // length of characteristic vector

  private final StateFactory stateFactory = new StateFactory();
  private final PositionFactory positionFactory = new PositionFactory();

  private final StandardPositionTransitionFunction transition =
    new StandardPositionTransitionFunction();

  private int i, e;

  @BeforeTest
  public void setUp() {
    transition.stateFactory(stateFactory);
    transition.positionFactory(positionFactory);
  }

  @Test
  public void testOf() {
    i = 0; e = 0;
    check(1 + i, e, true, false, true, false);
    check(i, 1 + e, 1 + i, 1 + e, 1 + 1 + i, 1 + e, false, true, false, true);
    check(i, 1 + e, 1 + i, 1 + e, false, false, false, false);

    i = W - 1;
    check(1 + i, e, true, true, true, true);
    check(i, 1 + e, 1 + i, 1 + e, false, false, false, false);

    i = W;
    check(W, 1 + e, true, true, true, true);

    i = 2; e = N;
    check(1 + i, N, true, true, true, true);
    check(false, false, false, false);

    i = W;
    check(true, true, true, true);
  }

  private void check(final boolean... characteristicVector) {
    assertThat(transition)
      .transitionsTo(null, N, positionFactory.build(i, e),
          characteristicVector, 0);
  }

  private void check(
      final int i1, final int e1,
      final boolean... characteristicVector) {
    final Position position = positionFactory.build(i1, e1);
    final State expectedState = stateFactory.build(position);
    assertThat(transition)
      .transitionsTo(expectedState, N, positionFactory.build(i, e),
          characteristicVector, 0);
  }

  private void check(
      final int i1, final int e1,
      final int i2, final int e2,
      final boolean... characteristicVector) {
    final Position p1 = positionFactory.build(i1, e1);
    final Position p2 = positionFactory.build(i2, e2);
    final State expectedState = stateFactory.build(p1, p2);
    assertThat(transition)
      .transitionsTo(expectedState, N, positionFactory.build(i, e),
          characteristicVector, 0);
  }

  private void check(
      final int i1, final int e1,
      final int i2, final int e2,
      final int i3, final int e3,
      final boolean... characteristicVector) {
    final Position p1 = positionFactory.build(i1, e1);
    final Position p2 = positionFactory.build(i2, e2);
    final Position p3 = positionFactory.build(i3, e3);
    final State expectedState = stateFactory.build(p1, p2, p3);
    assertThat(transition)
      .transitionsTo(expectedState, N, positionFactory.build(i, e),
          characteristicVector, 0);
  }
}
