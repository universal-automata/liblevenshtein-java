package com.github.liblevenshtein.transducer.factory;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;

import com.github.liblevenshtein.transducer.Candidate;

public class CandidateFactoryTest {

  @Test
  public void testWithDistance() {
    val candidateFactory = new CandidateFactory.WithDistance();
    final Candidate candidate = candidateFactory.build("foo", 2);
    assertThat(candidate).isEqualTo(new Candidate("foo", 2));
  }

  @Test
  public void testWithoutDistance() {
    val candidateFactory = new CandidateFactory.WithoutDistance();
    final String candidate = candidateFactory.build("foo", 2);
    assertThat(candidate).isEqualTo("foo");
  }
}
