package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;

public class UnsubsumeFunctionTest {

  private final ElementFactory<int[]> elementFactory = new ElementFactory<>();

  private final StateFactory stateFactory = new StateFactory();

  private final PositionFactory standardPositionFactory =
    new PositionFactory.ForStandardPositions();

  private final PositionFactory xPositionFactory =
    new PositionFactory.ForXPositions();

  private final UnsubsumeFunction standardPositionUnsubsume =
    new UnsubsumeFunction.ForStandardPositions();

  private final UnsubsumeFunction xPositionUnsubsume =
    new UnsubsumeFunction.ForXPositions();

  private final SubsumesFunction standardPositionSubsumes =
    new SubsumesFunction.ForStandardAlgorithm();

  private final SubsumesFunction xPositionSubsumes =
    new SubsumesFunction.ForTransposition();

  @BeforeTest
  public void setUp() {
    stateFactory.elementFactory(elementFactory);

    standardPositionUnsubsume.positionFactory(standardPositionFactory);
    standardPositionUnsubsume.subsumes(standardPositionSubsumes);

    xPositionUnsubsume.positionFactory(xPositionFactory);
    xPositionUnsubsume.subsumes(xPositionSubsumes);
  }

  @DataProvider(name = "forStandardPositions")
  public Object[][] forStandardPositions() {
    return new Object[][] {
      {4,2, 1,4, false}
    , {4,2, 1,5, true}
    , {4,2, 2,0, false}
    , {4,2, 2,5, true}
    };
  }

  @DataProvider(name = "forXPositions")
  public Object[][] forXPositions() {
    return new Object[][] {
      {1,1,0, 0,1,0, false}
    , {1,1,0, 1,2,0, true}
    , {3,1,0, 0,2,1, false}
    , {1,1,0, 0,2,1, true}
    };
  }

  @Test(dataProvider = "forStandardPositions")
  public void testForStandardPositions(
      final int i, final int e,
      final int j, final int f,
      final boolean shouldSubsume) {

    final IState actualOutput = stateFactory.build(
        standardPositionFactory.build(i,e),
        standardPositionFactory.build(j,f));

    final IState expectedOutput = shouldSubsume
      ? stateFactory.build(
          standardPositionFactory.build(i,e))
      : stateFactory.build(
          standardPositionFactory.build(i,e),
          standardPositionFactory.build(j,f));

    standardPositionUnsubsume.at(actualOutput);
    assertEquals(actualOutput, expectedOutput);
  }

  @Test(dataProvider = "forXPositions")
  public void testForXPositions(
      final int i, final int e, final int s,
      final int j, final int f, final int t,
      final boolean shouldSubsume) {

    final IState actualOutput = stateFactory.build(
        xPositionFactory.build(i,e,s),
        xPositionFactory.build(j,f,t));

    final IState expectedOutput = shouldSubsume
      ? stateFactory.build(
          xPositionFactory.build(i,e,s))
      : stateFactory.build(
          xPositionFactory.build(i,e,s),
          xPositionFactory.build(j,f,t));

    xPositionUnsubsume.at(actualOutput);
    assertEquals(actualOutput, expectedOutput);
  }
}
