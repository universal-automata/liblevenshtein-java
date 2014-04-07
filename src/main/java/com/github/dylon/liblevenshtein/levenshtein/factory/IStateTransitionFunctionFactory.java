package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IStateTransitionFunctionFactory {

  /**
   * @param maxDistance
   */
  IStateTransitionFunction build(int maxDistance);

  /**
   * @param transition
   */
  void recycle(IStateTransitionFunction transition);
}
