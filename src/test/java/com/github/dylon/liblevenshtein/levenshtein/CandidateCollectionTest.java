package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Iterator;

import lombok.val;

import org.testng.annotations.Test;

import static com.github.dylon.liblevenshtein.assertion.CandidateCollectionAssertions.assertThat;
import static com.github.dylon.liblevenshtein.assertion.IteratorAssertions.assertThat;

public class CandidateCollectionTest {

  @Test
  public void testWithoutDistance() {
    val candidates = new CandidateCollection.WithoutDistance(3);
    enqueueCandidates(candidates);

    assertThat(candidates).iterator()
      .hasNext("foo")
      .hasNext("bar")
      .hasNext("baz")
      .doesNotHaveNext();
  }

  @Test
  public void testWithDistance() {
    val candidates = new CandidateCollection.WithDistance(3);
    enqueueCandidates(candidates);

    assertThat(candidates).iterator()
      .hasNext(new Candidate("foo", 1))
      .hasNext(new Candidate("bar", 2))
      .hasNext(new Candidate("baz", 3))
      .doesNotHaveNext();
  }

  private void enqueueCandidates(final CandidateCollection<?> candidates) {
    assertThat(candidates)
      .offers("foo", 1)
      .offers("bar", 2)
      .offers("baz", 3)
      .doesNotOffer("qux", 4);
  }
}
