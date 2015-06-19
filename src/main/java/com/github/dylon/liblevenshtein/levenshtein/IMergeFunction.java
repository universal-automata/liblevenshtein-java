package com.github.dylon.liblevenshtein.levenshtein;

public interface IMergeFunction {

  /**
   * Merges the positions in the latter state into the former, in a
   * subsumption-friendly manner.  You should assume that once this method has
   * returned, some of the positions in the latter state may have been recycled
   * and it should therefore be recycled, too (DO NOT RE-USE IT !!!).
   *
   * @param state The state into which the positions of the latter should be
   * merged.
   *
   * @param positions The state from which the positions should be merged.
   */
  void into(IState state, IState positions);
}
