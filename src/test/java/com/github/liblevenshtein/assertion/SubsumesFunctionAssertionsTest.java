package com.github.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.transducer.Position;
import com.github.liblevenshtein.transducer.SubsumesFunction;

import static com.github.liblevenshtein.assertion.SubsumesFunctionAssertions.assertThat;

public class SubsumesFunctionAssertionsTest {

  private static final int TERM_LENGTH = 4;

  private final ThreadLocal<SubsumesFunction> standardSubsumes = new ThreadLocal<>();

  private final ThreadLocal<SubsumesFunction> specialSubsumes = new ThreadLocal<>();

  private final ThreadLocal<Position> lhs = new ThreadLocal<>();

  private final ThreadLocal<Position> rhs = new ThreadLocal<>();

  @BeforeMethod
  public void setUp() {
    standardSubsumes.set(mock(SubsumesFunction.class));
    specialSubsumes.set(mock(SubsumesFunction.class));
    lhs.set(mock(Position.class));
    rhs.set(mock(Position.class));
  }

  @Test
  public void testForStandardAlgorithm() {
    when(standardSubsumes.get().at(rhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(true);
    when(standardSubsumes.get().at(lhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(false);
    assertThat(standardSubsumes.get())
      .subsumesAt(rhs.get(), rhs.get(), TERM_LENGTH, true);
    assertThat(standardSubsumes.get())
      .subsumesAt(lhs.get(), rhs.get(), TERM_LENGTH, false);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForStandardAlgorithmAgainstTrueViolation() {
    when(standardSubsumes.get().at(lhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(false);
    assertThat(standardSubsumes.get())
      .subsumesAt(lhs.get(), rhs.get(), TERM_LENGTH, true);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForStandardAlgorithmAgainstFalseViolation() {
    when(standardSubsumes.get().at(lhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(true);
    assertThat(standardSubsumes.get())
      .subsumesAt(lhs.get(), rhs.get(), TERM_LENGTH, false);
  }

  @Test
  public void testForSpecialSubsumes() {
    when(specialSubsumes.get().at(rhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(true);
    when(specialSubsumes.get().at(lhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(false);
    assertThat(specialSubsumes.get())
      .subsumesAt(rhs.get(), rhs.get(), TERM_LENGTH, true);
    assertThat(specialSubsumes.get())
      .subsumesAt(lhs.get(), rhs.get(), TERM_LENGTH, false);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForSpecialSubsumesAgainstTrueViolation() {
    when(specialSubsumes.get().at(lhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(false);
    assertThat(specialSubsumes.get())
      .subsumesAt(lhs.get(), rhs.get(), TERM_LENGTH, true);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForSpecialSubsumesAgainstFalseViolation() {
    when(specialSubsumes.get().at(lhs.get(), rhs.get(), TERM_LENGTH))
      .thenReturn(true);
    assertThat(specialSubsumes.get())
      .subsumesAt(lhs.get(), rhs.get(), TERM_LENGTH, false);
  }
}
