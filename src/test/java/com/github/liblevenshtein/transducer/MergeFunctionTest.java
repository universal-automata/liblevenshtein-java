package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

public class MergeFunctionTest {
  private final StateFactory stateFactory = new StateFactory();
  private final PositionFactory positionFactory = new PositionFactory();

  @Test
  public void testStandardPositions() {
    final MergeFunction merge = new MergeFunction.ForStandardPositions();

    final State s1 = stateFactory.build(
        positionFactory.build(2, 3),
        positionFactory.build(1, 2));

    final State s2 = stateFactory.build(
        positionFactory.build(3, 3),
        positionFactory.build(2, 3),
        positionFactory.build(3, 2),
        positionFactory.build(2, 2),
        positionFactory.build(0, 2));

    final State s3 = stateFactory.build(
        positionFactory.build(3, 3),
        positionFactory.build(2, 3),
        positionFactory.build(3, 2),
        positionFactory.build(2, 2),
        positionFactory.build(1, 2),
        positionFactory.build(0, 2));

    merge.into(s1, s2);
    assertThat(s1).isEqualTo(s3);
  }

  @Test
  public void testXPositions() {
    final MergeFunction merge = new MergeFunction.ForSpecialPositions();

    final State s1 = stateFactory.build(
        positionFactory.build(2, 3, true),
        positionFactory.build(2, 3, false),
        positionFactory.build(1, 2, false));

    final State s2 = stateFactory.build(
        positionFactory.build(2, 4, false),
        positionFactory.build(2, 3, false),
        positionFactory.build(2, 2, true),
        positionFactory.build(3, 0, false));

    final State s3 = stateFactory.build(
        positionFactory.build(2, 4, false),
        positionFactory.build(2, 3, true),
        positionFactory.build(2, 3, false),
        positionFactory.build(2, 2, true),
        positionFactory.build(1, 2, false),
        positionFactory.build(3, 0, false));

    merge.into(s1, s2);
    assertThat(s1).isEqualTo(s3);
  }
}
