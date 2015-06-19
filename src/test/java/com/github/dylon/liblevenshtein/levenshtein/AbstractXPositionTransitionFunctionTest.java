package com.github.dylon.liblevenshtein.levenshtein;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;

public abstract class AbstractXPositionTransitionFunctionTest {
	protected static final int N = 3; // max number of errors
	protected static final int W = 4; // length of characteristic vector

	protected final AbstractPositionTransitionFunction transition = buildTransition();

	protected final ElementFactory<int[]> elementFactory = new ElementFactory<>();
	protected final StateFactory stateFactory = new StateFactory();
	protected final PositionFactory positionFactory = new PositionFactory.ForXPositions();

	protected int i = 0;
	protected int e = 0;
	protected int x = 0;

	protected AbstractXPositionTransitionFunctionTest() {
		stateFactory.elementFactory(elementFactory);
		transition.stateFactory(stateFactory);
		transition.positionFactory(positionFactory);
	}

	protected abstract AbstractPositionTransitionFunction buildTransition();

	protected void check(boolean... characteristicVector) {
		final IState actualState = transition.of(N, new int[] {i,e,x}, characteristicVector, 0);
		assertNull(actualState);
	}

	protected void check(
			final int i1, final int e1, final int t1,
			final boolean... characteristicVector) {
		final IState actualState = transition.of(N, new int[] {i,e,x}, characteristicVector, 0);
		final int[] position = positionFactory.build(i1,e1,t1);
		final IState expectedState = stateFactory.build(position);
		assertEquals(actualState, expectedState);
	}

	protected void check(
			final int i1, final int e1, final int t1,
			final int i2, final int e2, final int t2,
			final boolean... characteristicVector) {
		final IState actualState = transition.of(N, new int[] {i,e,x}, characteristicVector, 0);
		final int[] p1 = positionFactory.build(i1,e1,t1);
		final int[] p2 = positionFactory.build(i2,e2,t2);
		final IState expectedState = stateFactory.build(p1,p2);
		assertEquals(actualState, expectedState);
	}

	protected void check(
			final int i1, final int e1, final int t1,
			final int i2, final int e2, final int t2,
			final int i3, final int e3, final int t3,
			final boolean... characteristicVector) {
		final IState actualState = transition.of(N, new int[] {i,e,x}, characteristicVector, 0);
		final int[] p1 = positionFactory.build(i1,e1,t1);
		final int[] p2 = positionFactory.build(i2,e2,t2);
		final int[] p3 = positionFactory.build(i3,e3,t3);
		final IState expectedState = stateFactory.build(p1,p2,p3);
		assertEquals(actualState, expectedState);
	}

	protected void check(
			final int i1, final int e1, final int t1,
			final int i2, final int e2, final int t2,
			final int i3, final int e3, final int t3,
			final int i4, final int e4, final int t4,
			final boolean... characteristicVector) {
		final IState actualState = transition.of(N, new int[] {i,e,x}, characteristicVector, 0);
		final int[] p1 = positionFactory.build(i1,e1,t1);
		final int[] p2 = positionFactory.build(i2,e2,t2);
		final int[] p3 = positionFactory.build(i3,e3,t3);
		final int[] p4 = positionFactory.build(i4,e4,t4);
		final IState expectedState = stateFactory.build(p1,p2,p3,p4);
		assertEquals(actualState, expectedState);
	}
}
