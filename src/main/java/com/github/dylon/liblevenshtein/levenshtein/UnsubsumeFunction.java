package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;

import lombok.Setter;

import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionFactory;

/**
 * Defines methods to remove positions from a Levenshtein state that are
 * subsumed by other positions in that state.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class UnsubsumeFunction implements IUnsubsumeFunction, Serializable {

	private static final long serialVersionUID = 1L;

  /**
   * Determines whether one position subsumes another.
   * -- SETTER --
   * Determines whether one position subsumes another.
   * @param subsumes Whether one position subsumes another.
   * @return This {@link UnsubsumeFunction} for fluency.
   */
  @Setter
  protected ISubsumesFunction subsumes;

  /**
   * Builds and recycles position vectors.
   * -- SETTER --
   * Builds and recycles position vectors.
   * @param positionFactory Builds and recycles position vectors.
   * @return This {@link UnsubsumeFunction} for fluency.
   */
  @Setter
  protected IPositionFactory positionFactory;

  /**
   * Removes subsumed positions for standard, Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForStandardPositions extends UnsubsumeFunction {

	  private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void at(final IState state) {
      for (int m = 0; m < state.size(); ++m) {
        final int[] outer = state.getOuter(m);
        final int i = outer[0];
        final int e = outer[1];

        int n = m + 1;
        while (n < state.size()) {
          final int[] inner = state.getInner(n);
          final int f = inner[1];
          if (e < f) {
            break;
          }
          n += 1;
        }

        while (n < state.size()) {
          final int[] inner = state.getInner(n);
          final int j = inner[0];
          final int f = inner[1];

          if (subsumes.at(i,e, j,f)) {
            state.removeInner();
          }
          else {
            n += 1;
          }
        }
      }
    }
  }

  /**
   * Removes subsumed positions for transposition and merge-and-split,
   * Levenshtein states.
   * @author Dylon Edwards
   * @since 2.1.0
   */
  public static class ForXPositions extends UnsubsumeFunction {

	  private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void at(final IState state) {
      for (int m = 0; m < state.size(); ++m) {
        final int[] outer = state.getOuter(m);
        final int i = outer[0];
        final int e = outer[1];
        final int s = outer[2];

        int n = m + 1;
        while (n < state.size()) {
          final int[] inner = state.getInner(n);
          final int f = inner[1];
          if (e < f) {
            break;
          }
          n += 1;
        }

        while (n < state.size()) {
          final int[] inner = state.getInner(n);
          final int j = inner[0];
          final int f = inner[1];
          final int t = inner[2];

          if (subsumes.at(i,e,s, j,f,t, n)) {
            state.removeInner();
          }
          else {
            n += 1;
          }
        }
      }
    }
  }
}
