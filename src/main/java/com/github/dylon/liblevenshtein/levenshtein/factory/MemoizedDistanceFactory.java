package com.github.dylon.liblevenshtein.levenshtein.factory;

import lombok.NonNull;

import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.IDistance;
import com.github.dylon.liblevenshtein.levenshtein.IDistanceFactory;
import com.github.dylon.liblevenshtein.levenshtein.distance.MemoizedStandard;
import com.github.dylon.liblevenshtein.levenshtein.distance.MemoizedTransposition;
import com.github.dylon.liblevenshtein.levenshtein.distance.MemoizedMergeAndSplit;

/**
 * Builds memoized instances of Levensthein distance metrics.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class MemoizedDistanceFactory implements IDistanceFactory<String> {
  private IDistance<String> standard = null;
  private IDistance<String> transposition = null;
  private IDistance<String> mergeAndSplit = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public IDistance<String> build(@NonNull final Algorithm algorithm) {
    switch (algorithm) {
      case STANDARD:
        if (null == standard) standard = new MemoizedStandard();
        return standard;
      case TRANSPOSITION:
        if (null == transposition) transposition = new MemoizedTransposition();
        return transposition;
      case MERGE_AND_SPLIT:
        if (null == mergeAndSplit) mergeAndSplit = new MemoizedMergeAndSplit();
        return mergeAndSplit;
      default:
        throw new IllegalArgumentException("Unrecognized algorithm: " + algorithm);
    }
  }
}
