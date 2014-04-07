package com.github.dylon.liblevenshtein.levenshtein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertEquals;

public class DistanceAndTermComparatorTest {
  private List<Candidate> candidates;
  private Random random;
  private String term;
  private String[] rankings;

  @DataProvider(name="comparators")
  public Object[][] comparators() {
    return new Object[][] {
      {new CaseSensitiveDistanceAndTermComparator().term(term)},
      {new CaseInsensitiveDistanceAndTermComparator().term(term)}
    };
  }

  @BeforeClass
  public void setUpClass() {
    this.candidates = new ArrayList<Candidate>(7);
    this.random = new Random(8513);
    this.term = "c++";

    this.rankings = new String[] {
      "C++",
      "A++",
      "J++",
      "R++",
      "X++",
      "A+",
      "csh"
    };

    final Candidate[] candidates = {
      candidate("A++", 1),
      candidate("C++", 1),
      candidate("J++", 1),
      candidate("R++", 1),
      candidate("X++", 1),
      candidate("A+", 2),
      candidate("csh", 2)
    };

    for (final Candidate candidate : candidates) {
      this.candidates.add(candidate);
    }
  }

  @BeforeMethod
  public void setUpTest() {
    Collections.shuffle(candidates, random);
  }

  @Test(dataProvider="comparators")
  public void validateTermRanking(final Comparator<Candidate> comparator) {
    Collections.sort(candidates, comparator);
    for (int i = 0; i < rankings.length; ++i) {
      assertEquals(candidates.get(i).term(), rankings[i]);
    }
  }

  private Candidate candidate(final String term, final int distance) {
    final Candidate candidate = new Candidate();
    candidate.term(term);
    candidate.distance(distance);
    return candidate;
  }
}
