package com.github.dylon.liblevenshtein.levenshtein;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementTest {

  @Test
  public void testEqualsAndHashCode() {
    final Element<int[]> e1 = build(1,2, 2,3, 3,4);
    final Element<int[]> e2 = build(1,2, 2,3, 3,4);
    final Element<int[]> e3 = build(1,3, 2,4, 3,5);

    assertThat(e1)
      .isEqualTo(e1)
      .isEqualTo(e2)
      .isNotEqualTo(e3);

    assertThat(e1.hashCode())
      .isEqualTo(e2.hashCode())
      .isNotEqualTo(e3.hashCode());
  }

  private Element<int[]> build(
      final int i1, final int e1,
      final int i2, final int e2,
      final int i3, final int e3) {

    final Element<int[]> element1 = new Element<>();
    element1.value(new int[] {i1, e1});

    final Element<int[]> element2 = new Element<>();
    element2.value(new int[] {i2, e2});

    final Element<int[]> element3 = new Element<>();
    element3.value(new int[] {i3, e3});

    element1.next(element2);
    element2.prev(element1);
    element2.next(element3);
    element3.prev(element2);
    return element2;
  }
}
