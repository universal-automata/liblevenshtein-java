package com.github.liblevenshtein.transducer;

import org.testng.annotations.Test;

public class TranspositionPositionTransitionFunctionTest
    extends AbstractPositionTransitionFunctionTest {

  @Override
  protected PositionTransitionFunction buildTransition() {
    return new TranspositionPositionTransitionFunction();
  }

  @Test
  public void testOf() {
    i = 0; e = 0; x = false;
    check(1 + i, 0, false, new boolean[] {true, false, false, false});
    check(i, 1, false, i, 1, true, 1 + i, 1, false, 1 + 1 + i, 1 + 1 - 1, false,
        new boolean[] {false, true, false, false});
    check(i, 1, false, 1 + i, 1, false, 2 + 1 + i, 2 + 1 - 1, false,
        new boolean[] {false, false, true, true});
    check(i, 1, false, 1 + i, 1, false, new boolean[] {false, false, false, false});

    i = W - 1;
    check(1 + i, 0, false, new boolean[] {true, true, true, true});
    check(i, 1, false, 1 + i, 1, false, new boolean[] {false, false, false, false});

    i = W;
    check(W, 1, false, new boolean[] {true, true, true, true});

    i = 0; e = 1;
    check(1 + i, e, false, new boolean[] {true, false, false, false});
    check(i, 1 + e, false, i, 1 + e, true, 1 + i, 1 + e, false, 1 + 1 + i, 1 + 1 - 1 + e, false,
        new boolean[] {false, true, false, false});
    check(i, 1 + e, false, 1 + i, 1 + e, false, 2 + 1 + i, 2 + 1 - 1 + e, false,
        new boolean[] {false, false, true, true});
    check(i, 1 + e, false, 1 + i, 1 + e, false, new boolean[] {false, false, false, false});

    x = true;
    check(2 + i, e, false, new boolean[] {true, false, false, false});
    check(false, false, false, false);

    i = W - 1; x = false;
    check(1 + i, e, false, new boolean[] {true, true, true, true});
    check(i, 1 + e, false, 1 + i, 1 + e, false, new boolean[] {false, false, false, false});

    i = W;
    check(W, 1 + e, false, new boolean[] {false, false, false, false});

    i = W - 1; e = N;
    check(1 + i, N, false, new boolean[] {true, true, true, true});
    check(false, false, false, false);

    i = W - 2; x = true;
    check(2 + i, N, false, new boolean[] {true, true, true, true});
    check(false, false, false, false);

    i = W; x = false;
    check(true, true, true, true);
  }
}
