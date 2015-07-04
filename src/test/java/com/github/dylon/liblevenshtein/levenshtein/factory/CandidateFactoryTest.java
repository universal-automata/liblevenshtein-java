package com.github.dylon.liblevenshtein.levenshtein.factory;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import lombok.val;

import com.github.dylon.liblevenshtein.levenshtein.Candidate;

public class CandidateFactoryTest {

  @Test
  public void testWithDistance() {
    val candidateFactory = new CandidateFactory.WithDistance();
    final Candidate candidate = candidateFactory.build("foo", 2);
    assertEquals(candidate, new Candidate("foo", 2));
  }

  @Test
  public void testWithoutDistance() {
    val candidateFactory = new CandidateFactory.WithoutDistance();
    final String candidate = candidateFactory.build("foo", 2);
    assertEquals(candidate, "foo");
  }
}
