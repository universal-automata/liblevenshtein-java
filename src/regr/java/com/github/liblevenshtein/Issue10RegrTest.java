package com.github.liblevenshtein;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.collection.dictionary.Dawg;
import com.github.liblevenshtein.collection.dictionary.factory.DawgFactory;

/**
 * Regression test for issue #10, "Out-of-dictionary results returned".
 * @see https://github.com/universal-automata/liblevenshtein-java/issues/10
 */
public class Issue10RegrTest {

  @Test
  public void testOverlappingDictionaryTerms() {
    final List<String> terms = Arrays.asList(
      "Representatives",
      "Resource",
      "Resources");

    final DawgFactory factory = new DawgFactory();
    final Dawg dictionary = factory.build(terms, false);

    final Set<String> visited = new HashSet<>();
    final Iterator<String> iter = dictionary.iterator();
    while (iter.hasNext()) {
      final String term = iter.next();
      assertThat(visited).doesNotContain(term);
      assertThat(terms).contains(term);
      visited.add(term);
    }
    assertThat(visited.size()).isEqualTo(terms.size());
  }
}
