package com.github.liblevenshtein.distance.factory;

import java.io.Serializable;

import lombok.NonNull;

import com.github.liblevenshtein.distance.IDistance;
import com.github.liblevenshtein.distance.MemoizedMergeAndSplit;
import com.github.liblevenshtein.distance.MemoizedStandard;
import com.github.liblevenshtein.distance.MemoizedTransposition;
import com.github.liblevenshtein.transducer.Algorithm;

/**
 * Builds memoized instances of Levenshtein distance metrics.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class MemoizedDistanceFactory implements IDistanceFactory<String>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Computes the distance between two terms using the standard, Levenshtein
   * distance algorithm.
   */
  private volatile IDistance<String> standard = null;

  /**
   * Computes the distance between two terms using the standard, Levenshtein
   * distance algorithm extended with transpositions.
   */
  private volatile IDistance<String> transposition = null;

  /**
   * Computes the distance between two terms using the standard, Levenshtein
   * distance algorithm extended with merges and splits.
   */
  private volatile IDistance<String> mergeAndSplit = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public IDistance<String> build(@NonNull final Algorithm algorithm) {
    switch (algorithm) {
      case STANDARD:
        if (null == standard) {
          synchronized (this) {
            if (null == standard) {
              standard = new MemoizedStandard();
            }
          }
        }
        return standard;
      case TRANSPOSITION:
        if (null == transposition) {
          synchronized (this) {
            if (null == transposition) {
              transposition = new MemoizedTransposition();
            }
          }
        }
        return transposition;
      case MERGE_AND_SPLIT:
        if (null == mergeAndSplit) {
          synchronized (this) {
            if (null == mergeAndSplit) {
              mergeAndSplit = new MemoizedMergeAndSplit();
            }
          }
        }
        return mergeAndSplit;
      default:
        throw new IllegalArgumentException("Unrecognized algorithm: " + algorithm);
    }
  }
}
