package com.github.dylon.liblevenshtein.assertion;

import java.util.Arrays;
import java.util.Iterator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
import static com.github.dylon.liblevenshtein.assertion.CandidateCollectionAssertions.assertThat;

public class CandidateCollectionAssertionsTest {

  private CandidateCollection<String> candidates = null;

  @BeforeMethod
  @SuppressWarnings("unchecked")
  public void setUp() {
    this.candidates = mock(CandidateCollection.class);
  }

  @Test
  public void testOperations() {
    when(candidates.offer("foo", 1)).thenReturn(true);
    when(candidates.offer("bar", 2)).thenReturn(true);
    when(candidates.offer("baz", 3)).thenReturn(true);
    when(candidates.offer("qux", 4)).thenReturn(false);
    when(candidates.iterator()).thenReturn(iter("foo", "bar", "baz"));
    assertThat(candidates)
      .offers("foo", 1)
      .offers("bar", 2)
      .offers("baz", 3)
      .doesNotOffer("qux", 4)
      .iterator()
        .isEqualTo(iter("foo", "bar", "baz"));
  }

  private Iterator<String> iter(final String... values) {
    return Arrays.asList(values).iterator();
  }
}
