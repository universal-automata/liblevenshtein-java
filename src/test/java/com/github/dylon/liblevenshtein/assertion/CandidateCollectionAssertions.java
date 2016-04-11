package com.github.dylon.liblevenshtein.assertion;

import org.assertj.core.api.AbstractAssert;

import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;

/**
 * AssertJ-style assertions for {@link CandidateCollection}.
 */
public class CandidateCollectionAssertions<Type>
    extends AbstractAssert<CandidateCollectionAssertions<Type>, CandidateCollection<Type>> {

  /**
   * Constructs a new {@link CandidateCollectionAssertions} to assert-against.
   * @param actual {@link CandidateCollection} to assert-against.
   */
  public CandidateCollectionAssertions(final CandidateCollection<Type> actual) {
    super(actual, CandidateCollectionAssertions.class);
  }

  /**
   * Builds a new {@link CandidateCollectionAssertions} to assert-against.
   * @param actual {@link CandidateCollection} to assert-against.
   * @return A new {@link CandidateCollectionAssertions} to assert-against.
   */
  public static <Type> CandidateCollectionAssertions<Type> assertThat(
      final CandidateCollection<Type> actual) {
    return new CandidateCollectionAssertions<>(actual);
  }

  /**
   * Asserts that the collection can be offered another element.
   * @param value First parameter of the element to offer.
   * @param distance Second parameter of the element to offer.
   * @return This {@link CandidateCollectionAssertions} for fluency.
   * @throws AssertionError When the collection cannot be offered another
   * element.
   */
  public CandidateCollectionAssertions<Type> offers(
      final String value,
      final int distance) {

    isNotNull();

    if (!actual.offer(value, distance)) {
      failWithMessage("Expected CandidateCollection#offer(%s, %d) to be [true]",
        value, distance);
    }

    return this;
  }

  /**
   * Asserts that the collection cannot be offered another element.
   * @param value First parameter of the element to offer.
   * @param distance Second parameter of the element to offer.
   * @return This {@link CandidateCollectionAssertions} for fluency.
   * @throws AssertionError When the collection can be offered another element.
   */
  public CandidateCollectionAssertions<Type> doesNotOffer(
      final String value,
      final int distance) {

    isNotNull();

    if (actual.offer(value, distance)) {
      failWithMessage("Did not expect CandidateCollection#offer(%s, %d) to be [true]",
        value, distance);
    }

    return this;
  }

  /**
   * Returns a new {@link IteratorAssertions} to assert-against the collections
   * iterator.
   * @return A new {@link IteratorAssertions} to assert-against the collections
   * iterator.
   */
  public IteratorAssertions<Type> iterator() {
    isNotNull();
    return new IteratorAssertions<>(actual.iterator());
  }
}
