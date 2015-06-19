package com.github.dylon.liblevenshtein.levenshtein;

import lombok.val;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;

public class StateTest {

  @Test
  public void testState() {
    val elementFactory = new ElementFactory<int[]>();
    val state = new State(elementFactory);

    assertEquals(state.size(), 0);

    state.add(new int[] {1,2});
    assertEquals(state.size(), 1);

    assertEquals(state.getInner(0), new int[] {1,2});
    assertEquals(state.getOuter(0), new int[] {1,2});

    assertEquals(state.removeInner(), new int[] {1,2});
    assertEquals(state.size(), 0);

    state.add(new int[] {1,2});
    state.add(new int[] {3,2});
    state.add(new int[] {3,5});
    state.add(new int[] {0,2});
    assertEquals(state.size(), 4);

    state.insert(2, new int[] {4,5});
    assertEquals(state.size(), 5);

    assertEquals(state.getInner(0), new int[] {1,2});
    assertEquals(state.getInner(1), new int[] {3,2});
    assertEquals(state.getInner(2), new int[] {4,5});
    assertEquals(state.getInner(3), new int[] {3,5});
    assertEquals(state.getInner(4), new int[] {0,2});
    assertEquals(state.getInner(3), new int[] {3,5});
    assertEquals(state.getInner(2), new int[] {4,5});
    assertEquals(state.getInner(1), new int[] {3,2});
    assertEquals(state.getInner(0), new int[] {1,2});

    assertEquals(state.getOuter(0), new int[] {1,2});
    assertEquals(state.getOuter(1), new int[] {3,2});
    assertEquals(state.getOuter(2), new int[] {4,5});
    assertEquals(state.getOuter(3), new int[] {3,5});
    assertEquals(state.getOuter(4), new int[] {0,2});
    assertEquals(state.getOuter(3), new int[] {3,5});
    assertEquals(state.getOuter(2), new int[] {4,5});
    assertEquals(state.getOuter(1), new int[] {3,2});
    assertEquals(state.getOuter(0), new int[] {1,2});

    state.sort((a,b) -> {
      final int x = a[1] - b[1];
      if (0 != x) return x;
      return a[0] - b[0];
    });

    assertEquals(state.getInner(0), new int[] {0,2});
    assertEquals(state.getInner(1), new int[] {1,2});
    assertEquals(state.getInner(2), new int[] {3,2});
    assertEquals(state.getInner(3), new int[] {3,5});
    assertEquals(state.getInner(4), new int[] {4,5});
    assertEquals(state.getInner(3), new int[] {3,5});
    assertEquals(state.getInner(2), new int[] {3,2});
    assertEquals(state.getInner(1), new int[] {1,2});
    assertEquals(state.getInner(0), new int[] {0,2});

    assertEquals(state.getOuter(0), new int[] {0,2});
    assertEquals(state.getOuter(1), new int[] {1,2});
    assertEquals(state.getOuter(2), new int[] {3,2});
    assertEquals(state.getOuter(3), new int[] {3,5});
    assertEquals(state.getOuter(4), new int[] {4,5});
    assertEquals(state.getOuter(3), new int[] {3,5});
    assertEquals(state.getOuter(2), new int[] {3,2});
    assertEquals(state.getOuter(1), new int[] {1,2});
    assertEquals(state.getOuter(0), new int[] {0,2});

    assertEquals(state.getInner(2), new int[] {3,2});
    assertEquals(state.removeInner(), new int[] {3,2});

    assertEquals(state.size(), 4);

    assertEquals(state.getInner(0), new int[] {0,2});
    assertEquals(state.getInner(1), new int[] {1,2});
    assertEquals(state.getInner(2), new int[] {3,5});
    assertEquals(state.getInner(3), new int[] {4,5});

    assertEquals(state.getOuter(0), new int[] {0,2});
    assertEquals(state.getOuter(1), new int[] {1,2});
    assertEquals(state.getOuter(2), new int[] {3,5});
    assertEquals(state.getOuter(3), new int[] {4,5});

    state.clear();
    assertEquals(state.size(), 0);
  }
}
