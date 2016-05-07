package com.github.liblevenshtein.transducer;

/**
 * Transitions a transposition, Levenshtein position to all possible positions,
 * given a set of parameters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class TranspositionPositionTransitionFunction
  extends AbstractPositionTransitionFunction {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("checkstyle:methodlength")
  public IState of(
      final int n,
      final int[] position,
      final boolean[] characteristicVector,
      final int offset) {

    final int i = position[0];
    final int e = position[1];
    final int t = position[2];
    final int h = i - offset;
    final int w = characteristicVector.length;

    if (e == 0 && 0 < n) {
      if (h <= w - 2) {
        final int a = n - e < Integer.MAX_VALUE
          ? n - e + 1
          : Integer.MAX_VALUE;
        final int b = w - h;
        final int k = a < b ? a : b;
        final int j = indexOf(characteristicVector, k, h);

        switch (j) {
          case 0:
            return stateFactory.build(
                // [No Error]: Increment the index by one; leave the error and
                // special state alone
                positionFactory.build(i + 1, 0, 0)
            );
          case 1:
            return stateFactory.build(
                // [Insertion]: Leave index alone; increment the error by one.
                positionFactory.build(i, 1, 0),
                // [Transposition]: Leave index alone; increment error by one.
                positionFactory.build(i, 1, 1),
                // [Substitution]: Replace the current character; increment the
                // index by one; increment the error by one.
                positionFactory.build(i + 1, 1, 0),
                // [Deletion]: Increment the index by one-more than a single
                // deletion; increment the error by one.
                positionFactory.build(i + 2, 1, 0)
            );
          case -1:
            return stateFactory.build(
                // [Insertion]: Leave index alone; increment the error by one.
                positionFactory.build(i, 1, 0),
                // [Substitution]: Replace the current character; increment the
                // index by one; increment the error by one.
                positionFactory.build(i + 1, 1, 0)
            );
          default: // j > 1
            return stateFactory.build(
                // [Insertion]: Leave index alone; increment the error by one.
                positionFactory.build(i, 1, 0),
                // [Substitution]: Replace the current character; increment the
                // index by one; increment the error by one.
                positionFactory.build(i + 1, 1, 0),
                // [Deletion]: Increment index by one-more than the number of
                // deletions; increment error by the number of deletions.
                positionFactory.build(i + j + 1, j, 0)
            );
        }
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by one; leave error alone
              positionFactory.build(i + 1, 0, 0)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave index alone; increment error by one.
            positionFactory.build(i, 1, 0),
            // [Substitution]: Increment index by one; increment error by one.
            positionFactory.build(i + 1, 1, 0)
        );
      }

      // else, h == 2
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, 1, 0)
      );
    }

    if (1 <= e && e < n) {
      if (h <= w - 2) {
        if (t == 0) {
          final int a = n - e < Integer.MAX_VALUE
            ? n - e + 1
            : Integer.MAX_VALUE;
          final int b = w - h;
          final int k = a < b ? a : b;
          final int j = indexOf(characteristicVector, k, h);

          switch (j) {
            case 0:
              return stateFactory.build(
                  // [No Error]: Increment index by one; leave error alone.
                  positionFactory.build(i + 1, e, 0)
              );
            case 1:
              return stateFactory.build(
                  // [Insertion]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, 0),
                  // [Transposition]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, 1),
                  // [Substitution]: Increment index by one; increment error by
                  // one.
                  positionFactory.build(i + 1, e + 1, 0),
                  // [Deletion]: Increment index by one-more than one deletion;
                  // increment error by one.
                  positionFactory.build(i + 2, e + 1, 0)
              );
            case -1:
              return stateFactory.build(
                  // [Insertion]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, 0),
                  // [Substitution]: Increment index by one; increment error by
                  // one.
                  positionFactory.build(i + 1, e + 1, 0)
              );
            default: // j > 1
              return stateFactory.build(
                  // [Insertion]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, 0),
                  // [Substitution]: Increment index by one; increment error by
                  // one.
                  positionFactory.build(i + 1, e + 1, 0),
                  // [Deletion]: Increment index by one-more than number of
                  // deletions; increment error by number of deletions.
                  positionFactory.build(i + j + 1, e + j, 0)
              );
          }
        }

        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by two; leave error alone.
              positionFactory.build(i + 2, e, 0)
          );
        }

        // [Too Many Errors]: The edit distance has exceeded the max distance
        // for the candidate term.
        return null;
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
            // [Substitution]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, e + 1, 0)
        );
      }

      // else, h == w
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, e + 1, 0)
      );
    }

    if (h <= w - 1 && t == 0) {
      if (characteristicVector[h]) {
        return stateFactory.build(
            // [No Error]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, n, 0)
        );
      }

      // [Too Many Errors]: The edit distance has exceeded the max distance for
      // the candidate term.
      return null;
    }

    if (h <= w - 2 && t == 1) {
      if (characteristicVector[h]) {
        return stateFactory.build(
            // [No Error]: Increment index by two; leave error alone.
            positionFactory.build(i + 2, n, 0)
        );
      }

      // [Too Many Errors]: The edit distance has exceeded the max distance for
      // the candidate term.
      return null;
    }

    // else, h == w
    // ------------
    // [Too Many Errors]: The edit distance has exceeded the max distance for
    // the candidate term.
    return null;
  }
}
