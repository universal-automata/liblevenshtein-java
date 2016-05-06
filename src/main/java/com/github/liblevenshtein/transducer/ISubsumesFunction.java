package com.github.liblevenshtein.transducer;

import java.io.Serializable;

/**
 * Determines whether the position represented by the combination of parameters
 * in the left half subsumes the position represented by the combination of
 * parameters in the right half.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ISubsumesFunction extends Serializable {

  /**
   * Determines whether the standard, Levenshtein state represented by
   * {@code (i,e)} subsumes the other, represented by {@code (j,f)}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @return Whether {@code (i,e)} subsumes {@code (j,f)}
   */
  boolean at(int i, int e, int j, int f);

  /**
   * Determines whether the transposition or merge-and-split, Levenshtein state
   * represented by {@code (i,e,s)} subsumes the other, represented by
   * {@code (j,f,t)}, under the maximum number of errors, {@code n}.
   * @param i Index of the spelling candidate of the first position.
   * @param e Number of accumulated errors of the spelling candidate at index
   * {@code i} of the first position.
   * @param s Either {@code 0} or {@code 1}, depending on whether the first
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param j Index of the spelling candidate of the second position.
   * @param f Number of accumulated errors of the spelling candidate at index
   * {@code j} of the second position.
   * @param t Either {@code 0} or {@code 1}, depending on whether the second
   * position is a regular state or a special case (e.g. transposition, merge,
   * or split).
   * @param n Maximum number of errors allowed in spelling candidates.
   * @return Whether {@code (i,e,s)} subsumes {@code (j,f,t)}
   */
  boolean at(int i, int e, int s, int j, int f, int t, int n);
}
