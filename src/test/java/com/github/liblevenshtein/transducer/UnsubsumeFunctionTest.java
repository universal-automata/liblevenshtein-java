package com.github.liblevenshtein.transducer;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

public class UnsubsumeFunctionTest {

  private static final int QUERY_LENGTH = 20;

  private final StateFactory stateFactory = new StateFactory();

  private final PositionFactory positionFactory = new PositionFactory();

  private final UnsubsumeFunction standardPositionUnsubsume =
    new UnsubsumeFunction.ForStandardPositions();

  private final UnsubsumeFunction specialPositionUnsubsume =
    new UnsubsumeFunction.ForSpecialPositions();

  private final SubsumesFunction standardPositionSubsumes =
    new SubsumesFunction.ForStandardAlgorithm();

  private final SubsumesFunction transpositionPositionSubsumes =
    new SubsumesFunction.ForTransposition();

  @BeforeTest
  public void setUp() {
    standardPositionUnsubsume.subsumes(standardPositionSubsumes);
    specialPositionUnsubsume.subsumes(transpositionPositionSubsumes);
  }

  @DataProvider(name = "forStandardPositions")
  public Object[][] forStandardPositions() {
    return new Object[][] {
      {4, 2, 1, 4, false},
      {4, 2, 1, 5, true},
      {4, 2, 2, 0, false},
      {4, 2, 2, 5, true},
    };
  }

  @DataProvider(name = "forSpecialPositions")
  public Object[][] forSpecialPositions() {
    return new Object[][] {
      {1, 1, false, 0, 1, false, false},
      {1, 1, false, 1, 2, false, true},
      {3, 1, false, 0, 2, true, false},
      {1, 1, false, 0, 2, true, true},
    };
  }

  @Test(dataProvider = "forStandardPositions")
  public void testForStandardPositions(
      final int i, final int e,
      final int j, final int f,
      final boolean shouldSubsume) {

    final State actualOutput = stateFactory.build(
        positionFactory.build(i, e),
        positionFactory.build(j, f));

    final State expectedOutput = shouldSubsume
      ? stateFactory.build(
          positionFactory.build(i, e))
      : stateFactory.build(
          positionFactory.build(i, e),
          positionFactory.build(j, f));

    standardPositionUnsubsume.at(actualOutput, QUERY_LENGTH);
    assertThat(actualOutput).isEqualTo(expectedOutput);
  }

  @Test(dataProvider = "forSpecialPositions")
  public void testForXPositions(
      final int i, final int e, final boolean s,
      final int j, final int f, final boolean t,
      final boolean shouldSubsume) {

    final State actualOutput = stateFactory.build(
        positionFactory.build(i, e, s),
        positionFactory.build(j, f, t));

    final State expectedOutput = shouldSubsume
      ? stateFactory.build(
          positionFactory.build(i, e, s))
      : stateFactory.build(
          positionFactory.build(i, e, s),
          positionFactory.build(j, f, t));

    specialPositionUnsubsume.at(actualOutput, QUERY_LENGTH);
    assertThat(actualOutput).isEqualTo(expectedOutput);
  }
}
