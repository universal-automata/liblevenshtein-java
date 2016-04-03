package com.github.dylon.liblevenshtein.collection.dawg;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

import com.google.common.base.Joiner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.PrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.TransitionFactory;

public class DawgTest {
  private List<String> terms;
  private DawgNodeFactory dawgNodeFactory;
  private PrefixFactory<DawgNode> prefixFactory;
  private TransitionFactory<DawgNode> transitionFactory;
  private DawgFactory dawgFactory;
  private AbstractDawg emptyDawg;
  private AbstractDawg fullDawg;

  @BeforeClass
  public void setUp() throws IOException {
    try (final BufferedReader reader = new BufferedReader(
          new InputStreamReader(
            getClass().getResourceAsStream("/resources/wordsEn.txt"),
            StandardCharsets.UTF_8))) {

      final List<String> terms = new ArrayList<String>();

      String term;
      while ((term = reader.readLine()) != null) {
        terms.add(term);
      }

      Collections.sort(terms);

      this.terms = terms;
      this.dawgNodeFactory = new DawgNodeFactory();
      this.prefixFactory = new PrefixFactory<DawgNode>();
      this.transitionFactory = new TransitionFactory<DawgNode>();
      this.dawgFactory = new DawgFactory(dawgNodeFactory, prefixFactory, transitionFactory);
      this.emptyDawg = dawgFactory.build(new ArrayList<String>(0));
      this.fullDawg = dawgFactory.build(terms);
    }
  }

  @DataProvider(name="terms")
  public Iterator<Object[]> terms() {
    return new TermIterator(terms.iterator());
  }

  @Test(dataProvider="terms")
  public void emptyDawgAcceptsNothing(final String term) {
    assertFalse(emptyDawg.contains(term));
  }

  @Test(dataProvider="terms")
  public void dawgAcceptsAllItsTerms(final String term) {
    assertTrue(fullDawg.contains(term));
  }

  @Test
  public void dawgAcceptsNoTermsItDoesNotContain() {
    assertFalse(fullDawg.contains(""));
    assertFalse(fullDawg.contains("foobar"));
    assertFalse(fullDawg.contains("C+"));
  }

  @Test
  public void dawgSizeIsSameAsTerms() {
    assertEquals(emptyDawg.size(), 0);
    assertEquals(fullDawg.size(), terms.size());
  }

  @Test
  public void dawgAcceptsEmptyStringIfInTerms() {
    final List<String> terms = new ArrayList<>(1);
    terms.add("");
    final AbstractDawg dawg = dawgFactory.build(terms);
    assertTrue(dawg.contains(""));
  }

  @Test
  public void dawgShouldIterateOverAllTerms() {
    final Set<String> terms = new HashSet<>(this.terms);
    for (final String term : fullDawg) {
      try {
        assertTrue(terms.contains(term));
      }
      catch (final AssertionError exception) {
        throw new AssertionError("Expected terms to contain: \"" + term + "\"", exception);
      }
      terms.remove(term);
    }
    if (!terms.isEmpty()) {
      final String message =
        String.format("Expected all terms to be iterated over, but missed [%s]",
          Joiner.on(", ").join(terms));
      throw new AssertionError(message);
    }
  }

  @Test(expectedExceptions=IllegalArgumentException.class)
  public void insertingTermsOutOfOrderShouldThrowAnException() {
    final List<String> terms = new ArrayList<>(3);
    terms.add("a");
    terms.add("c");
    terms.add("b");
    dawgFactory.build(terms, true);
  }

  @Test
  public void equivalentDawgsShouldBeEqual() {
    final AbstractDawg other = dawgFactory.build(terms);
    assertEquals(fullDawg, other);
    assertEquals(fullDawg.hashCode(), other.hashCode());
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
      final Object[] params = this.params;
      this.params = null;
      return params;
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
