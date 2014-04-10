package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.IStateTransitionFunction;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IStateTransitionFactory {

  /**
   * @param maxDistance
   */
  IStateTransitionFunction build(int maxDistance);

  /**
   * @param transition
   */
  void recycle(IStateTransitionFunction transition);
}
