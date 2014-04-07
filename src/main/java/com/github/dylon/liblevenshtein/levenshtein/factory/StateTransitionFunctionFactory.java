package com.github.dylon.liblevenshtein.levenshtein;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * Builds (and recycles) instances of {@link IStateTransitionFunction}
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class StateTransitionFunctionFactory
  implements IStateTransitionFunctionFactory {

  /**
   * Recycled transition functions
   */
  final Queue<StateTransitionFunction> transitions = new ArrayDeque<>();

  /**
   * Compares Levenshtein-state positions
   */
  Comparator<int[]> comparator;

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
