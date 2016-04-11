package com.github.dylon.liblevenshtein.collection;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;

public class SymmetricImmutablePairTest {

  @DataProvider(name = "equivalentPairs")
  public Object[][] equivalentPairs() {
    return new Object[][] {
      {build("a", "a"), build("a", "a")},
      {build("a", "b"), build("b", "a")},
      {build("b", "a"), build("a", "b")}
    };
  }

  @DataProvider(name = "inequivalentPairs")
  public Object[][] inequivalentPairs() {
    return new Object[][] {
      {build("a", "b"), build("a", "c")}
    };
  }

  @Test(dataProvider = "equivalentPairs")
  public void testEquivalentPairs(
      final SymmetricImmutablePair<String> lhs,
      final SymmetricImmutablePair<String> rhs) {

    assertThat(lhs).isEqualByComparingTo(lhs);
    assertThat(rhs).isEqualByComparingTo(rhs);
    assertThat(lhs).isEqualByComparingTo(rhs);
    assertThat(rhs).isEqualByComparingTo(lhs);

    assertThat(lhs).isEqualTo(lhs);
    assertThat(rhs).isEqualTo(rhs);
    assertThat(lhs).isEqualTo(rhs);
    assertThat(rhs).isEqualTo(lhs);

    assertThat(lhs.hashCode()).isEqualTo(rhs.hashCode());

    Object2IntMap<SymmetricImmutablePair<String>> map;

    map = new Object2IntOpenHashMap<>(2);

    map.put(lhs, 1);
    assertThat(map).containsEntry(lhs, 1);
    assertThat(map).containsEntry(rhs, 1);

    map.put(rhs, 2);
    assertThat(map).containsEntry(rhs, 2);
    assertThat(map).containsEntry(lhs, 2);

    map = new Object2IntRBTreeMap<>();

    map.put(lhs, 1);
    assertThat(map).containsEntry(lhs, 1);
    assertThat(map).containsEntry(rhs, 1);

    map.put(rhs, 2);
    assertThat(map).containsEntry(rhs, 2);
    assertThat(map).containsEntry(lhs, 2);
  }

  @Test(dataProvider = "inequivalentPairs")
  public void testInequivalentPairs(
      final SymmetricImmutablePair<String> lhs,
      final SymmetricImmutablePair<String> rhs) {

    assertThat(lhs).isEqualByComparingTo(lhs);
    assertThat(rhs).isEqualByComparingTo(rhs);
    assertThat(lhs).isLessThan(rhs);
    assertThat(rhs).isGreaterThan(lhs);

    assertThat(lhs).isEqualTo(lhs);
    assertThat(rhs).isEqualTo(rhs);
    assertThat(lhs).isNotEqualTo(rhs);
    assertThat(rhs).isNotEqualTo(lhs);

    assertThat(lhs.hashCode()).isNotEqualTo(rhs.hashCode());
  }

  public SymmetricImmutablePair<String> build(
      final String first,
      final String second) {
    return new SymmetricImmutablePair<>(first, second);
  }
}
