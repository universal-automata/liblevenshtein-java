package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Defines the methods on Levenshtein states, which consist of all possible
 * position vectors at the current index of the spelling candidate.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IState extends Serializable {

  /**
   * Number of positions in this state.
   * @return Number of positions in this state.
   */
  int size();

  /**
   * Adds a new position to the linked-list of positions in this state.
   * @param position Position to add to those already in this state.
   */
  void add(int[] position);

  /**
   * Inserts a position into a specific location of the linked-list of positions.
   * @param index Location at which to insert the position.
   * @param position Position vector to insert into this state.
   */
  void insert(int index, int[] position);

  /**
   * One of two cursors maintained in this state, which returns the position at
   * the requested index.  Subsequent requests for positions at the immediately
   * proceeding or preceding indices will begin at the index of the last
   * position requested, so lookup for consecutive positions is fast.
   * <p>
   * {@link #getOuter(int)} is used within the outer loops of
   * {@link com.github.dylon.liblevenshtein.levenshtein.UnsubsumeFunction}s.
   * @param index Location of the position to return
   * @return Position vector at the requested index.
   * @see #getInner(int)
   */
  int[] getOuter(int index);

  /**
   * One of two cursors maintained in this state, which returns the position at
   * the requested index.  Subsequent requests for positions at the immediately
   * proceeding or preceding indices will begin at the index of the last
   * position requested, so lookup for consecutive positions is fast.
   * <p>
   * {@link #getInner(int)} is used within the inner loops of
   * {@link com.github.dylon.liblevenshtein.levenshtein.UnsubsumeFunction}s.
   * @param index Location of the position to return
   * @return Position vector at the requested index.
   * @see #getOuter(int)
   */
  int[] getInner(int index);

  /**
   * Unlinks the position corresponding to the inner cursor, at the last index
   * requested of {@link #getInner(int)}.
   * @return Position vector that was unlinked from those in this state.
   */
  int[] removeInner();

  /**
   * Unlinks and recycles all the positions in this state, making it empty. This
   * is usually done just before this state is itself recycled.
   */
  void clear();

  /**
   * Merge-sorts the positions in this state in a fashion that makes
   * un-subsumption easy.
   * @param comparator Describes how to sort the positions (dependent on the
   * Levenshtein algorithm).
   */
  void sort(Comparator<int[]> comparator);
}
