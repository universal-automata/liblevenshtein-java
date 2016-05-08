package com.github.liblevenshtein.transducer;

/**
 * Transitions a merge-and-split, Levenshtein position to all possible
 * positions, given a set of parameters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class MergeAndSplitPositionTransitionFunction extends PositionTransitionFunction {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  public State of(
      final int n,
      final Position position,
      final boolean[] characteristicVector,
      final int offset) {

    final int i = position.termIndex();
    final int e = position.numErrors();
    final boolean s = position.isSpecial();
    final int h = i - offset;
    final int w = characteristicVector.length;

    if (e == 0 && 0 < n) {
      if (h <= w - 2) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by one; leave error alone
              positionFactory.build(i + 1, e, false)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave index alone; incrmeent error by one.
            positionFactory.build(i, e + 1, false),
            // [Split]: Leave index alone; increment error by one.
            positionFactory.build(i, e + 1, true),
            // [Substitution]: Increment index by one; increment error by one.
            positionFactory.build(i + 1, e + 1, false),
            // [Merge]: Increment index by two; increment error by one.
            positionFactory.build(i + 2, e + 1, false)
        );
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by one; leave error alone.
              positionFactory.build(i + 1, e, false)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave index alone; increment error by one.
            positionFactory.build(i, e + 1, false),
            // [Split]: Leave index alone; increment error by one.
            positionFactory.build(i, e + 1, true),
            // [Substitution]: Increment index by one; increment error by one.
            positionFactory.build(i + 1, e + 1, false)
        );
      }

      // else, h == w
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, e + 1, false)
      );
    }

    if (e < n) {
      if (h <= w - 2) {
        if (!s) {
          if (characteristicVector[h]) {
            return stateFactory.build(
                // [No Error]: Increment index by one; leave error alone.
                positionFactory.build(i + 1, e, false)
            );
          }

          return stateFactory.build(
              // [Insertion]: Leave index alone; increment index by one.
              positionFactory.build(i, e + 1, false),
              // [Split]: Leave index alone; increment error by one.
              positionFactory.build(i, e + 1, true),
              // [Substitution]: Increment index by one; increment error by one.
              positionFactory.build(i + 1, e + 1, false),
              // [Merge]: Increment index by two; incremnt error by one.
              positionFactory.build(i + 2, e + 1, false)
          );
        }

        return stateFactory.build(
            // [No Error]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, e, false)
        );
      }

      if (h == w - 1) {
        if (!s) {
          if (characteristicVector[h]) {
            return stateFactory.build(
                // [No Error]: Increment index by one; leave error alone.
                positionFactory.build(i + 1, e, false)
            );
          }

          return stateFactory.build(
              // [Insertion]: Leave index alone; increment error by one.
              positionFactory.build(i, e + 1, false),
              // [Split]: Leave index alone; increment error by one.
              positionFactory.build(i, e + 1, true),
              // [Substitution]: Increment index by one; increment error by one.
              positionFactory.build(i + 1, e + 1, false)
          );
        }

        return stateFactory.build(
            // [No Error]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, e, false)
        );
      }

      // else, h == w
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, e + 1, false)
      );
    }

    if (h <= w - 1) {
      if (!s) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by one; leave error alone.
              positionFactory.build(i + 1, n, false)
          );
        }

        return null;
      }

      return stateFactory.build(
          // [No Error]: Increment index by one; leave error alone.
          positionFactory.build(i + 1, e, false)
      );
    }

    // else, h == w
    // ------------
    // [Too Many Errors]: The edit distance has exceeded the max distance for
    // the candidate term.
    return null;
  }
}
