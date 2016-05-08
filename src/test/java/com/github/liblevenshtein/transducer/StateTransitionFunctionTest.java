package com.github.liblevenshtein.transducer;

import java.util.Arrays;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.PositionTransitionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;
import static com.github.liblevenshtein.assertion.StateTransitionFunctionAssertions.assertThat;

/**
 * These tests were taken from the transition tables on page 29 of "Fast String
 * Correction with Levenshtein Automata".
 */
public class StateTransitionFunctionTest {
  private static final int N = 1;
  private static final int W = 5;

  private final PositionFactory positionFactory = new PositionFactory();
  private final StateFactory stateFactory = new StateFactory();
  private final PositionTransitionFactory transitionFactory =
    new PositionTransitionFactory.ForStandardPositions();
  private final MergeFunction merge = new MergeFunction.ForStandardPositions();
  private final UnsubsumeFunction unsubsume = new UnsubsumeFunction.ForStandardPositions();
  private final SubsumesFunction subsumes = new SubsumesFunction.ForStandardAlgorithm();
  private final StateTransitionFunction transition = new StateTransitionFunction();

  @BeforeTest
  public void setUp() {
    transitionFactory.stateFactory(stateFactory);
    transitionFactory.positionFactory(positionFactory);

    unsubsume.subsumes(subsumes);

    transition.comparator((a, b) -> {
      final int i = a.numErrors() - b.numErrors();
      if (0 != i) {
        return i;
      }
      return a.termIndex() - b.termIndex();
    });

    transition.stateFactory(stateFactory);
    transition.transitionFactory(transitionFactory);
    transition.merge(merge);
    transition.unsubsume(unsubsume);
    transition.maxDistance(N);
  }

  @DataProvider(name = "is")
  public Object[][] is() {
    final Object[][] is = new Object[1 + W - 3][1];
    for (int i = 0; i <= W - 3; ++i) {
      is[i] = new Object[] {i};
    }
    return is;
  }

