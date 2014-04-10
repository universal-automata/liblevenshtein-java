package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public enum Match {

  /** Matches on whole, dictionary terms */
  TERM,

  /** Matches on prefixes of dictionary terms */
  PREFIX,

  /** Matches on substrings of dictionary terms */
  SUBSTRING,

  /** Matches on suffixes of dictionary terms */
  SUFFIX;
}
