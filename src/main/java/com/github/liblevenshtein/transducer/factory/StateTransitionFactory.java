package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;
import java.util.Comparator;

import lombok.Setter;

import com.github.liblevenshtein.transducer.MergeFunction;
import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.StateTransitionFunction;
import com.github.liblevenshtein.transducer.UnsubsumeFunction;

/**
 * Builds (and recycles) instances of {@link StateTransitionFunction}.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Setter
public class StateTransitionFactory implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Compares Levenshtein-state positions.
   * -- SETTER --
   * Compares Levenshtein-state positions
   * @param comparator Compares Levenshtein-state positions
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private Comparator<Position> comparator;

  /**
   * Builds Levenshtein states.
   * -- SETTER --
   * Builds Levenshtein states.
   * @param stateFactory Builds Levenshtein states.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private StateFactory stateFactory;

  /**
   * Builds position-transition functions.
   * -- SETTER --
   * Builds position-transition functions.
   * @param positionTransitionFactory Builds position-transition functions.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private PositionTransitionFactory positionTransitionFactory;

  /**
   * Merges Levenshtein states together.
   * -- SETTER --
   * Merges Levenshtein states together.
   * @param merge Merges Levenshtein states together.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private MergeFunction merge;

  /**
   * Removes subsumed positions from Levenshtein states.
   * -- SETTER --
   * Removes subsumed positions from Levenshtein states.
   * @param unsubsume Removes subsumed positions from Levenshtein states.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  private UnsubsumeFunction unsubsume;

  /**
   * Builds a new state-transition function that only considers spelling
   * candidates no more than {@code maxDistance} errors from the query term.
   * @param maxDistance Maximum number of errors tolerated in transforming the
   * query term into the spelling candidate.
   * @param queryLength Length of the query term.
   * @return New state-transition function that only considers spelling
   * candidates within {@code maxDistance} errors from the query term.
   */
  public StateTransitionFunction build(
      final int maxDistance,
      final int queryLength) {
    return new StateTransitionFunction()
      .comparator(comparator)
      .stateFactory(stateFactory)
      .transitionFactory(positionTransitionFactory)
      .merge(merge)
      .unsubsume(unsubsume)
      .maxDistance(maxDistance)
      .queryLength(queryLength);
  }
}
