package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

import lombok.val;

import com.github.liblevenshtein.transducer.factory.ElementFactory;
import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;
import static com.github.liblevenshtein.assertion.DistanceFunctionAssertions.assertThat;

public class StandardPositionDistanceFunctionTest {

  @Test
  public void testAt() {
    val elementFactory = new ElementFactory<int[]>();
    val stateFactory = new StateFactory();
    val positionFactory = new PositionFactory.ForStandardPositions();
    stateFactory.elementFactory(elementFactory);

    final IState state = stateFactory.build(
        positionFactory.build(2, 3),
        positionFactory.build(1, 1),
        positionFactory.build(4, 2));

    final IDistanceFunction distance = new StandardPositionDistanceFunction();
    assertThat(distance).hasDistance(state, 4, 2);
  }
}
