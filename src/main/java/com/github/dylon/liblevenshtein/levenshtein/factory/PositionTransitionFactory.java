package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.MergeAndSplitPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.TranspositionPositionTransitionFunction;

/**
 * Builds position-transition functions for Levenshtein states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class PositionTransitionFactory implements IPositionTransitionFactory {

  /**
   * Object pool of recycled transitions
   */
  final Queue<IPositionTransitionFunction> transitions = new ArrayDeque<>();

  /**
   * Builds and recycles Levenshtein states.
   * -- SETTER --
   * Builds and recycles Levenshtein states.
   * @param stateFactory Builds and recycles Levenshtein states.
   * @return This {@link PositionTransitionFactory} for fluency.
   */
  @Setter IStateFactory stateFactory;

  /**
   * Builds and recycles Levenshtein positions.
   * -- SETTER --
   * Builds and recycles Levenshtein positions.
   * @param positionFactory Builds and recycles Levenshtein positions.
   * @return This {@link PositionTransitionFactory} for fluency.
   */
  @Setter IPositionFactory positionFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public IPositionTransitionFunction build() {
    IPositionTransitionFunction transition = transitions.poll();

    if (null == transition) {
      transition = create();
    }

    return transition;
  }

  /**
   * Factory method that builds Levenshtein algorithm-specific,
   * position-transition functions.
   * @return Levenshtein algorithm-specific, position-transition function.
   */
  protected abstract IPositionTransitionFunction create();

  /**
   * {@inheritDoc}
   */
  @Override
  public void recycle(final IPositionTransitionFunction transition) {
    transitions.offer(transition);
  }

  /**
   * Builds position-transition functions for standard, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForStandardPositions extends PositionTransitionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    protected IPositionTransitionFunction create() {
      return new StandardPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }

  /**
   * Builds position-transition functions for transposition, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForTranspositionPositions extends PositionTransitionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    protected IPositionTransitionFunction create() {
      return new TranspositionPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }

  /**
   * Builds position-transition functions for merge-and-split, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForMergeAndSplitPositions extends PositionTransitionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    protected IPositionTransitionFunction create() {
      return new MergeAndSplitPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }
}
