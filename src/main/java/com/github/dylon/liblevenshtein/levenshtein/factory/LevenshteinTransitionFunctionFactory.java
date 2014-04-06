package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Queue;
import java.util.ArrayDeque;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * Builds (and recycles) instances of {@link ILevenshteinTransitionFunction}
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class LevenshteinTransitionFunctionFactory
  implements ILevenshteinTransitionFunctionFactory {

  /**
   * Recycled transition functions
   */
  Queue<LevenshteinTransitionFunction> transitions = new ArrayDeque<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public ILevenshteinTransitionFunction build(final int maxDistance) {
    LevenshteinTransitionFunction transition = transitions.poll();

    if (null == transition) {
      transition = new LevenshteinTransitionFunction();
    }

    transition.maxDistance(maxDistance);
    return transition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void recycle(final ILevenshteinTransitionFunction transition) {
    //transition.maxDistance(0); // primitive type: leave alone
    transitions.offer((LevenshteinTransitionFunction) transition);
  }
}
