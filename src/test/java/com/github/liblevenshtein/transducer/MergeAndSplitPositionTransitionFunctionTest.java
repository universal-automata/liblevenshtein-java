package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

public class MergeAndSplitPositionTransitionFunctionTest
    extends AbstractPositionTransitionFunctionTest {

  @Override
  protected PositionTransitionFunction buildTransition() {
    return new MergeAndSplitPositionTransitionFunction();
  }

  @Test
  public void testOf() {
    i = 0; e = 0; x = false;
    check(1 + i, e, false, new boolean[] {true, false, false, false});
    check(i, 1 + e, false, i, 1 + e, true, 1 + i, 1 + e, false, 2 + i, 1 + e, false,
        new boolean[] {false, true, true, true});

    i = W - 1;
    check(1 + i, e, false, new boolean[] {true, true, true, true});
    check(i, 1 + e, false, i, 1 + e, true, 1 + i, 1 + e, false,
        new boolean[] {false, false, false, false});

    i = 1; e = 1;
    check(1 + i, e, false, new boolean[] {true, true, false, false});
    check(i, 1 + e, false, i, 1 + e, true, 1 + i, 1 + e, false, 2 + i, 1 + e, false,
        new boolean[] {false, false, true, true});

    x = true;
    check(1 + i, e, false, new boolean[] {true, true, true, true});

    i = W - 1; x = false;
    check(1 + i, e, false, new boolean[] {true, true, true, true});
    check(i, 1 + e, false, i, 1 + e, true, 1 + i, 1 + e, false,
        new boolean[] {false, false, false, false});

    x = true;
    check(1 + i, e, false, new boolean[] {true, true, true, true});

    i = W; x = false;
    check(W, 1 + e, false, new boolean[] {true, true, true, true});

    i = 1; e = N;
    check(1 + i, N, false, new boolean[] {true, true, true, true});
    check(false, false, false, false);

    x = true;
    check(1 + i, e, false, new boolean[] {true, true, true, true});

    i = W; x = false;
    check(true, true, true, true);
  }
}