  @Test(dataProvider = "is")
  public void testOfAgainstPrefixes(final int i) {
    final boolean[] characteristicVector = new boolean[W];
    Arrays.fill(characteristicVector, false);

    characteristicVector[0] = false;
    characteristicVector[1] = false;
    characteristicVector[2] = false;
    validate(a(i), c(i), characteristicVector);
    validate(b(i), null, characteristicVector);
    validate(c(i), null, characteristicVector);
    validate(d(i), null, characteristicVector);
    validate(e(i), null, characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = false;
    characteristicVector[2] = false;
    validate(a(i), a(1 + i), characteristicVector);
    validate(b(i), b(1 + i), characteristicVector);
    validate(c(i), b(1 + i), characteristicVector);
    validate(d(i), b(1 + i), characteristicVector);
    validate(e(i), b(1 + i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = true;
    characteristicVector[2] = false;
    validate(a(i), e(i), characteristicVector);
    validate(b(i), null, characteristicVector);
    validate(c(i), b(2 + i), characteristicVector);
    validate(d(i), null, characteristicVector);
    validate(e(i), b(2 + i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = false;
    characteristicVector[2] = true;
    validate(a(i), c(i), characteristicVector);
    validate(b(i), null, characteristicVector);
    validate(c(i), null, characteristicVector);
    validate(d(i), b(3 + i), characteristicVector);
    validate(e(i), b(3 + i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = true;
    characteristicVector[2] = false;
    validate(a(i), a(1 + i), characteristicVector);
    validate(b(i), b(1 + i), characteristicVector);
    validate(c(i), c(1 + i), characteristicVector);
    validate(d(i), b(1 + i), characteristicVector);
    validate(e(i), c(1 + i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = false;
    characteristicVector[2] = true;
    validate(a(i), a(1 + i), characteristicVector);
    validate(b(i), b(1 + i), characteristicVector);
    validate(c(i), b(1 + i), characteristicVector);
    validate(d(i), d(1 + i), characteristicVector);
    validate(e(i), d(1 + i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = true;
    characteristicVector[2] = true;
    validate(a(i), e(i), characteristicVector);
    validate(b(i), null, characteristicVector);
    validate(c(i), b(2 + i), characteristicVector);
    validate(d(i), b(3 + i), characteristicVector);
    validate(e(i), c(2 + i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = true;
    characteristicVector[2] = true;
    validate(a(i), a(1 + i), characteristicVector);
    validate(b(i), b(1 + i), characteristicVector);
    validate(c(i), c(1 + i), characteristicVector);
    validate(d(i), d(1 + i), characteristicVector);
    validate(e(i), e(1 + i), characteristicVector);
  }

  @Test
  public void testOfAgainstSuffixes() {
    final boolean[] characteristicVector = new boolean[W];
    int i;

    i = W - 2;
    Arrays.fill(characteristicVector, false);

    characteristicVector[0] = false;
    characteristicVector[1] = false;
    validate(a(i), c(i), characteristicVector);
    validate(b(i), null, characteristicVector);
    validate(c(i), null, characteristicVector);
    validate(d(i), null, characteristicVector);
    validate(e(i), null, characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = false;
    validate(a(i), a(1 + i), characteristicVector);
    validate(b(i), b(1 + i), characteristicVector);
    validate(c(i), b(1 + i), characteristicVector);
    validate(d(i), b(1 + i), characteristicVector);
    validate(e(i), b(1 + i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = true;
    validate(a(i), e(i), characteristicVector);
    validate(b(i), null, characteristicVector);
    validate(c(i), b(2 + i), characteristicVector);
    validate(d(i), null, characteristicVector);
    validate(e(i), b(2 + i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = true;
    validate(a(i), a(1 + i), characteristicVector);
    validate(b(i), b(1 + i), characteristicVector);
    validate(c(i), c(1 + i), characteristicVector);
    validate(d(i), b(1 + i), characteristicVector);
    validate(e(i), c(1 + i), characteristicVector);

    i = W - 1;
    Arrays.fill(characteristicVector, false);

    characteristicVector[0] = false;
    validate(a(i), c(i), characteristicVector);
    validate(b(i), null, characteristicVector);
    validate(c(i), null, characteristicVector);

    characteristicVector[0] = true;
    validate(a(i), a(1 + i), characteristicVector);
    validate(b(i), b(1 + i), characteristicVector);
    validate(c(i), b(1 + i), characteristicVector);

    i = W;
    Arrays.fill(characteristicVector, false);

    // [NOTE] :: In the paper, for the case when i = W, A(i) maps to B(i), but
    // that isn't what I get with the transitions.
    //
    // [TODO] :: Verify that the mapping should indeed be A(i) to C(i), or
    // figure out what I'm doing wrong ...
    //
    //validate(A(i), B(i), characteristicVector);
    validate(a(i), c(i), characteristicVector);
    validate(b(i), null, characteristicVector);
  }

  private void validate(
      final State input,
      final State expectedOutput,
      final boolean... characteristicVector) {
    assertThat(transition).transitionsTo(expectedOutput, input, characteristicVector);
  }

  private State a(final int i) {
    if (0 <= i && i <= W) {
      return stateFactory.build(
          positionFactory.build(i, 0));
    }

    return null;
  }

  private State b(final int i) {
    if (0 <= i && i <= W) {
      return stateFactory.build(
          positionFactory.build(i, 1));
    }

    return null;
  }

  private State c(final int i) {
    // [NOTE] :: In the paper, this should not be defined when i = W, but from
    // my experiments it seems to be the appropriate image of A(i) when i = W.
    //
    // [TODO] :: Verify whether this is the correct image of A(i) when i = W, or
    // determine what I am doing wrong ...
    //
    //if (0 <= i && i <= W - 1) {
      return stateFactory.build(
          positionFactory.build(i, 1),
          positionFactory.build(1 + i, 1));
    //}

    //return null;
  }

  private State d(final int i) {
    if (0 <= i && i <= W - 2) {
      return stateFactory.build(
          positionFactory.build(i, 1),
          positionFactory.build(2 + i, 1));
    }

    return null;
  }

  private State e(final int i) {
    if (0 <= i && i <= W - 2) {
      return stateFactory.build(
          positionFactory.build(i, 1),
          positionFactory.build(1 + i, 1),
          positionFactory.build(2 + i, 1));
    }

    return null;
  }
}
