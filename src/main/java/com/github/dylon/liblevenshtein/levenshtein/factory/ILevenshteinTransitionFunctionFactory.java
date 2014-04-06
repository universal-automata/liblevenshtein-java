package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ILevenshteinTransitionFunctionFactory {

  /**
   * @param maxDistance
   */
  ILevenshteinTransitionFunction build(int maxDistance);

  /**
   * @param transition
   */
  void recycle(ILevenshteinTransitionFunction transition);
}
