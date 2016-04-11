package com.github.dylon.liblevenshtein.levenshtein;

import lombok.val;

import org.testng.annotations.Test;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;
import static com.github.dylon.liblevenshtein.assertion.DistanceFunctionAssertions.assertThat;

public class XPositionDistanceFunctionTest {

  @Test
  public void testAt() {
    val elementFactory = new ElementFactory<int[]>();
    val stateFactory = new StateFactory();
    val positionFactory = new PositionFactory.ForXPositions();
    stateFactory.elementFactory(elementFactory);

    final IState state = stateFactory.build(
        positionFactory.build(2, 3, 0),
        positionFactory.build(1, 1, 0),
        positionFactory.build(4, 2, 1));

    final IDistanceFunction distance = new XPositionDistanceFunction();
    assertThat(distance).hasDistance(state, 4, 4);
  }
}
