package com.github.liblevenshtein.assertion;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.State;
import static com.github.liblevenshtein.assertion.StateIteratorAssertions.assertThat;

public class StateIteratorAssertionsTest {

  @Test
  public void testHasNext() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    assertThat(state.iterator())
      .doesNotHaveNext();
    state.add(p0);
    assertThat(state.iterator())
      .hasNext()
      .hasNext(p0);
    assertThat(state.iterator())
      .isEqualTo(Arrays.asList(p0).iterator());
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasNextAgainstFailure() {
    final State state = new State();
    assertThat(state.iterator()).hasNext();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testDoesNotHaveNextAgainstFailure() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    state.add(p0);
    assertThat(state.iterator()).doesNotHaveNext();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testIsEqualToAgainstFailure() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    state.add(p0);
    assertThat(state.iterator())
      .isEqualTo(Arrays.asList(p1).iterator());
  }

  @Test
  public void testPeeks() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    assertThat(state.iterator()).peeks(null);
    state.add(p0);
    assertThat(state.iterator()).peeks(p0);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testPeeksAgainstEmpty() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    assertThat(state.iterator()).peeks(p0);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testPeeksAgainstNonEmpty() {
    final Position p0 = new Position(0, 0);
    final State state = new State();
    state.add(p0);
    assertThat(state.iterator()).peeks(null);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testPeeksAgainstDiffElems() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    state.add(p0);
    assertThat(state.iterator()).peeks(p1);
  }

  @Test
  public void testRemove() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    state.add(p0);
    state.add(p1);
    assertThat(state.iterator())
      .hasNext(p0)
      .hasNext(p1)
      .doesNotHaveNext();
    assertThat(state.iterator())
      .remove()
      .hasNext(p0)
      .hasNext(p1)
      .doesNotHaveNext();
    assertThat(state.iterator())
      .hasNext(p0)
      .remove()
      .hasNext(p1)
      .doesNotHaveNext();
    assertThat(state.iterator())
      .hasNext(p1)
      .remove()
      .doesNotHaveNext();
    assertThat(state.iterator())
      .doesNotHaveNext();
  }

  @Test
  public void testCopy() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    state.add(p0);
    state.add(p1);
    assertThat(state.iterator())
      .copy()
        .hasNext(p0)
        .hasNext(p1)
        .doesNotHaveNext();
    assertThat(state.iterator())
      .hasNext(p0)
      .copy()
        .hasNext(p1)
        .doesNotHaveNext();
    assertThat(state.iterator())
      .hasNext(p0)
      .hasNext(p1)
      .copy()
        .doesNotHaveNext();
    assertThat(state.iterator())
      .hasNext(p0)
      .hasNext(p1)
      .doesNotHaveNext()
      .copy()
        .doesNotHaveNext();
  }

  @Test
  public void testInsert() {
    final Position p0 = new Position(0, 0);
    final Position p1 = new Position(1, 0);
    final State state = new State();
    assertThat(state.iterator())
      .doesNotHaveNext()
      .insert(p0)
      .hasNext(p0)
      .doesNotHaveNext()
      .insert(p1)
      .hasNext(p1)
      .doesNotHaveNext();
    assertThat(state.iterator())
      .hasNext(p0)
      .hasNext(p1)
      .doesNotHaveNext();
  }
}
