package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Builds position vectors for the given algorithm.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class PositionFactory implements IPositionFactory {

  /**
   * Object pool for recycled positions.
   */
  protected final Queue<int[]> positions = new ArrayDeque<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] build(int i, int e) {
    throw new UnsupportedOperationException("build(i,e) is not implemented");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] build(int i, int e, int x) {
    throw new UnsupportedOperationException("build(i,e,x) is not implemented");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recycle(int[] position) {
    positions.offer(position);
  }

  /**
   * Builds position vectors for the standard algorithm.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForStandardPositions extends PositionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] build(final int i, final int e) {
      int[] position = positions.poll();

      if (null == position) {
        position = new int[2];
      }

      position[0] = i;
      position[1] = e;
      return position;
    }
  }

  /**
   * Builds position vectors for the transposition and merge-and-split
   * algorithms.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForXPositions extends PositionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] build(final int i, final int e, final int x) {
      int[] position = positions.poll();

      if (null == position) {
        position = new int[3];
      }

      position[0] = i;
      position[1] = e;
      position[2] = x;
      return position;
    }
  }
}
