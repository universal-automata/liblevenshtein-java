package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.IMergeFunction;
import com.github.dylon.liblevenshtein.levenshtein.IStateTransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.IUnsubsumeFunction;
import com.github.dylon.liblevenshtein.levenshtein.StateTransitionFunction;

/**
 * Builds (and recycles) instances of {@link IStateTransitionFunction}
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class StateTransitionFactory implements IStateTransitionFactory {

  /**
   * Recycled transition functions
   */
  final Queue<StateTransitionFunction> transitions = new ArrayDeque<>();

  /**
   * Compares Levenshtein-state positions
   * -- SETTER --
   * Compares Levenshtein-state positions
   * @param comparator Compares Levenshtein-state positions
   * @return This {@link StateTransitionFactory} for fluency.
   */
  @Setter Comparator<int[]> comparator;

  /**
   * Builds and recycles Levenshtein states.
   * -- SETTER --
   * Builds and recycles Levenshtein states.
   * @param stateFactory Builds and recycles Levenshtein states.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  @Setter private IStateFactory stateFactory;

  /**
   * Builds and recycles position-transition functions.
   * -- SETTER --
   * Builds and recycles position-transition functions.
   * @param positionTransitionFactory Builds and recycles position-transition
   * functions.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  @Setter private IPositionTransitionFactory positionTransitionFactory;

  /**
   * Merges Levenshtein states together.
   * -- SETTER --
   * Merges Levenshtein states together.
   * @param merge Merges Levenshtein states together.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  @Setter private IMergeFunction merge;

  /**
   * Removes subsumed positions from Levenshtein states.
   * -- SETTER --
   * Removes subsumed positions from Levenshtein states.
   * @param unsubsume Removes subsumed positions from Levenshtein states.
   * @return This {@link StateTransitionFactory} for fluency.
   */
  @Setter private IUnsubsumeFunction unsubsume;

  /**
   * {@inheritDoc}
   */
  @Override
  public IStateTransitionFunction build(final int maxDistance) {
    StateTransitionFunction transition = transitions.poll();

    if (null == transition) {
      transition = new StateTransitionFunction();
    }

    transition.comparator(comparator);
    transition.stateFactory(stateFactory);
    transition.transitionFactory(positionTransitionFactory);
    transition.merge(merge);
    transition.unsubsume(unsubsume);
    transition.maxDistance(maxDistance);
    return transition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void recycle(final IStateTransitionFunction transition) {
    //transition.maxDistance(0); // primitive type: leave alone
    transitions.offer((StateTransitionFunction) transition);
  }
}
