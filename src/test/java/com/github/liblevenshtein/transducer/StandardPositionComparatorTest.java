package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

import static com.github.liblevenshtein.assertion.ComparatorAssertions.assertThat;

public class StandardPositionComparatorTest {
  private final StandardPositionComparator comparator = new StandardPositionComparator();

  @Test
  public void testCompare() {
    assertThat(comparator)
      .comparesLessThan(new Position(0, 0), new Position(0, 1))
      .comparesLessThan(new Position(0, 0), new Position(1, 0))
      .comparesLessThan(new Position(0, 0), new Position(1, 1))
      .comparesLessThan(new Position(0, 1), new Position(1, 0))
      .comparesLessThan(new Position(0, 1), new Position(1, 1))
      .comparesLessThan(new Position(1, 0), new Position(1, 1))
      .comparesEqualTo(new Position(0, 0), new Position(0, 0))
      .comparesEqualTo(new Position(0, 1), new Position(0, 1))
      .comparesEqualTo(new Position(1, 0), new Position(1, 0))
      .comparesEqualTo(new Position(1, 1), new Position(1, 1))
      .comparesGreaterThan(new Position(1, 1), new Position(0, 0))
      .comparesGreaterThan(new Position(1, 1), new Position(0, 1))
      .comparesGreaterThan(new Position(1, 1), new Position(1, 0))
      .comparesGreaterThan(new Position(1, 0), new Position(0, 1))
      .comparesGreaterThan(new Position(1, 0), new Position(0, 0))
      .comparesGreaterThan(new Position(0, 1), new Position(0, 0));
  }
}
