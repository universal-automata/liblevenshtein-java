package com.github.dylon.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.SubsumesFunction;
import static com.github.dylon.liblevenshtein.assertion.SubsumesFunctionAssertions.assertThat;

public class SubsumesFunctionAssertionsTest {

  private final ThreadLocal<SubsumesFunction> standardSubsumes = new ThreadLocal<>();
  private final ThreadLocal<SubsumesFunction> specialSubsumes = new ThreadLocal<>();

  @BeforeMethod
  public void setUp() {
    standardSubsumes.set(mock(SubsumesFunction.class));
    specialSubsumes.set(mock(SubsumesFunction.class));
  }

  @Test
  public void testForStandardAlgorithm() {
    when(standardSubsumes.get().at(0, 0, 0, 0)).thenReturn(true);
    when(standardSubsumes.get().at(1, 0, 0, 0)).thenReturn(false);
    assertThat(standardSubsumes.get()).subsumesAt(0, 0, 0, 0, true);
    assertThat(standardSubsumes.get()).subsumesAt(1, 0, 0, 0, false);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForStandardAlgorithmAgainstTrueViolation() {
    when(standardSubsumes.get().at(0, 0, 0, 0)).thenReturn(false);
    assertThat(standardSubsumes.get()).subsumesAt(0, 0, 0, 0, true);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForStandardAlgorithmAgainstFalseViolation() {
    when(standardSubsumes.get().at(1, 0, 0, 0)).thenReturn(true);
    assertThat(standardSubsumes.get()).subsumesAt(1, 0, 0, 0, false);
  }

  @Test
  public void testForSpecialSubsumes() {
    when(specialSubsumes.get().at(0, 0, 0, 0, 0, 0, 0)).thenReturn(true);
    when(specialSubsumes.get().at(1, 0, 0, 0, 0, 0, 0)).thenReturn(false);
    assertThat(specialSubsumes.get()).subsumesAt(0, 0, 0, 0, 0, 0, 0, true);
    assertThat(specialSubsumes.get()).subsumesAt(1, 0, 0, 0, 0, 0, 0, false);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForSpecialSubsumesAgainstTrueViolation() {
    when(specialSubsumes.get().at(0, 0, 0, 0, 0, 0, 0)).thenReturn(false);
    assertThat(specialSubsumes.get()).subsumesAt(0, 0, 0, 0, 0, 0, 0, true);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForSpecialSubsumesAgainstFalseViolation() {
    when(specialSubsumes.get().at(1, 0, 0, 0, 0, 0, 0)).thenReturn(true);
    assertThat(specialSubsumes.get()).subsumesAt(1, 0, 0, 0, 0, 0, 0, false);
  }
}
