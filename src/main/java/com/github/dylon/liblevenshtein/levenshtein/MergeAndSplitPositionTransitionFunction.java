package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Transitions a merge-and-split, Levenshtein position to all possible
 * positions, given a set of parameters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class MergeAndSplitPositionTransitionFunction
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
    final int s = position[2];
    final int h = i - offset;
    final int w = characteristicVector.length;

    if (e == 0 && 0 < n) {
      if (h <= w - 2) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              positionFactory.build(i + 1, e, 0)
          );
        }

        return stateFactory.build(
            positionFactory.build(i, e + 1, 0),
            positionFactory.build(i, e + 1, 1),
            positionFactory.build(i + 1, e + 1, 0),
            positionFactory.build(i + 2, e + 1, 0)
        );
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              positionFactory.build(i + 1, e, 0)
          );
        }

        return stateFactory.build(
            positionFactory.build(i, e + 1, 0),
            positionFactory.build(i, e + 1, 1),
            positionFactory.build(i + 1, e + 1, 0)
        );
      }

      // else, h == w
      return stateFactory.build(
          positionFactory.build(i, e + 1, 0)
      );
    }

    if (e < n) {
      if (h <= w - 2) {
        if (s == 0) {
          if (characteristicVector[h]) {
            return stateFactory.build(
                positionFactory.build(i + 1, e, 0)
            );
          }

          return stateFactory.build(
              positionFactory.build(i, e + 1, 0),
              positionFactory.build(i, e + 1, 1),
              positionFactory.build(i + 1, e + 1, 0),
              positionFactory.build(i + 2, e + 1, 0)
          );
        }

        return stateFactory.build(
            positionFactory.build(i + 1, e, 0)
        );
      }

      if (h == w - 1) {
        if (s == 0) {
          if (characteristicVector[h]) {
            return stateFactory.build(
                positionFactory.build(i + 1, e, 0)
            );
          }

          return stateFactory.build(
              positionFactory.build(i, e + 1, 0),
              positionFactory.build(i, e + 1, 1),
              positionFactory.build(i + 1, e + 1, 0)
          );
        }

        return stateFactory.build(
            positionFactory.build(i + 1, e, 0)
        );
      }

      // else, h == w
      return stateFactory.build(
          positionFactory.build(i, e + 1, 0)
      );
    }

    if (h <= w - 1) {
      if (s == 0) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              positionFactory.build(i + 1, n, 0)
          );
        }

        return null;
      }

      return stateFactory.build(
          positionFactory.build(i + 1, e, 0)
      );
    }

    // else, h == w
    return null;
  }
}
