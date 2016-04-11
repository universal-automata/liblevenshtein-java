package com.github.dylon.liblevenshtein.assertion;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.dylon.liblevenshtein.levenshtein.SubsumesFunction;
import static com.github.dylon.liblevenshtein.assertion.SubsumesFunctionAssertions.assertThat;

public class SubsumesFunctionAssertionsTest {
	private SubsumesFunction standardSubsumes = null;
	private SubsumesFunction specialSubsumes = null;

	@BeforeMethod
	public void setUp() {
		this.standardSubsumes = mock(SubsumesFunction.class);
		this.specialSubsumes = mock(SubsumesFunction.class);
	}

	@Test
  public void testForStandardAlgorithm() {
  	when(standardSubsumes.at(0,0, 0,0)).thenReturn(true);
  	when(standardSubsumes.at(1,0, 0,0)).thenReturn(false);
    assertThat(standardSubsumes).subsumesAt(0,0, 0,0, true);
    assertThat(standardSubsumes).subsumesAt(1,0, 0,0, false);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForStandardAlgorithmAgainstTrueViolation() {
  	when(standardSubsumes.at(0,0, 0,0)).thenReturn(false);
    assertThat(standardSubsumes).subsumesAt(0,0, 0,0, true);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForStandardAlgorithmAgainstFalseViolation() {
  	when(standardSubsumes.at(1,0, 0,0)).thenReturn(true);
    assertThat(standardSubsumes).subsumesAt(1,0, 0,0, false);
  }

  @Test
  public void testForSpecialSubsumes() {
  	when(specialSubsumes.at(0,0,0, 0,0,0, 0)).thenReturn(true);
  	when(specialSubsumes.at(1,0,0, 0,0,0, 0)).thenReturn(false);
    assertThat(specialSubsumes).subsumesAt(0,0,0, 0,0,0, 0, true);
    assertThat(specialSubsumes).subsumesAt(1,0,0, 0,0,0, 0, false);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForSpecialSubsumesAgainstTrueViolation() {
  	when(specialSubsumes.at(0,0,0, 0,0,0, 0)).thenReturn(false);
    assertThat(specialSubsumes).subsumesAt(0,0,0, 0,0,0, 0, true);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testForSpecialSubsumesAgainstFalseViolation() {
  	when(specialSubsumes.at(1,0,0, 0,0,0, 0)).thenReturn(true);
    assertThat(specialSubsumes).subsumesAt(1,0,0, 0,0,0, 0, false);
  }
}
