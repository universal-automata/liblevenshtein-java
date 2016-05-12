package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

import lombok.val;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

import static com.github.liblevenshtein.assertion.DistanceFunctionAssertions.assertThat;

public class StandardPositionDistanceFunctionTest {

  @Test
  public void testAt() {
    val stateFactory = new StateFactory();
    val positionFactory = new PositionFactory();

    final State state = stateFactory.build(
        positionFactory.build(2, 3),
        positionFactory.build(1, 1),
        positionFactory.build(4, 2));

    final DistanceFunction distance = new DistanceFunction.ForStandardPositions();
    assertThat(distance).hasDistance(state, 4, 2);
  }
}
