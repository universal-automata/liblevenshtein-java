package com.github.liblevenshtein.transducer;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

public abstract class AbstractPositionTransitionFunctionTest {
  protected static final int N = 3; // max number of errors
  protected static final int W = 4; // length of characteristic vector

  protected final PositionTransitionFunction transition = buildTransition();

  protected final StateFactory stateFactory = new StateFactory();
  protected final PositionFactory positionFactory = new PositionFactory();

  protected int i = 0;
  protected int e = 0;
  protected boolean x = false;

  protected AbstractPositionTransitionFunctionTest() {
    transition.stateFactory(stateFactory);
    transition.positionFactory(positionFactory);
  }

  protected abstract PositionTransitionFunction buildTransition();

  protected void check(final boolean... characteristicVector) {
    final State actualState = transition.of(N, positionFactory.build(i, e, x),
      characteristicVector, 0);
    assertThat(actualState).isNull();
  }

  protected void check(
      final int i1, final int e1, final boolean t1,
      final boolean[] characteristicVector) {
    final State actualState = transition.of(N, positionFactory.build(i, e, x),
      characteristicVector, 0);
    final Position position = positionFactory.build(i1, e1, t1);
    final State expectedState = stateFactory.build(position);
    assertThat(actualState).isEqualTo(expectedState);
  }

  protected void check(
      final int i1, final int e1, final boolean t1,
      final int i2, final int e2, final boolean t2,
      final boolean[] characteristicVector) {
    final State actualState = transition.of(N, positionFactory.build(i, e, x),
      characteristicVector, 0);
    final Position p1 = positionFactory.build(i1, e1, t1);
    final Position p2 = positionFactory.build(i2, e2, t2);
    final State expectedState = stateFactory.build(p1, p2);
    assertThat(actualState).isEqualTo(expectedState);
  }

  @SuppressWarnings("checkstyle:parameternumber")
  protected void check(
      final int i1, final int e1, final boolean t1,
      final int i2, final int e2, final boolean t2,
      final int i3, final int e3, final boolean t3,
      final boolean[] characteristicVector) {
    final State actualState = transition.of(N, positionFactory.build(i, e, x),
      characteristicVector, 0);
    final Position p1 = positionFactory.build(i1, e1, t1);
    final Position p2 = positionFactory.build(i2, e2, t2);
    final Position p3 = positionFactory.build(i3, e3, t3);
    final State expectedState = stateFactory.build(p1, p2, p3);
    assertThat(actualState).isEqualTo(expectedState);
  }

  @SuppressWarnings("checkstyle:parameternumber")
  protected void check(
      final int i1, final int e1, final boolean t1,
      final int i2, final int e2, final boolean t2,
      final int i3, final int e3, final boolean t3,
      final int i4, final int e4, final boolean t4,
      final boolean[] characteristicVector) {
    final State actualState = transition.of(N, positionFactory.build(i, e, x),
      characteristicVector, 0);
    final Position p1 = positionFactory.build(i1, e1, t1);
    final Position p2 = positionFactory.build(i2, e2, t2);
    final Position p3 = positionFactory.build(i3, e3, t3);
    final Position p4 = positionFactory.build(i4, e4, t4);
    final State expectedState = stateFactory.build(p1, p2, p3, p4);
    assertThat(actualState).isEqualTo(expectedState);
  }
}
