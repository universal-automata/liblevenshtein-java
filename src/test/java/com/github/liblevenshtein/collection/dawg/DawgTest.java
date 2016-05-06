package com.github.liblevenshtein.collection.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.common.base.Joiner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.liblevenshtein.collection.dictionary.factory.DawgFactory;

import static com.github.liblevenshtein.assertion.SetAssertions.assertThat;

@Slf4j
public class DawgTest {
  private List<String> terms;
  private DawgFactory dawgFactory;
  private Dawg emptyDawg;
  private Dawg fullDawg;

  @BeforeClass
  public void setUp() throws IOException {
    try (final BufferedReader reader = new BufferedReader(
          new InputStreamReader(
            getClass().getResourceAsStream("/wordsEn.txt"),
            StandardCharsets.UTF_8))) {

      final List<String> termsList = new ArrayList<>();

      String term;
      while ((term = reader.readLine()) != null) {
        termsList.add(term);
      }

      Collections.sort(termsList);

      this.terms = termsList;
      this.dawgFactory = new DawgFactory();
      this.emptyDawg = dawgFactory.build(new ArrayList<>(0));
      this.fullDawg = dawgFactory.build(termsList);
    }
  }

  @DataProvider(name = "terms")
  public Iterator<Object[]> terms() {
    return new TermIterator(terms.iterator());
  }

  @Test(dataProvider = "terms")
  public void emptyDawgAcceptsNothing(final String term) {
    assertThat(emptyDawg).doesNotContain(term);
  }

  @Test(dataProvider = "terms")
  public void dawgAcceptsAllItsTerms(final String term) {
    assertThat(fullDawg).contains(term);
  }

  @Test
  public void dawgAcceptsNoTermsItDoesNotContain() {
    assertThat(fullDawg).doesNotContain("", "foobar", "C+");
  }

  @Test
  public void dawgSizeIsSameAsTerms() {
    assertThat(emptyDawg)
      .isEmpty()
      .hasSize(0);
    assertThat(fullDawg)
      .isNotEmpty()
      .hasSize(terms.size());
  }

  @Test
  public void dawgAcceptsEmptyStringIfInTerms() {
    final List<String> termsList = new ArrayList<>(1);
    termsList.add("");
    final Dawg dawg = dawgFactory.build(termsList);
    assertThat(dawg).contains("");
  }

  @Test
  public void dawgShouldIterateOverAllTerms() {
    final Set<String> termsList = new HashSet<>(this.terms);
    for (final String term : fullDawg) {
      try {
        assertThat(termsList).contains(term);
      }
      catch (final AssertionError exception) {
        throw new AssertionError("Expected terms to contain: \"" + term + "\"", exception);
      }
      termsList.remove(term);
    }
    if (!termsList.isEmpty()) {
      final String message =
        String.format("Expected all terms to be iterated over, but missed [%s]",
          Joiner.on(", ").join(termsList));
      throw new AssertionError(message);
    }
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void insertingTermsOutOfOrderShouldThrowAnException() {
    final List<String> termsList = new ArrayList<>(3);
    termsList.add("a");
    termsList.add("c");
    termsList.add("b");
    dawgFactory.build(termsList, true);
  }

  @Test
  public void equivalentDawgsShouldBeEqual() {
    final Dawg other = dawgFactory.build(new ArrayList<>(terms));
    assertThat(fullDawg).isEqualTo(other);
  }

  @RequiredArgsConstructor
  private static class TermIterator implements Iterator<Object[]> {
    private final Iterator<String> terms;
    private Object[] params = null;
    private Object[] buffer = new Object[1];

    @Override
    public boolean hasNext() {
      advance();
      return null != params;
    }

    @Override
    public Object[] next() {
      advance();
      final Object[] paramsLocal = this.params;
      this.params = null;
      return paramsLocal;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    public void advance() {
      if (null == params && terms.hasNext()) {
        buffer[0] = terms.next();
        params = buffer;
      }
    }
  }
}
