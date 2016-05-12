package com.github.liblevenshtein.assertion;

import org.testng.annotations.Test;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.State;

import static com.github.liblevenshtein.assertion.StateAssertions.assertThat;

public class StateAssertionsTest {

  @Test
  public void testIterator() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    assertThat(state).iterator()
      .doesNotHaveNext();
    state.add(p0);
    assertThat(state).iterator()
      .hasNext(p0)
      .doesNotHaveNext();
    state.add(p1);
    assertThat(state).iterator()
      .hasNext(p0)
      .hasNext(p1)
      .doesNotHaveNext();
  }

  @Test
  public void testAdd() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final Position p2 = new Position(2, 0);
    final State state = new State();
    assertThat(state)
      .add()
      .add(p0)
      .add(p1, p2)
      .iterator()
        .hasNext(p0)
        .hasNext(p1)
        .hasNext(p2)
        .doesNotHaveNext();
  }

  @Test
  public void testHasHead() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    assertThat(state).hasHead(null);
    state.add(p0);
    assertThat(state).hasHead(p0);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasHeadAgainstNullHead() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    assertThat(state).hasHead(p0);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasHeadAgainstNonNullHead() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    state.add(p0);
    assertThat(state).hasHead(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasHeadAgainstDiffHeads() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    state.add(p0);
    assertThat(state).hasHead(p1);
  }

  @Test
  public void testHead() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    assertThat(state)
      .head(p0)
      .hasHead(p0);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHeadAgainstMismatch() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    assertThat(state)
      .head(p0)
      .hasHead(p1);
  }

  @Test
  public void testInsertAfter() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final Position p2 = new Position(2, 0);
    final State state = new State();
    assertThat(state)
      .head(p0)
      .insertAfter(p0, p2)
      .insertAfter(p0, p1)
      .iterator()
        .hasNext(p0)
        .hasNext(p1)
        .hasNext(p2)
        .doesNotHaveNext();
  }
}
