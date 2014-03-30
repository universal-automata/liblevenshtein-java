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

  /**
   * {@inheritDoc}
   */
  @Override
  public IDistance<String> build(@NonNull final Algorithm algorithm) {
    switch (algorithm) {
      case STANDARD:
        return new MemoizedStandard();
      case TRANSPOSITION:
        return new MemoizedTransposition();
      case MERGE_AND_SPLIT:
        return new MemoizedMergeAndSplit();
      default:
        throw new IllegalArgumentException("Unrecognized algorithm: " + algorithm);
    }
  }
}
