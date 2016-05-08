package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * {@link Position}s are used to maintain a sorted, linked-list of positions
 * within {@link com.github.dylon.liblevenshtein.levenshtein.collection.State}s.
 * They are sorted to simplify and optimize various operations on the positions
 * (like subsumption and merging-in new positions).
 * @author Dylon Edwards
 * @since 3.0.0
 */
@Data
@RequiredArgsConstructor
public class Position implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Reference to the next node in this linked-list.  The next node may be null
   * -- GETTER --
   * Reference to the next node in this linked-list.  The next node may be null
   * if this is the tail of the list.
   * @return Reference to the next node in this linked-list.
   * -- SETTER --
   * Sets the next node in this linked-list.  The next node may be null if this
   * is the tail of the list.
   * @param next The next node in this linked-list.
   * @return This {@link Position} for fluency.
   */
  private Position next = null;

  /**
   * Index of the dictionary term represented by this coordinate.
   * -- GETTER --
   * Index of the dictionary term represented by this coordinate.
   * @return Index of the dictionary term represented by this coordinate.
   */
  private final int termIndex;

  /**
   * Number of accumulated errors at this coordinate.
   * -- GETTER --
   * Number of accumulated errors at this coordinate.
   * @return Number of accumulated errors at this coordinate.
   */
  private final int numErrors;

  /**
   * Whether this position should be treated specially, such as whether it
   * represents a tranposition, merge, or split.
   * @return Whether this position should be treated specially.
   */
  public boolean isSpecial() {
    return false;
  }
}
