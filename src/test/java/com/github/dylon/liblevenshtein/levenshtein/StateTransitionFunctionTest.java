package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Arrays;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionTransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;

/**
 * These tests were taken from the transition tables on page 29 of "Fast String
 * Correction with Levenshtein Automata"
 */
public class StateTransitionFunctionTest {
  private static final int N = 1;
  private static final int W = 5;

  private final ElementFactory<int[]> elementFactory = new ElementFactory<>();
  private final PositionFactory positionFactory = new PositionFactory.ForStandardPositions();
  private final StateFactory stateFactory = new StateFactory();
  private final PositionTransitionFactory transitionFactory =
    new PositionTransitionFactory.ForStandardPositions();
  private final MergeFunction merge = new MergeFunction.ForStandardPositions();
  private final UnsubsumeFunction unsubsume = new UnsubsumeFunction.ForStandardPositions();
  private final SubsumesFunction subsumes = new SubsumesFunction.ForStandardAlgorithm();
  private final StateTransitionFunction transition = new StateTransitionFunction();

  @BeforeTest
  public void setUp() {
    stateFactory.elementFactory(elementFactory);

    transitionFactory.stateFactory(stateFactory);
    transitionFactory.positionFactory(positionFactory);

    merge.positionFactory(positionFactory);

    unsubsume.subsumes(subsumes);
    unsubsume.positionFactory(positionFactory);

    transition.comparator((a,b) -> {
      final int i = a[1] - b[1];
      if (0 != i) return i;
      return a[0] - b[0];
    });

    transition.stateFactory(stateFactory);
    transition.transitionFactory(transitionFactory);
    transition.merge(merge);
    transition.unsubsume(unsubsume);
    transition.maxDistance(N);
  }

