package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class PositionFactory implements IPositionFactory {
  protected final Queue<int[]> positions = new ArrayDeque<>();

  @Override
  public int[] build(int i, int e) {
    throw new UnsupportedOperationException("build(i,e) is not implemented");
  }

  @Override
  public int[] build(int i, int e, int x) {
    throw new UnsupportedOperationException("build(i,e,x) is not implemented");
  }

  @Override
  public void recycle(int[] position) {
    positions.offer(position);
  }

  public static class ForStandardPositions extends PositionFactory {

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

  public static class ForXPositions extends PositionFactory {

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
