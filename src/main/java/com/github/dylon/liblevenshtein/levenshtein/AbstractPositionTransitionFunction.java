package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IStateFactory;

/**
 * Implements common logic shared among the position-transition functions, which
 * for their specific algorithm take one of the positions in a state and a few
 * parameters, and return all posible positions leading from that one, under the
 * constraints of the given paramters and the Levenshtein algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class AbstractPositionTransitionFunction
  implements IPositionTransitionFunction, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds and caches states for the transducer.
   * -- SETTER --
   * Builds and caches states for the transducer.
   * @param stateFactory Builds and caches states for the transducer.
   * @return This {@link AbstractPositionTransitionFunction} for fluency.
   */
  @Setter IStateFactory stateFactory;

  /**
   * Builds and caches positions for states in the transducer.
   * -- SETTER --
   * Builds and caches positions for states in the transducer.
   * @param positionFactory Builds and caches positions for states in the transducer.
   * @return This {@link AbstractPositionTransitionFunction} for fluency.
   */
  @Setter IPositionFactory positionFactory;

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
}
