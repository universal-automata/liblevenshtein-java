package com.github.liblevenshtein;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;

/**
 * Regression test for issue #2, "Standard algorithm seems to be off".
 * @see https://github.com/universal-automata/liblevenshtein-java/issues/2
 */
public class Issue2RegrTest {

  @Test
  @SuppressWarnings("checkstyle:multiplestringliterals")
  public void testStandardDistances() {
    final List<String> terms = Arrays.asList("foo", "bar", "baz");

    final ITransducer<Candidate> transducer = new TransducerBuilder()
      .defaultMaxDistance(Integer.MAX_VALUE)
      .algorithm(Algorithm.STANDARD)
      .includeDistance(true)
      .dictionary(terms)
      .build();

    final Iterable<Candidate> candidates = transducer.transduce("foo");
    assertThat(candidates).contains(new Candidate("foo", 0));
  }
}
