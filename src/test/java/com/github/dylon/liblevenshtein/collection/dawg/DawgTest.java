package com.github.dylon.liblevenshtein.collection.dawg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.github.dylon.liblevenshtein.collection.PrefixFactory;

public class DawgTest {
  private List<String> terms;
  private DawgNodeFactory dawgNodeFactory;
  private PrefixFactory<DawgNode> prefixFactory;
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
              "/resources/top-20-most-common-english-words.txt")));

      final List<String> terms = new ArrayList<String>();

      String term;
      while ((term = reader.readLine()) != null) {
        terms.add(term);
      }

      Collections.sort(terms);

      this.terms = terms;
      this.dawgNodeFactory = new DawgNodeFactory();
      this.prefixFactory = new PrefixFactory<DawgNode>();
      this.dawgFactory = new DawgFactory(dawgNodeFactory, prefixFactory);
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

  @Test
  public void dawgShouldIterateOverAllTerms() {
    final Set<String> terms = new HashSet<>(this.terms);
    for (final String term : fullDawg) {
      try {
        assertTrue(terms.contains(term));
      }
      catch (final AssertionError exception) {
        System.err.println("Expected terms to contain: \"" + term + "\"");
        throw exception;
      }
      terms.remove(term);
    }
    assertTrue(terms.isEmpty());
  }

  @Test(expectedExceptions=IllegalArgumentException.class)
  public void insertingTermsOutOfOrderShouldThrowAnException() {
    final List<String> terms = new ArrayList<>(3);
    terms.add("a");
    terms.add("c");
    terms.add("b");
    dawgFactory.build(terms);
  }

  @Test
  public void equivalentDawgsShouldBeEqual() {
    final Dawg other = dawgFactory.build(terms);
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
