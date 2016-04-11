package com.github.dylon.liblevenshtein.assertion;

import java.util.Set;

import org.assertj.core.api.AbstractAssert;

/**
 * AssertJ-quality assertions for Set objects. AssertJ's provided assertions do
 * not include an efficient implementation for Sets; instead, they are treated
 * as Iterables and are iterated-over for determining element containment, etc.
 * This behavior kills the performance of my tests against large dictionaries,
 * so I wrote this custom class of assertions.
 * @param <Type> Generic type of the set that is asserted-against.
 */
public class SetAssertions<Type>
    extends AbstractAssert<SetAssertions<Type>, Set<Type>> {

  /**
   * Constructs a new {@link SetAssertions} for asserting-against {@link #actual}.
   * @param actual Set to assert-against.
   */
  public SetAssertions(final Set<Type> actual) {
    super(actual, SetAssertions.class);
  }

  /**
   * Constructs a new {@link SetAssertions} for asserting-against {@link #actual}.
   * @param actual Set to assert-against.
   * @param <Type> Generic type of the set that is asserted-against.
   * @return New instance of {@link SetAssertions} to assert-against {@link #param}.
   */
  public static <Type> SetAssertions<Type> assertThat(final Set<Type> actual) {
    return new SetAssertions<Type>(actual);
  }

  /**
   * Asserts that {@link #actual} contains each of the expected {@link #values}.
   * @param values Expected elements of the {@link #actual} set.
   * @return This {@link SetAssertions} for fluency.
   * @throws AssertionError When the {@link #actual} set is null or does not
   * contain any of the {@link #values}.
   */
  public SetAssertions<Type> contains(final Object... values) {
    isNotNull();

    for (final Object value : values) {
      if (!actual.contains(value)) {
        failWithMessage("Expected value [%s] to be in the set", value);
      }
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} does not contain any of the {@link #values}.
   * @param values Elements expected not to be in the {@link #actual} set.
   * @return This {@link SetAssertions} for fluency.
   * @throws AssertionError When the {@link #actual} set is null or contains any
   * of the {@link #values}.
   */
  public SetAssertions<Type> doesNotContain(final Object... values) {
    isNotNull();

    for (final Object value : values) {
      if (actual.contains(value)) {
        failWithMessage("Did not expect value [%s] to be in the set", value);
      }
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} has the expected size.
   * @param size Expected size of the {@link #actual} set.
   * @return This {@link SetAssertions} for fluency.
   * @throws AssertionError When the {@link #actual} set is null or does not
   * have the expected size.
   */
  public SetAssertions<Type> hasSize(final int size) {
    isNotNull();

    if (size != actual.size()) {
      failWithMessage("Expected size of the set to be [%d], but was [%d]",
        size, actual.size());
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} is empty.
   * @return This {@link SetAssertions} for fluency.
   * @throws AssertionError When the {@link #actual} set is null, its size is
   * not zero, or {@link Set#isEmpty()} returns false.
   */
  public SetAssertions<Type> isEmpty() {
    isNotNull();

    if (0 != actual.size()) {
      failWithMessage("Expected size of set to be [0], but was [%d]",
        actual.size());
    }

    if (!actual.isEmpty()) {
      failWithMessage("Expected set.isEmpty() to be [true]");
    }

    return this;
  }

  /**
   * Asserts that {@link #actual} is not empty.
   * @return This {@link SetAssertions} for fluency.
   * @throws AssertionError When the {@link #actual} set is null, its size is
   * non-positive, or {@link Set#isEmpty()} returns true.
   */
  public SetAssertions<Type> isNotEmpty() {
    isNotNull();

    if (actual.size() < 1) {
      failWithMessage("Expected size of set to be positive, but was [%d]",
        actual.size());
    }

    if (actual.isEmpty()) {
      failWithMessage("Expected set.isEmpty() to be [false]");
    }

    return this;
  }

  /**
   * Verifies that the actual set is equivalent to the expected set, by
   * checking {@link Set#equals(Object)} and {@link Set#hashCode()}.
   * @param expected Equivalent set of the actual one.
   * @return This {@link SetAssertions} for fluency.
   * @throws AssertionError When the {@link #actual} set is null,
   * {@link Set#equals(Object)} return false, or the values of
   * {@link Set#hashCode()} differ.
   */
  public SetAssertions<Type> isEqualTo(final Set<Type> expected) {
    isNotNull();

    if (!actual.equals(expected)) {
      failWithMessage("Expected sets to be equivalent: [%s] != [%s]",
        expected, actual);
    }

    if (actual.hashCode() != expected.hashCode()) {
      failWithMessage("Expected hashCode to be [%d], but was [%d]",
        actual.hashCode(), expected.hashCode());
    }

    return this;
  }
}
