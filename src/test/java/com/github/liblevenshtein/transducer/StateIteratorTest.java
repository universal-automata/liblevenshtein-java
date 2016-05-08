package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

import static com.github.liblevenshtein.assertion.StateAssertions.assertThat;

public class StateIteratorTest {

  @Test
  public void emptyStateIteratorShouldNotHaveNextElement() {
    final State state = new State();
    assertThat(state).iterator()
      .doesNotHaveNext()
      .copy()
        .doesNotHaveNext();
  }

  @Test
  public void testInsertOnEmptyStateIterator() {
    final State state = new State();
    final Position position = new Position(0, 0);

    assertThat(state).iterator()
      .doesNotHaveNext()
      .insert(position)
      .hasNext()
      .peeks(position)
      .hasNext(position)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .hasNext()
      .peeks(position)
      .hasNext(position)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .copy()
        .hasNext()
        .peeks(position)
        .hasNext(position)
        .doesNotHaveNext();
  }

  @Test
  public void testInsert() {
    final Position p00 = new Position(0, 0);
    final Position p10 = new Position(1, 0);
    final Position p20 = new Position(2, 0);

    final State state = new State();

    assertThat(state)
      .add(p20, p00)
      .iterator()
        .hasNext(p20)
        .hasNext(p00)
        .insert(p10)
        .hasNext(p10)
        .doesNotHaveNext();

    assertThat(state).iterator()
      .hasNext(p20)
      .hasNext(p00)
      .hasNext(p10)
      .doesNotHaveNext();
  }

  @Test
  public void testOperations() {
    final Position p00 = new Position(0, 0);
    final Position p002 = new Position(0, 0);
    final Position p10 = new Position(1, 0);
    final Position p01 = new Position(0, 1);

    final State state = new State();

    assertThat(state).iterator()
      .doesNotHaveNext()
      .insert(p00)
      .hasNext()
      .peeks(p00)
      .hasNext(p00)
      .doesNotHaveNext()
      .insert(p10)
      .hasNext()
      .peeks(p10)
      .hasNext(p10)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .hasNext(p00)
      .hasNext(p10)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .hasNext(p00)
      .hasNext(p10)
      .insert(p01)
      .hasNext(p01)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .hasNext(p00)
      .hasNext(p10)
      .hasNext(p01)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .hasNext(p00)
      .hasNext(p10)
      .remove()
      .hasNext(p01)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .hasNext(p00)
      .hasNext(p01)
      .doesNotHaveNext();

    assertThat(state).iterator()
      .insert(p002)
      .hasNext(p002)
      .hasNext(p00)
      .hasNext(p01)
      .doesNotHaveNext();
  }
}
