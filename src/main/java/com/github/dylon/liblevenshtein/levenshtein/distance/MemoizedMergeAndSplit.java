package com.github.dylon.liblevenshtein.levenshtein.distance;

import lombok.val;

import com.github.dylon.liblevenshtein.collection.SymmetricImmutablePair;

/**
 * Memoizes the distance between two terms, where the distance is calculated
 * using the standard Levenshtein distance extended with merge and split.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class MemoizedMergeAndSplit extends AbstractMemoized {

  /**
   * {@inheritDoc}
   */
  @Override
  public int memoizedDistance(String v, String w) {
    val key = new SymmetricImmutablePair<String>(v,w);

    int distance = memo.getInt(key);
    if (distance != DEFAULT_RETURN_VALUE) {
      return distance;
    }

    if (v.isEmpty()) {
      distance = w.length();
      memo.put(key, distance);
      return distance;
    }

    if (w.isEmpty()) {
      distance = v.length();
      memo.put(key, distance);
      return distance;
    }

    char a = v.charAt(0); String x = v.substring(1);
    char b = w.charAt(0); String y = w.substring(1);

    // Discard identical characters
    while (a == b && x.length() > 0 && y.length() > 0) {
      a = x.charAt(0); v = x; x = v.substring(1);
      b = y.charAt(0); w = y; y = w.substring(1);
    }

    // x.length() == 0 or y.length() == 0
    if (a == b) {
      distance = x.isEmpty() ? y.length() : x.length();
      memo.put(key, distance);
      return distance;
    }

    distance = memoizedDistance(x,w);
    if (0 == distance) {
      memo.put(key, 1);
      return 1;
    }

    int min_distance = distance;

    distance = memoizedDistance(v,y);
    if (0 == distance) {
      memo.put(key, 1);
      return 1;
    }

    if (distance < min_distance) {
      min_distance = distance;
    }

    distance = memoizedDistance(x,y);
    if (0 == distance) {
      memo.put(key, 1);
      return 1;
    }

    if (distance < min_distance) {
      min_distance = distance;
    }

    if (w.length() > 1) {
      distance = memoizedDistance(x, f(w,1));

      if (0 == distance) {
        memo.put(key, 1);
        return 1;
      }

      if (distance < min_distance) {
        min_distance = distance;
      }
    }

    if (v.length() > 1) {
      distance = memoizedDistance(f(v,1), y);

      if (0 == distance) {
        memo.put(key, 1);
        return 1;
      }

      if (distance < min_distance) {
        min_distance = distance;
      }
    }

    distance = 1 + min_distance;
    memo.put(key, distance);
    return distance;
  }
}
