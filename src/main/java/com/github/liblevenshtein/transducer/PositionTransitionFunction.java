package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.Setter;

import com.github.liblevenshtein.transducer.factory.PositionFactory;
import com.github.liblevenshtein.transducer.factory.StateFactory;

/**
 * Implements common logic shared among the position-transition functions, which
 * for their specific algorithm take one of the positions in a state and a few
 * parameters, and return all posible positions leading from that one, under the
 * constraints of the given paramters and the Levenshtein algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Setter
public abstract class PositionTransitionFunction implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds and caches states for the transducer.
   * -- SETTER --
   * Builds and caches states for the transducer.
   * @param stateFactory Builds and caches states for the transducer.
   * @return This {@link PositionTransitionFunction} for fluency.
   */
  protected StateFactory stateFactory;

  /**
   * Builds and caches positions for states in the transducer.
   * -- SETTER --
   * Builds and caches positions for states in the transducer.
   * @param positionFactory Builds and caches positions for states in the transducer.
   * @return This {@link PositionTransitionFunction} for fluency.
   */
  protected PositionFactory positionFactory;

  /**
   * Returns the first index of the characteristic vector between indices, i and
   * k, that is true.  This corresponds to the first index of the relevant
   * subword whose element is the character of interest.
   * @param characteristicVector Contains relevant subwords, which are booleans
   * denoting whether the index at their locations correspond to the character
   * being sought.
   * @param k Last index of {@code characteristicVector} to examine for the
   * first index of the relevant subword whose element is the character of
   * interest.
   * @param i First index of {@code characteristicVector} to examine for the
   * first index of the relevant subword whose element is the character of
   * interest.
   * @return If a character match exists in the relevant subword, the first
   * index of the relevant subword whose element is it.  Otherwise, -1 is
   * returned if no match exists (in the relevant subword).  Note that
   * {@code characteristicVector} may have more elements than are examined.
   */
  protected int indexOf(
      final boolean[] characteristicVector,
      final int k,
      final int i) {

    for (int j = 0; j < k; ++j) {
      if (characteristicVector[i + j]) {
        return j;
      }
    }

    return -1;
  }

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
  public abstract State of(
      int n,
      Position position,
      boolean[] characteristicVector,
      int offset);
}