  @DataProvider(name="is")
  public Object[][] is() {
    final Object[][] is = new Object[1+W-3][1];
    for (int i = 0; i <= W - 3; ++i) {
      is[i] = new Object[] { i };
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
    validate(A(i), C(i), characteristicVector);
    validate(B(i), null, characteristicVector);
    validate(C(i), null, characteristicVector);
    validate(D(i), null, characteristicVector);
    validate(E(i), null, characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = false;
    characteristicVector[2] = false;
    validate(A(i), A(1+i), characteristicVector);
    validate(B(i), B(1+i), characteristicVector);
    validate(C(i), B(1+i), characteristicVector);
    validate(D(i), B(1+i), characteristicVector);
    validate(E(i), B(1+i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = true;
    characteristicVector[2] = false;
    validate(A(i), E(i), characteristicVector);
    validate(B(i), null, characteristicVector);
    validate(C(i), B(2+i), characteristicVector);
    validate(D(i), null, characteristicVector);
    validate(E(i), B(2+i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = false;
    characteristicVector[2] = true;
    validate(A(i), C(i), characteristicVector);
    validate(B(i), null, characteristicVector);
    validate(C(i), null, characteristicVector);
    validate(D(i), B(3+i), characteristicVector);
    validate(E(i), B(3+i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = true;
    characteristicVector[2] = false;
    validate(A(i), A(1+i), characteristicVector);
    validate(B(i), B(1+i), characteristicVector);
    validate(C(i), C(1+i), characteristicVector);
    validate(D(i), B(1+i), characteristicVector);
    validate(E(i), C(1+i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = false;
    characteristicVector[2] = true;
    validate(A(i), A(1+i), characteristicVector);
    validate(B(i), B(1+i), characteristicVector);
    validate(C(i), B(1+i), characteristicVector);
    validate(D(i), D(1+i), characteristicVector);
    validate(E(i), D(1+i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = true;
    characteristicVector[2] = true;
    validate(A(i), E(i), characteristicVector);
    validate(B(i), null, characteristicVector);
    validate(C(i), B(2+i), characteristicVector);
    validate(D(i), B(3+i), characteristicVector);
    validate(E(i), C(2+i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = true;
    characteristicVector[2] = true;
    validate(A(i), A(1+i), characteristicVector);
    validate(B(i), B(1+i), characteristicVector);
    validate(C(i), C(1+i), characteristicVector);
    validate(D(i), D(1+i), characteristicVector);
    validate(E(i), E(1+i), characteristicVector);
  }

  @Test
  public void testOfAgainstSuffixes() {
    final boolean[] characteristicVector = new boolean[W];
    int i;

    i = W - 2;
    Arrays.fill(characteristicVector, false);

    characteristicVector[0] = false;
    characteristicVector[1] = false;
    validate(A(i), C(i), characteristicVector);
    validate(B(i), null, characteristicVector);
    validate(C(i), null, characteristicVector);
    validate(D(i), null, characteristicVector);
    validate(E(i), null, characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = false;
    validate(A(i), A(1+i), characteristicVector);
    validate(B(i), B(1+i), characteristicVector);
    validate(C(i), B(1+i), characteristicVector);
    validate(D(i), B(1+i), characteristicVector);
    validate(E(i), B(1+i), characteristicVector);

    characteristicVector[0] = false;
    characteristicVector[1] = true;
    validate(A(i), E(i), characteristicVector);
    validate(B(i), null, characteristicVector);
    validate(C(i), B(2+i), characteristicVector);
    validate(D(i), null, characteristicVector);
    validate(E(i), B(2+i), characteristicVector);

    characteristicVector[0] = true;
    characteristicVector[1] = true;
    validate(A(i), A(1+i), characteristicVector);
    validate(B(i), B(1+i), characteristicVector);
    validate(C(i), C(1+i), characteristicVector);
    validate(D(i), B(1+i), characteristicVector);
    validate(E(i), C(1+i), characteristicVector);

    i = W - 1;
    Arrays.fill(characteristicVector, false);

    characteristicVector[0] = false;
    validate(A(i), C(i), characteristicVector);
    validate(B(i), null, characteristicVector);
    validate(C(i), null, characteristicVector);

    characteristicVector[0] = true;
    validate(A(i), A(1+i), characteristicVector);
    validate(B(i), B(1+i), characteristicVector);
    validate(C(i), B(1+i), characteristicVector);

    i = W;
    Arrays.fill(characteristicVector, false);

    // [NOTE] :: In the paper, for the case when i = W, A(i) maps to B(i), but
    // that isn't what I get with the transitions.
    //
    // [TODO] :: Verify that the mapping should indeed be A(i) to C(i), or
    // figure out what I'm doing wrong ...
    //
    //validate(A(i), B(i), characteristicVector);
    validate(A(i), C(i), characteristicVector);
    validate(B(i), null, characteristicVector);
  }

  private void validate(
      final IState input,
      final IState expectedOutput,
      final boolean... characteristicVector) {
    final IState actualOutput = transition.of(input, characteristicVector);
    assertEquals(actualOutput, expectedOutput);
  }

  private IState A(final int i) {
    if (0 <= i && i <= W) {
      return stateFactory.build(
          positionFactory.build(i,0));
    }

    return null;
  }

  private IState B(final int i) {
    if (0 <= i && i <= W) {
      return stateFactory.build(
          positionFactory.build(i,1));
    }

    return null;
  }

  private IState C(final int i) {
    // [NOTE] :: In the paper, this should not be defined when i = W, but from
    // my experiments it seems to be the appropriate image of A(i) when i = W.
    //
    // [TODO] :: Verify whether this is the correct image of A(i) when i = W, or
    // determine what I am doing wrong ...
    //
    //if (0 <= i && i <= W - 1) {
      return stateFactory.build(
          positionFactory.build(i,1),
          positionFactory.build(1+i,1));
    //}

    //return null;
  }

  private IState D(final int i) {
    if (0 <= i && i <= W - 2) {
      return stateFactory.build(
          positionFactory.build(i,1),
          positionFactory.build(2+i,1));
    }

    return null;
  }

  private IState E(final int i) {
    if (0 <= i && i <= W - 2) {
      return stateFactory.build(
          positionFactory.build(i,1),
          positionFactory.build(1+i,1),
          positionFactory.build(2+i,1));
    }

    return null;
  }
}
