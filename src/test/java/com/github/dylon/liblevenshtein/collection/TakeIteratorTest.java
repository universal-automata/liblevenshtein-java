package com.github.dylon.liblevenshtein.collection;

import java.util.Arrays;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;

public class TakeIteratorTest {

  @Test
  public void testIterator() {
    val iterator = new TakeIterator<Integer>(3, Arrays.asList(1, 2, 3, 4, 5).iterator());
    assertThat(iterator.hasNext()).isTrue();
    assertThat(iterator.next()).isEqualTo(Integer.valueOf(1));
    assertThat(iterator.hasNext()).isTrue();
    assertThat(iterator.next()).isEqualTo(Integer.valueOf(2));
    assertThat(iterator.hasNext()).isTrue();
    assertThat(iterator.next()).isEqualTo(Integer.valueOf(3));
    assertThat(iterator.hasNext()).isFalse();
  }
}
