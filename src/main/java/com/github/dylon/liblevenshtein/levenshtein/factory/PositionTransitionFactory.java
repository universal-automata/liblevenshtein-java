package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.MergeAndSplitPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.StandardPositionTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.TranspositionPositionTransitionFunction;

/**
 * Builds position-transition functions for Levenshtein states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@RequiredArgsConstructor
public abstract class PositionTransitionFactory implements IPositionTransitionFactory, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds and recycles Levenshtein states.
   * -- SETTER --
   * Builds and recycles Levenshtein states.
   * @param stateFactory Builds and recycles Levenshtein states.
   * @return This {@link PositionTransitionFactory} for fluency.
   */
  @Setter protected IStateFactory stateFactory;

  /**
   * Builds and recycles Levenshtein positions.
   * -- SETTER --
   * Builds and recycles Levenshtein positions.
   * @param positionFactory Builds and recycles Levenshtein positions.
   * @return This {@link PositionTransitionFactory} for fluency.
   */
  @Setter protected IPositionFactory positionFactory;

  /**
   * Builds position-transition functions for standard, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForStandardPositions extends PositionTransitionFactory {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public IPositionTransitionFunction build() {
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

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public IPositionTransitionFunction build() {
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

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public IPositionTransitionFunction build() {
      return new MergeAndSplitPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }
}
