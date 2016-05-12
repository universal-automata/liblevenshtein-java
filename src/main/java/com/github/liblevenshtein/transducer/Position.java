package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * {@link Position}s are used to maintain a sorted, linked-list of positions
 * within {@link com.github.liblevenshtein.transducer.State}s.  They are sorted
 * to simplify and optimize various operations on the positions (like
 * subsumption and merging-in new positions).
 * @author Dylon Edwards
 * @since 3.0.0
 */
@Data
@RequiredArgsConstructor
public class Position implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Reference to the next node in this linked-list.  The next node may be null
   */
  private Position next = null;

  /**
   * Index of the dictionary term represented by this coordinate.
   */
  private final int termIndex;

  /**
   * Number of accumulated errors at this coordinate.
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
