package com.github.dylon.liblevenshtein.levenshtein;

import lombok.val;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import com.github.dylon.liblevenshtein.levenshtein.factory.ElementFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.PositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.StateFactory;

public class MergeFunctionTest {

	@Test
	public void testStandardPositions() {
		final MergeFunction merge = new MergeFunction.ForStandardPositions();
		val elementFactory = new ElementFactory<int[]>();
		val stateFactory = new StateFactory();
		val positionFactory = new PositionFactory.ForStandardPositions();
		init(elementFactory, stateFactory, positionFactory, merge);

		final IState s1 = stateFactory.build(
				positionFactory.build(2,3),
				positionFactory.build(1,2));

		final IState s2 = stateFactory.build(
				positionFactory.build(3,3),
				positionFactory.build(2,3),
				positionFactory.build(3,2),
				positionFactory.build(2,2),
				positionFactory.build(0,2));

		final IState s3 = stateFactory.build(
				positionFactory.build(3,3),
				positionFactory.build(2,3),
				positionFactory.build(3,2),
				positionFactory.build(2,2),
				positionFactory.build(1,2),
				positionFactory.build(0,2));

		merge.into(s1, s2);
		assertEquals(s1, s3);
	}

	@Test
	public void testXPositions() {
		final MergeFunction merge = new MergeFunction.ForXPositions();
		val elementFactory = new ElementFactory<int[]>();
		val stateFactory = new StateFactory();
		val positionFactory = new PositionFactory.ForXPositions();
		init(elementFactory, stateFactory, positionFactory, merge);

		final IState s1 = stateFactory.build(
				positionFactory.build(2,3,1),
				positionFactory.build(2,3,0),
				positionFactory.build(1,2,0));

		final IState s2 = stateFactory.build(
				positionFactory.build(2,4,0),
				positionFactory.build(2,3,0),
				positionFactory.build(2,2,1),
				positionFactory.build(3,0,0));

		final IState s3 = stateFactory.build(
				positionFactory.build(2,4,0),
				positionFactory.build(2,3,1),
				positionFactory.build(2,3,0),
				positionFactory.build(2,2,1),
				positionFactory.build(1,2,0),
				positionFactory.build(3,0,0));

		merge.into(s1, s2);
		assertEquals(s1, s3);
	}

	private void init(
			final ElementFactory<int[]> elementFactory,
			final StateFactory stateFactory,
			final PositionFactory positionFactory,
			final MergeFunction merge) {
		stateFactory.elementFactory(elementFactory);
		merge.positionFactory(positionFactory);
	}
}
