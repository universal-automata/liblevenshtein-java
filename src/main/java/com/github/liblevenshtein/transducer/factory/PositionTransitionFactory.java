package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.github.liblevenshtein.transducer.MergeAndSplitPositionTransitionFunction;
import com.github.liblevenshtein.transducer.PositionTransitionFunction;
import com.github.liblevenshtein.transducer.StandardPositionTransitionFunction;
import com.github.liblevenshtein.transducer.TranspositionPositionTransitionFunction;

/**
 * Builds position-transition functions for Levenshtein states.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PositionTransitionFactory implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds Levenshtein states.
   * -- SETTER --
   * Builds Levenshtein states.
   * @param stateFactory Builds Levenshtein states.
   * @return This {@link PositionTransitionFactory} for fluency.
   */
  protected StateFactory stateFactory;

  /**
   * Builds Levenshtein positions.
   * -- SETTER --
   * Builds Levenshtein positions.
   * @param positionFactory Builds Levenshtein positions.
   * @return This {@link PositionTransitionFactory} for fluency.
   */
  protected PositionFactory positionFactory;

  /**
   * Builds a new position-transition function.
   * @return New position-transition function.
   */
  public abstract PositionTransitionFunction build();

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
    public PositionTransitionFunction build() {
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
    public PositionTransitionFunction build() {
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
    public PositionTransitionFunction build() {
      return new MergeAndSplitPositionTransitionFunction()
        .stateFactory(stateFactory)
        .positionFactory(positionFactory);
    }
  }
}
