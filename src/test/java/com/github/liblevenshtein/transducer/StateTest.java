package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

import lombok.val;

import com.github.liblevenshtein.transducer.factory.ElementFactory;
import static com.github.liblevenshtein.assertion.StateAssertions.assertThat;
import static com.github.liblevenshtein.utils.ArrayUtils.arr;

public class StateTest {

  @Test
  public void testState() {
    val elementFactory = new ElementFactory<int[]>();
    val state = new State(elementFactory);

    assertThat(state).hasSize(0);

    state.add(arr(1, 2));
    assertThat(state)
      .hasSize(1)
      .hasInner(0, arr(1, 2))
      .hasOuter(0, arr(1, 2));

    assertThat(state)
      .removeInner(arr(1, 2))
      .hasSize(0);

    state.add(new int[] {1, 2});
    assertThat(state).hasSize(1);

    state.add(new int[] {3, 2});
    assertThat(state).hasSize(2);

    state.add(new int[] {3, 5});
    assertThat(state).hasSize(3);

    state.add(new int[] {0, 2});
    assertThat(state).hasSize(4);

    state.insert(2, new int[] {4, 5});
    assertThat(state).hasSize(5);

    assertThat(state)
      // Inner Values
      .hasInner(0, arr(1, 2))
      .hasInner(1, arr(3, 2))
      .hasInner(2, arr(4, 5))
      .hasInner(3, arr(3, 5))
      .hasInner(4, arr(0, 2))
      .hasInner(3, arr(3, 5))
      .hasInner(2, arr(4, 5))
      .hasInner(1, arr(3, 2))
      .hasInner(0, arr(1, 2))
      // Outer Values
      .hasOuter(0, arr(1, 2))
      .hasOuter(1, arr(3, 2))
      .hasOuter(2, arr(4, 5))
      .hasOuter(3, arr(3, 5))
      .hasOuter(4, arr(0, 2))
      .hasOuter(3, arr(3, 5))
      .hasOuter(2, arr(4, 5))
      .hasOuter(1, arr(3, 2))
      .hasOuter(0, arr(1, 2));

    state.sort((a, b) -> {
      final int x = a[1] - b[1];
      if (0 != x) {
        return x;
      }
      return a[0] - b[0];
    });

    assertThat(state)
      // Inner Values
      .hasInner(0, arr(0, 2))
      .hasInner(1, arr(1, 2))
      .hasInner(2, arr(3, 2))
      .hasInner(3, arr(3, 5))
      .hasInner(4, arr(4, 5))
      .hasInner(3, arr(3, 5))
      .hasInner(2, arr(3, 2))
      .hasInner(1, arr(1, 2))
      .hasInner(0, arr(0, 2))
      // Outer Values
      .hasOuter(0, arr(0, 2))
      .hasOuter(1, arr(1, 2))
      .hasOuter(2, arr(3, 2))
      .hasOuter(3, arr(3, 5))
      .hasOuter(4, arr(4, 5))
      .hasOuter(3, arr(3, 5))
      .hasOuter(2, arr(3, 2))
      .hasOuter(1, arr(1, 2))
      .hasOuter(0, arr(0, 2));

    assertThat(state)
      .hasSize(5)
      .hasInner(2, arr(3, 2))
      .removeInner(arr(3, 2))
      .hasSize(4)
      // Inner Values
      .hasInner(0, arr(0, 2))
      .hasInner(1, arr(1, 2))
      .hasInner(2, arr(3, 5))
      .hasInner(3, arr(4, 5))
      // Outer Values
      .hasOuter(0, arr(0, 2))
      .hasOuter(1, arr(1, 2))
      .hasOuter(2, arr(3, 5))
      .hasOuter(3, arr(4, 5));

    state.clear();
    assertThat(state).hasSize(0);
  }
}
