package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Transitions a standard, Levenshtein position to all possible positions, given
 * a set of parameters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class StandardPositionTransitionFunction
  extends AbstractPositionTransitionFunction {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  public IState of(
      final int n,
      final int[] position,
      final boolean[] characteristicVector,
      final int offset) {

    final int i = position[0];
    final int e = position[1];
    final int h = i - offset;
    final int w = characteristicVector.length;

    if (e < n) {
      if (h <= w - 2) {
        final int a = (n - e < Integer.MAX_VALUE)
          ? n - e + 1
          : Integer.MAX_VALUE;
        final int b = w - h;
        final int k = (a < b) ? a : b;
        final int j = indexOf(characteristicVector, k, h);

        if (0 == j) {
          return stateFactory.build(
              positionFactory.build((1 + i), e));
        }

        if (j > 0) {
          return stateFactory.build(
              positionFactory.build(i, (e + 1)),
              positionFactory.build((i + 1), (e + 1)),
              positionFactory.build((i + j + 1), (e + j)));
        }

        // else, j < 0
        return stateFactory.build(
            positionFactory.build(i, (e + 1)),
            positionFactory.build((i + 1), (e + 1)));
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              positionFactory.build((i + 1), e));
        }

        return stateFactory.build(
            positionFactory.build(i, (e + 1)),
            positionFactory.build((i + 1), (e + 1)));
      }

      // else, h == w
      return stateFactory.build(
          positionFactory.build(i, (e + 1)));
    }

    if ((e == n) && (h <= w - 1) && characteristicVector[h]) {
      return stateFactory.build(
          positionFactory.build((i + 1), n));
    }

    return null;
  }
}
