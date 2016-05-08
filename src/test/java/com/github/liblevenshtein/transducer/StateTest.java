package com.github.liblevenshtein.transducer;

import java.util.Comparator;

import org.testng.annotations.Test;

import static com.github.liblevenshtein.assertion.StateAssertions.assertThat;

public class StateTest {

  @Test
  public void testInsertionAndIterationOperations() {
    final Position p00 = new Position(0, 0);
    final Position p01 = new Position(0, 1);
    final Position p10 = new Position(1, 0);
    final Position p11 = new Position(1, 1);

    final Comparator<Position> comparator = new StandardPositionComparator();

    final State state = new State();

    assertThat(state)
      .hasHead(null)
      .head(p00)
      .hasHead(p00)
      .iterator()
        .hasNext(p00)
        .doesNotHaveNext();

    assertThat(state)
      .hasHead(p00)
      .add(p01)
      .iterator()
        .hasNext(p00)
        .hasNext(p01)
        .doesNotHaveNext();

    assertThat(state)
      .hasHead(p00)
      .insertAfter(p00, p10)
      .insertAfter(p00, p11)
      .iterator()
        .hasNext(p00)
        .hasNext(p11)
        .hasNext(p10)
        .hasNext(p01)
        .doesNotHaveNext();

    state.sort(comparator);

    assertThat(state)
      .iterator()
        .hasNext(p00)
        .hasNext(p01)
        .hasNext(p10)
        .hasNext(p11)
        .doesNotHaveNext();

    state.remove(p10, p11).remove(null, p00);

    assertThat(state)
      .hasHead(p01)
      .iterator()
        .hasNext(p01)
        .hasNext(p10)
        .doesNotHaveNext();
  }
}
