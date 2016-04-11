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
              // [No Error]: Increment index by one; leave error alone
              positionFactory.build(i + 1, e, 0)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave index alone; incrmeent error by one.
            positionFactory.build(i, e + 1, 0),
            // [Split]: Leave index alone; increment error by one.
            positionFactory.build(i, e + 1, 1),
            // [Substitution]: Increment index by one; increment error by one.
            positionFactory.build(i + 1, e + 1, 0),
            // [Merge]: Increment index by two; increment error by one.
            positionFactory.build(i + 2, e + 1, 0)
        );
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by one; leave error alone.
              positionFactory.build(i + 1, e, 0)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave index alone; increment error by one.
            positionFactory.build(i, e + 1, 0),
            // [Split]: Leave index alone; increment error by one.
            positionFactory.build(i, e + 1, 1),
            // [Substitution]: Increment index by one; increment error by one.
            positionFactory.build(i + 1, e + 1, 0)
        );
      }

      // else, h == w
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, e + 1, 0)
      );
    }

    if (e < n) {
      if (h <= w - 2) {
        if (s == 0) {
          if (characteristicVector[h]) {
            return stateFactory.build(
                // [No Error]: Increment index by one; leave error alone.
                positionFactory.build(i + 1, e, 0)
            );
          }

          return stateFactory.build(
              // [Insertion]: Leave index alone; increment index by one.
              positionFactory.build(i, e + 1, 0),
              // [Split]: Leave index alone; increment error by one.
              positionFactory.build(i, e + 1, 1),
              // [Substitution]: Increment index by one; increment error by one.
              positionFactory.build(i + 1, e + 1, 0),
              // [Merge]: Increment index by two; incremnt error by one.
              positionFactory.build(i + 2, e + 1, 0)
          );
        }

        return stateFactory.build(
            // [No Error]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, e, 0)
        );
      }

      if (h == w - 1) {
        if (s == 0) {
          if (characteristicVector[h]) {
            return stateFactory.build(
                // [No Error]: Increment index by one; leave error alone.
                positionFactory.build(i + 1, e, 0)
            );
          }

          return stateFactory.build(
              // [Insertion]: Leave index alone; increment error by one.
              positionFactory.build(i, e + 1, 0),
              // [Split]: Leave index alone; increment error by one.
              positionFactory.build(i, e + 1, 1),
              // [Substitution]: Increment index by one; increment error by one.
              positionFactory.build(i + 1, e + 1, 0)
          );
        }

        return stateFactory.build(
            // [No Error]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, e, 0)
        );
      }

      // else, h == w
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, e + 1, 0)
      );
    }

    if (h <= w - 1) {
      if (s == 0) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by one; leave error alone.
              positionFactory.build(i + 1, n, 0)
          );
        }

        return null;
      }

      return stateFactory.build(
          // [No Error]: Increment index by one; leave error alone.
          positionFactory.build(i + 1, e, 0)
      );
    }

    // else, h == w
    // ------------
    // [Too Many Errors]: The edit distance has exceeded the max distance for
    // the candidate term.
    return null;
  }
}
