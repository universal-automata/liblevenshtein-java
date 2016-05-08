package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.Setter;

/**
 * Defines methods to remove positions from a Levenshtein state that are
 * subsumed by other positions in that state.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public abstract class UnsubsumeFunction implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Determines whether one position subsumes another.
   * -- SETTER --
   * Determines whether one position subsumes another.
   * @param subsumes Whether one position subsumes another.
   * @return This {@link UnsubsumeFunction} for fluency.
   */
  @Setter
  protected SubsumesFunction subsumes;

  /**
   * Removes all the positions from {@code state} that are subsumed by other
   * position.
   * @param state State whose positions should be un-subsumed.
   * @param queryLength Length of the query term.
   */
  public abstract void at(State state, int queryLength);

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
    public void at(final State state, final int queryLength) {
      final StateIterator outerIter = state.iterator();
      while (outerIter.hasNext()) {
        final Position outer = outerIter.next();
        final int outerErrors = outer.numErrors();

        final StateIterator innerIter = outerIter.copy();

        while (innerIter.hasNext()) {
          final Position inner = innerIter.peek();
          if (outerErrors < inner.numErrors()) {
            break;
          }
          innerIter.next();
        }

        while (innerIter.hasNext()) {
          final Position inner = innerIter.next();
          if (subsumes.at(outer, inner, queryLength)) {
            innerIter.remove();
          }
        }
      }
    }
  }

  /**
   * Removes subsumed positions for transposition and merge-and-split,
   * Levenshtein states.
   * @author Dylon Edwards
   * @since 3.0.0
   */
  public static class ForSpecialPositions extends UnsubsumeFunction {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void at(final State state, final int queryLength) {
      final StateIterator outerIter = state.iterator();
      while (outerIter.hasNext()) {
        final Position outer = outerIter.next();
        final int outerErrors = outer.numErrors();

        final StateIterator innerIter = outerIter.copy();

        while (innerIter.hasNext()) {
          final Position inner = innerIter.peek();
          if (outerErrors < inner.numErrors()) {
            break;
          }
          innerIter.next();
        }

        while (innerIter.hasNext()) {
          final Position inner = innerIter.next();
          if (subsumes.at(outer, inner, queryLength)) {
            innerIter.remove();
          }
        }
      }
    }
  }
}
