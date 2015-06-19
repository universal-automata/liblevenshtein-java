package com.github.dylon.liblevenshtein.levenshtein;

import lombok.val;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;

public class StandardPositionDistanceFunctionTest {

  @Test
  public void testAt() {
    val elementFactory = new ElementFactory<int[]>();
    val stateFactory = new StateFactory();
    val positionFactory = new PositionFactory.ForStandardPositions();
    stateFactory.elementFactory(elementFactory);

    final IState state = stateFactory.build(
        positionFactory.build(2,3),
        positionFactory.build(1,1),
        positionFactory.build(4,2));

    final IDistanceFunction distance = new StandardPositionDistanceFunction();

    final int w = 4;
    final int d = distance.at(state, w);
    assertEquals(d, 2);
  }
}
