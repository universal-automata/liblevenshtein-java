package com.github.dylon.liblevenshtein.levenshtein;

import lombok.val;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;

public class XPositionDistanceFunctionTest {

  @Test
  public void testAt() {
    val elementFactory = new ElementFactory<int[]>();
    val stateFactory = new StateFactory();
    val positionFactory = new PositionFactory.ForXPositions();
    stateFactory.elementFactory(elementFactory);

    final IState state = stateFactory.build(
        positionFactory.build(2,3,0),
        positionFactory.build(1,1,0),
        positionFactory.build(4,2,1));

    final IDistanceFunction distance = new XPositionDistanceFunction();

    final int w = 4;
    final int d = distance.at(state, w);
    assertEquals(d, 4);
  }
}
