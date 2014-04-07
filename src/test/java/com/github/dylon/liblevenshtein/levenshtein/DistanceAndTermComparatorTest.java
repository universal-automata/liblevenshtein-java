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
  private List<Intersection> intersections;
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
    this.intersections = new ArrayList<Intersection>(7);
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

    final Intersection[] intersections = {
      intersection("A++", 1),
      intersection("C++", 1),
      intersection("J++", 1),
      intersection("R++", 1),
      intersection("X++", 1),
      intersection("A+", 2),
      intersection("csh", 2)
    };

    for (final Intersection intersection : intersections) {
      this.intersections.add(intersection);
    }
  }

  @BeforeMethod
  public void setUpTest() {
    Collections.shuffle(intersections, random);
  }

  @Test(dataProvider="comparators")
  public void validateTermRanking(final Comparator<Intersection> comparator) {
    Collections.sort(intersections, comparator);
    for (int i = 0; i < rankings.length; ++i) {
      assertEquals(intersections.get(i).candidate(), rankings[i]);
    }
  }

  private Intersection intersection(final String candidate, final int distance) {
    final Intersection intersection = new Intersection();
    intersection.candidate(candidate);
    intersection.distance(distance);
    return intersection;
  }
}
