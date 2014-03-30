package com.github.dylon.liblevenshtein.levenshtein;

/**
 * Enumerates the variants of Levenshtein distance supported by this library.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public enum Algorithm {

  /**
   * This is the standard, textbook version of Levenshtein distance. It includes
   * support for the elementary operations of insertion, deletion and
   * substitution.
   */
  STANDARD,

  /**
   * Somethimes known as Damerau–Levenshtein distance, this includes the
   * elementary operations described by the standard algorithm, as well as the
   * elementary operation, transposition, which would incur a penalty of two
   * units of error in the standard algorithm (whether it be an insertion and
   * deletion, a deletion and insertion, or two substitutions). This algorithm
   * is particularly useful in typed spelling-correction, where most spelling
   * errors occur when a user transposes two characters.
   */
  TRANSPOSITION,

  /**
   * This algorithm includes the elementary operations described by the basic
   * algorithm, as well as the elementary operations, merge and split.  A merge
   * occurs when two characters are joined together, and a split occurs when a
   * single character is exploded into two characters. This algorithm is
   * particularly useful in OCR applications, where one may read a "cl" when one
   * should have read a "d" (i.e. merge), or when one may have read a "d" when
   * one should have read a "cl" (i.e. split).
   */
  MERGE_AND_SPLIT;
}
