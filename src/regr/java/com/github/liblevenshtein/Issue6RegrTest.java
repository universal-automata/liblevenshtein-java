package com.github.liblevenshtein;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;

/**
 * Regression test for issue #6, "Dictionary phrases sometimes appear to be
 * dropped".
 * @see https://github.com/universal-automata/liblevenshtein-java/issues/6
 */
public class Issue6RegrTest {

  @Test
  public void testOverlappingDictionaryTerms() {
    final List<String> terms = Arrays.asList(
      "Representatives",
      "Resource",
      "Resources");

    final ITransducer<String> transducer = new TransducerBuilder()
      .algorithm(Algorithm.TRANSPOSITION)
      .defaultMaxDistance(2)
      .includeDistance(false)
      .dictionary(terms)
      .build();

    for (final String term : terms) {
      final Iterable<String> candidates = transducer.transduce(term);
      assertThat(candidates).contains(term);
    }
  }
}
