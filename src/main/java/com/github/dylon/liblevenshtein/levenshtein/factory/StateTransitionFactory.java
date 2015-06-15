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
   */
  @Setter Comparator<int[]> comparator;

  @Setter private IStateFactory stateFactory;

  @Setter private IPositionTransitionFactory positionTransitionFactory;

  @Setter private IMergeFunction merge;

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
