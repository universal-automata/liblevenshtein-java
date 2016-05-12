package com.github.liblevenshtein.transducer;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.liblevenshtein.transducer.factory.PositionFactory;

import static com.github.liblevenshtein.assertion.SubsumesFunctionAssertions.assertThat;

public class SubsumesFunctionTest {

  private final PositionFactory positionFactory = new PositionFactory();

  private final SubsumesFunction standardSubsumes = new SubsumesFunction.ForStandardAlgorithm();

  private final SubsumesFunction transpositionSubsumes = new SubsumesFunction.ForTransposition();

  private final SubsumesFunction mergeAndSplitSubsumes = new SubsumesFunction.ForMergeAndSplit();

  @DataProvider(name = "forStandardAlgorithm")
  public Object[][] forStandardAlgorithm() {
    return new Object[][] {
      {4, 2, 0, 0, 4, false},
      {4, 2, 0, 1, 4, false},
      {4, 2, 0, 2, 4, false},
      {4, 2, 0, 3, 4, false},
      {4, 2, 0, 4, 4, false},
      {4, 2, 0, 5, 4, false},

      {4, 2, 1, 0, 4, false},
      {4, 2, 1, 1, 4, false},
      {4, 2, 1, 2, 4, false},
      {4, 2, 1, 3, 4, false},
      {4, 2, 1, 4, 4, false},
      {4, 2, 1, 5, 4, true},

      {4, 2, 2, 0, 4, false},
      {4, 2, 2, 1, 4, false},
      {4, 2, 2, 2, 4, false},
      {4, 2, 2, 3, 4, false},
      {4, 2, 2, 4, 4, true},
      {4, 2, 2, 5, 4, true},

      {4, 2, 3, 0, 4, false},
      {4, 2, 3, 1, 4, false},
      {4, 2, 3, 2, 4, false},
      {4, 2, 3, 3, 4, true},
      {4, 2, 3, 4, 4, true},
      {4, 2, 3, 5, 4, true},

      {4, 2, 4, 0, 4, false},
      {4, 2, 4, 1, 4, false},
      {4, 2, 4, 2, 4, true},
      {4, 2, 4, 3, 4, true},
      {4, 2, 4, 4, 4, true},
      {4, 2, 4, 5, 4, true},

      {4, 2, 5, 0, 4, false},
      {4, 2, 5, 1, 4, false},
      {4, 2, 5, 2, 4, false},
      {4, 2, 5, 3, 4, true},
      {4, 2, 5, 4, 4, true},
      {4, 2, 5, 5, 4, true},

      {4, 2, 6, 0, 4, false},
      {4, 2, 6, 1, 4, false},
      {4, 2, 6, 2, 4, false},
      {4, 2, 6, 3, 4, false},
      {4, 2, 6, 4, 4, true},
      {4, 2, 6, 5, 4, true},

      {4, 2, 7, 0, 4, false},
      {4, 2, 7, 1, 4, false},
      {4, 2, 7, 2, 4, false},
      {4, 2, 7, 3, 4, false},
      {4, 2, 7, 4, 4, false},
      {4, 2, 7, 5, 4, true},

      {4, 2, 8, 0, 4, false},
      {4, 2, 8, 1, 4, false},
      {4, 2, 8, 2, 4, false},
      {4, 2, 8, 3, 4, false},
      {4, 2, 8, 4, 4, false},
      {4, 2, 8, 5, 4, false},
    };
  }

  @DataProvider(name = "forTransposition")
  public Object[][] forTransposition() {
    return new Object[][] {
      {1, 1, false, 0, 1, false, 4, false},
      {1, 1, false, 1, 2, false, 4, true},
      {3, 1, false, 0, 2, true, 4, false},
      {1, 1, false, 0, 2, true, 4, true},
      {3, 3, true, 3, 3, false, 4, false},
      {4, 4, true, 4, 4, false, 4, true},
      {1, 1, true, 2, 1, true, 4, false},
      {1, 1, true, 1, 1, true, 4, true},
    };
  }

  @DataProvider(name = "forMergeAndSplit")
  public Object[][] forMergeAndSplit() {
    return new Object[][] {
      {1, 1, false, 1, 0, false, 4, false},
      {1, 1, false, 1, 1, false, 4, true},
      {1, 1, false, 1, 0, true, 4, false},
      {1, 1, false, 1, 1, true, 4, true},
      {1, 1, true, 1, 0, false, 4, false},
      {1, 1, true, 1, 1, false, 4, false},
      {1, 1, true, 1, 0, true, 4, false},
      {1, 1, true, 1, 1, true, 4, true},
    };
  }

  @Test(dataProvider = "forStandardAlgorithm")
  public void testForStandardAlgorithm(
      final int i, final int e,
      final int j, final int f,
      final int n,
      final boolean shouldSubsume) {
    assertThat(standardSubsumes)
      .subsumesAt(
        positionFactory.build(i, e),
        positionFactory.build(j, f),
        n, shouldSubsume);
  }

  @Test(dataProvider = "forTransposition")
  @SuppressWarnings("checkstyle:parameternumber")
  public void testForTransposition(
      final int i, final int e, final boolean s,
      final int j, final int f, final boolean t,
      final int n,
      final boolean shouldSubsume) {
    assertThat(transpositionSubsumes)
      .subsumesAt(
        positionFactory.build(i, e, s),
        positionFactory.build(j, f, t),
        n, shouldSubsume);
  }

  @Test(dataProvider = "forMergeAndSplit")
  @SuppressWarnings("checkstyle:parameternumber")
  public void testForMergeAndSplit(
      final int i, final int e, final boolean s,
      final int j, final int f, final boolean t,
      final int n,
      final boolean shouldSubsume) {
    assertThat(mergeAndSplitSubsumes)
      .subsumesAt(
        positionFactory.build(i, e, s),
        positionFactory.build(j, f, t),
        n, shouldSubsume);
  }
}
