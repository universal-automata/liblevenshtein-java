package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;
import java.util.Comparator;

import lombok.Setter;

import com.github.liblevenshtein.transducer.IMergeFunction;
import com.github.liblevenshtein.transducer.IStateTransitionFunction;
import com.github.liblevenshtein.transducer.IUnsubsumeFunction;
import com.github.liblevenshtein.transducer.StateTransitionFunction;

/**
 * Builds (and recycles) instances of {@link IStateTransitionFunction}.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Setter
public class StateTransitionFactory implements IStateTransitionFactory, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Compares Levenshtein-state positions.
   * -- SETTER --
   * Compares Levenshtein-state positions.
   * @param comparator Compares Levenshtein-state positions.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private Comparator<int[]> comparator;

  /**
   * Builds and recycles Levenshtein states.
   * -- SETTER --
   * Builds and recycles Levenshtein states.
   * @param stateFactory Builds and recycles Levenshtein states.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private IStateFactory stateFactory;

  /**
   * Builds and recycles position-transition functions.
   * -- SETTER --
   * Builds and recycles position-transition functions.
   * @param positionTransitionFactory Builds and recycles position-transition
   * functions.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private IPositionTransitionFactory positionTransitionFactory;

  /**
   * Merges Levenshtein states together.
   * -- SETTER --
   * Merges Levenshtein states together.
   * @param merge Merges Levenshtein states together.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private IMergeFunction merge;

  /**
   * Removes subsumed positions from Levenshtein states.
   * -- SETTER --
   * Removes subsumed positions from Levenshtein states.
   * @param unsubsume Removes subsumed positions from Levenshtein states.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private IUnsubsumeFunction unsubsume;

  /**
   * {@inheritDoc}
   */
  @Override
  public IStateTransitionFunction build(final int maxDistance) {
    return new StateTransitionFunction()
      .comparator(comparator)
      .stateFactory(stateFactory)
      .transitionFactory(positionTransitionFactory)
      .merge(merge)
      .unsubsume(unsubsume)
      .maxDistance(maxDistance);
  }
}
