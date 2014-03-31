package com.github.dylon.liblevenshtein.collection.dawg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DawgTest {
  private List<String> terms;
  private DawgNodeFactory dawgNodeFactory;
  private DawgFactory dawgFactory;
  private Dawg emptyDawg;
  private Dawg fullDawg;

  @BeforeClass
  public void setUp() {
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(
          new InputStreamReader(
            getClass().getResourceAsStream(
              "/resources/top-10-most-common-english-words.txt")));
              //"/resources/top-20-most-common-english-words.txt")));

      final List<String> terms = new ArrayList<String>();

      String term;
      while ((term = reader.readLine()) != null) {
        terms.add(term);
      }

      java.util.Collections.sort(terms);

      this.terms = terms;
      this.dawgNodeFactory = new DawgNodeFactory();
      this.dawgFactory = new DawgFactory(dawgNodeFactory);
      this.emptyDawg = dawgFactory.build(new ArrayList<String>(0));
      this.fullDawg = dawgFactory.build(terms);
    }
    catch (final Throwable exception) {
      System.err.println(exception.getMessage());
      exception.printStackTrace();
      System.exit(1);
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
    assertFalse(fullDawg.contains("java"));
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
    final Dawg dawg = dawgFactory.build(terms);
    assertTrue(dawg.contains(""));
  }

  @Test(expectedExceptions=IllegalArgumentException.class)
  public void insertingTermsOutOfOrderShouldThrowAnException() {
    final List<String> terms = new ArrayList<>(1);
    terms.add("a");
    terms.add("c");
    terms.add("b");
    dawgFactory.build(terms);
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
