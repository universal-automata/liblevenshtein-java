package com.github.dylon.liblevenshtein.utils;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static com.github.dylon.liblevenshtein.utils.ArrayUtils.arr;

public class ArrayUtilsTest {

  @Test
  public void testArr() {
    assertThat(arr()).isEqualTo(new int[0]);
    assertThat(arr(1,2,3)).isEqualTo(new int[] {1,2,3});
  }
}
