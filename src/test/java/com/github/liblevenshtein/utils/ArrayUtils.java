package com.github.liblevenshtein.utils;

/**
 * Various utilities regarding Java arrays.
 */
public class ArrayUtils {

  /**
   * Constructs a default {@link ArrayUtils}.
   * @throws UnsupportedOperationException Because Checkstyle prohibits pure,
   * utility classes from being instantiated.
   */
  protected ArrayUtils() {
    throw new UnsupportedOperationException(
      "Checkstyle prohibits pure, utility classes from being instantiated.");
  }

  /**
   * Returns a new, int array from a varargs array.
   * @param is Varargs array to convert to a standard, int array.
   * @return A new, int array from a varargs array.
   */
  public static int[] arr(final int... is) {
    return is;
  }
}
