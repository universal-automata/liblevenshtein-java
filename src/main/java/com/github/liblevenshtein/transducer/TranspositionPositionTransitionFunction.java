package com.github.liblevenshtein.transducer;

/**
 * Transitions a transposition, Levenshtein position to all possible positions,
 * given a set of parameters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class TranspositionPositionTransitionFunction extends PositionTransitionFunction {

  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("checkstyle:methodlength")
  public State of(
      final int n,
      final Position position,
      final boolean[] characteristicVector,
      final int offset) {

    final int i = position.termIndex();
    final int e = position.numErrors();
    final boolean t = position.isSpecial();
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
                positionFactory.build(i + 1, 0, false)
            );
          case 1:
            return stateFactory.build(
                // [Insertion]: Leave index alone; increment the error by one.
                positionFactory.build(i, 1, false),
                // [Transposition]: Leave index alone; increment error by one.
                positionFactory.build(i, 1, true),
                // [Substitution]: Replace the current character; increment the
                // index by one; increment the error by one.
                positionFactory.build(i + 1, 1, false),
                // [Deletion]: Increment the index by one-more than a single
                // deletion; increment the error by one.
                positionFactory.build(i + 2, 1, false)
            );
          case -1:
            return stateFactory.build(
                // [Insertion]: Leave index alone; increment the error by one.
                positionFactory.build(i, 1, false),
                // [Substitution]: Replace the current character; increment the
                // index by one; increment the error by one.
                positionFactory.build(i + 1, 1, false)
            );
          default: // j > 1
            return stateFactory.build(
                // [Insertion]: Leave index alone; increment the error by one.
                positionFactory.build(i, 1, false),
                // [Substitution]: Replace the current character; increment the
                // index by one; increment the error by one.
                positionFactory.build(i + 1, 1, false),
                // [Deletion]: Increment index by one-more than the number of
                // deletions; increment error by the number of deletions.
                positionFactory.build(i + j + 1, j, false)
            );
        }
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by one; leave error alone
              positionFactory.build(i + 1, 0, false)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave index alone; increment error by one.
            positionFactory.build(i, 1, false),
            // [Substitution]: Increment index by one; increment error by one.
            positionFactory.build(i + 1, 1, false)
        );
      }

      // else, h == 2
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, 1, false)
      );
    }

    if (1 <= e && e < n) {
      if (h <= w - 2) {
        if (!t) {
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
                  positionFactory.build(i + 1, e, false)
              );
            case 1:
              return stateFactory.build(
                  // [Insertion]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, false),
                  // [Transposition]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, true),
                  // [Substitution]: Increment index by one; increment error by
                  // one.
                  positionFactory.build(i + 1, e + 1, false),
                  // [Deletion]: Increment index by one-more than one deletion;
                  // increment error by one.
                  positionFactory.build(i + 2, e + 1, false)
              );
            case -1:
              return stateFactory.build(
                  // [Insertion]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, false),
                  // [Substitution]: Increment index by one; increment error by
                  // one.
                  positionFactory.build(i + 1, e + 1, false)
              );
            default: // j > 1
              return stateFactory.build(
                  // [Insertion]: Leave index alone; increment error by one.
                  positionFactory.build(i, e + 1, false),
                  // [Substitution]: Increment index by one; increment error by
                  // one.
                  positionFactory.build(i + 1, e + 1, false),
                  // [Deletion]: Increment index by one-more than number of
                  // deletions; increment error by number of deletions.
                  positionFactory.build(i + j + 1, e + j, false)
              );
          }
        }

        if (characteristicVector[h]) {
          return stateFactory.build(
              // [No Error]: Increment index by two; leave error alone.
              positionFactory.build(i + 2, e, false)
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
              positionFactory.build(i + 1, e, false)
          );
        }

        return stateFactory.build(
            // [Insertion]: Leave index alone; increment error by one.
            positionFactory.build(i, e + 1, false),
            // [Substitution]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, e + 1, false)
        );
      }

      // else, h == w
      return stateFactory.build(
          // [Insertion]: Leave index alone; increment error by one.
          positionFactory.build(i, e + 1, false)
      );
    }

    if (h <= w - 1 && !t) {
      if (characteristicVector[h]) {
        return stateFactory.build(
            // [No Error]: Increment index by one; leave error alone.
            positionFactory.build(i + 1, n, false)
        );
      }

      // [Too Many Errors]: The edit distance has exceeded the max distance for
      // the candidate term.
      return null;
    }

    if (h <= w - 2 && t) {
      if (characteristicVector[h]) {
        return stateFactory.build(
            // [No Error]: Increment index by two; leave error alone.
            positionFactory.build(i + 2, n, false)
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
