package com.github.liblevenshtein.assertion;

import java.util.Arrays;
import java.util.Iterator;

import org.testng.annotations.Test;

import static com.github.liblevenshtein.assertion.IteratorAssertions.assertThat;

public class IteratorAssertionsTest {

  private static final String FOO = "foo";

  private static final String BAR = "bar";

  private static final String BAZ = "baz";

  private static final String QUX = "qux";

  @Test
  public void testOperations() {
    assertThat(iter(FOO, BAR, BAZ))
      .hasNext(FOO)
      .hasNext(BAR)
      .hasNext(BAZ)
      .doesNotHaveNext();

    assertThat(iter(FOO, BAR, BAZ))
      .isEqualTo(iter(FOO, BAR, BAZ));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasNextAgainstViolation() {
    assertThat(iter()).hasNext();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testHasNextValueAgainstViolation() {
    assertThat(iter(FOO)).hasNext(BAR);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testDoesNotHaveNextAgainstViolation() {
    assertThat(iter(FOO)).doesNotHaveNext();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testIsEqualsToAgainstDifferingValues() {
    assertThat(iter(FOO, BAR, BAZ))
      .isEqualTo(iter(FOO, BAR, QUX));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testIsEqualToAgainstTooFewValues() {
    assertThat(iter(FOO, BAR))
      .isEqualTo(iter(FOO, BAR, BAZ));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testIsEqualToAgainstTooManyValues() {
    assertThat(iter(FOO, BAR, BAZ))
      .isEqualTo(iter(FOO, BAR));
  }

  private Iterator<String> iter(final String... values) {
    return Arrays.asList(values).iterator();
  }
}
