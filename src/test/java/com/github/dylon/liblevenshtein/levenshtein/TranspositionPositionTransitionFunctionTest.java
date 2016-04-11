package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.Test;

public class TranspositionPositionTransitionFunctionTest
    extends AbstractXPositionTransitionFunctionTest {

  @Override
  protected AbstractPositionTransitionFunction buildTransition() {
    return new TranspositionPositionTransitionFunction();
  }

  @Test
  public void testOf() {
    i = 0; e = 0; x = 0;
    check(1 + i, 0, 0, true, false, false, false);
    check(i, 1, 0, i, 1, 1, 1 + i, 1, 0, (1 + 1) + i, (1 + 1) - 1, 0, false, true, false, false);
    check(i, 1, 0, 1 + i, 1, 0, (2 + 1) + i, (2 + 1) - 1, 0, false, false, true, true);
    check(i, 1, 0, 1 + i, 1, 0, false, false, false, false);

    i = W - 1;
    check(1 + i, 0, 0, true, true, true, true);
    check(i, 1, 0, 1 + i, 1, 0, false, false, false, false);

    i = W;
    check(W, 1, 0, true, true, true, true);

    i = 0; e = 1;
    check(1 + i, e, 0, true, false, false, false);
    check(i, 1 + e, 0, i, 1 + e, 1, 1 + i, 1 + e, 0, (1 + 1) + i, (1 + 1) - 1 + e, 0, false, true, false, false);
    check(i, 1 + e, 0, 1 + i, 1 + e, 0, (2 + 1) + i, (2 + 1) - 1 + e, 0, false, false, true, true);
    check(i, 1 + e, 0, 1 + i, 1 + e, 0, false, false, false, false);

    x = 1;
    check(2 + i, e, 0, true, false, false, false);
    check(false, false, false, false);

    i = W - 1; x = 0;
    check(1 + i, e, 0, true, true, true, true);
    check(i, 1 + e, 0, 1 + i, 1 + e, 0, false, false, false, false);

    i = W;
    check(W, 1 + e, 0, false, false, false, false);

    i = W - 1; e = N;
    check(1 + i, N, 0, true, true, true, true);
    check(false, false, false, false);

    i = W - 2; x = 1;
    check(2 + i, N, 0, true, true, true, true);
    check(false, false, false, false);

    i = W; x = 0;
    check(true, true, true, true);
  }
}
