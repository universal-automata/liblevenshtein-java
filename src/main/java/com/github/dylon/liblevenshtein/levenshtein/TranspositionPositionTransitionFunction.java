package com.github.dylon.liblevenshtein.levenshtein;

public class TranspositionPositionTransitionFunction
  extends AbstractPositionTransitionFunction {

  @Override
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
        final int a = (n - e < Integer.MAX_VALUE)
          ? n - e + 1
          : Integer.MAX_VALUE;
        final int b = w - h;
        final int k = (a < b) ? a : b;
        final int j = indexOf(characteristicVector, k, h);

        switch (j) {
          case 0:
            return stateFactory.build(
                positionFactory.build((i + 1), 0, 0));
          case 1:
            return stateFactory.build(
                positionFactory.build(i, 1, 0),
                positionFactory.build(i, 1, 1),
                positionFactory.build((i + 1), 1, 0),
                positionFactory.build((i + 2), 1, 0));
          case -1:
            return stateFactory.build(
                positionFactory.build(i, 1, 0),
                positionFactory.build((i + 1), 1, 0));
          default: // j > 1
            return stateFactory.build(
                positionFactory.build(i, 1, 0),
                positionFactory.build((i + 1), 1, 0),
                positionFactory.build((i + j + 1), j, 0));
        }
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              positionFactory.build((i + 1), 0, 0));
        }

        return stateFactory.build(
            positionFactory.build(i, 1, 0),
            positionFactory.build((i + 1), 1, 0));
      }

      // else, h == 2
      return stateFactory.build(
          positionFactory.build(i, 1, 0));
    }

    if (1 <= e && e < n) {
      if (h <= w - 2) {
        if (t == 0) {
          final int a = (n - e < Integer.MAX_VALUE)
            ? n - e + 1
            : Integer.MAX_VALUE;
          final int b = w - h;
          final int k = (a < b) ? a : b;
          final int j = indexOf(characteristicVector, k, h);

          switch (j) {
            case 0:
              return stateFactory.build(
                  positionFactory.build((i + 1), e, 0));
            case 1:
              return stateFactory.build(
                  positionFactory.build(i, (e + 1), 0),
                  positionFactory.build(i, (e + 1), 1),
                  positionFactory.build((i + 1), (e + 1), 0),
                  positionFactory.build((i + 2), (e + 1), 0));
            case -1:
              return stateFactory.build(
                  positionFactory.build(i, (e + 1), 0),
                  positionFactory.build((i + 1), (e + 1), 0));
            default: // j > 1
              return stateFactory.build(
                  positionFactory.build(i, (e + 1), 0),
                  positionFactory.build((i + 1), (e + 1), 0),
                  positionFactory.build((i + j + 1), (e + j), 0));
          }
        }

        if (characteristicVector[h]) {
          return stateFactory.build(
              positionFactory.build((i + 2), e, 0));
        }

        return null;
      }

      if (h == w - 1) {
        if (characteristicVector[h]) {
          return stateFactory.build(
              positionFactory.build((i + 1), e, 0));
        }

        return stateFactory.build(
            positionFactory.build(i, (e + 1), 0),
            positionFactory.build((i + 1), (e + 1), 0));
      }

      // else, h == w
      return stateFactory.build(
          positionFactory.build(i, (e + 1), 0));
    }

    if ((h <= w - 1) && (t == 0)) {
      if (characteristicVector[h]) {
        return stateFactory.build(
            positionFactory.build((i + 1), n, 0));
      }

      return null;
    }

    if ((h <= w - 2) && (t == 1)) {
    	if (characteristicVector[h]) {
      	return stateFactory.build(
          	positionFactory.build((i + 2), n, 0));
    	}

    	return null;
    }

    // else, h == w
    return null;
  }
}
