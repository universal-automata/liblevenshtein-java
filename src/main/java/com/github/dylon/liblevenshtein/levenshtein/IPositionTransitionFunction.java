package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;

/**
 * Transitions an input position of a Levenshtein state to all candidate
 * positions, conditioned by a set of parameters.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IPositionTransitionFunction extends Serializable {

  /**
   * Returns a state with all the possible transitions from the current
   * position.
   * @param n Maximum number of errors to tolerate in spelling candidates.
   * @param position Vector consisting of the current index of the spelling
   * candidate, the number of errors accumulated up to that index, and
   * (optionally) a flag specifying whether the position is a special kind as
   * defined by its specific, Levenshtein algorithm (e.g. whether it is a
   * transposition position, etc.).
   * @param characteristicVector Relevant subwords consisting of booleans about
   * whether the characters associated by their indices in the spelling
   * candidate are the same as the character being sought from the query term.
   * @param offset Offset for various operations within the transition function.
   * @return New state consisting of all possible transitions for the given
   * position, given the other paramters.
   */
  IState of(int n, int[] position, boolean[] characteristicVector, int offset);
}
