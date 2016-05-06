package com.github.liblevenshtein.assertion;

import java.util.Arrays;
import java.util.Iterator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.transducer.CandidateCollection;
import static com.github.liblevenshtein.assertion.CandidateCollectionAssertions.assertThat;

public class CandidateCollectionAssertionsTest {

  private final ThreadLocal<CandidateCollection<String>> candidates = new ThreadLocal<>();

  @BeforeMethod
  @SuppressWarnings("unchecked")
  public void setUp() {
    candidates.set(mock(CandidateCollection.class));
  }

  @Test
  public void testOperations() {
    when(candidates.get().offer("foo", 1)).thenReturn(true);
    when(candidates.get().offer("bar", 2)).thenReturn(true);
    when(candidates.get().offer("baz", 3)).thenReturn(true);
    when(candidates.get().offer("qux", 4)).thenReturn(false);
    when(candidates.get().iterator()).thenReturn(iter("foo", "bar", "baz"));
    assertThat(candidates.get())
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
