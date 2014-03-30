package com.github.dylon.liblevenshtein.collection;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class SymmetricImmutablePairTest {

  @DataProvider(name = "equivalentPairs")
  public Object[][] pairs() {
    return new Object[][] {
      {build("a", "a"), build("a", "a")},
      {build("a", "b"), build("b", "a")},
      {build("b", "a"), build("a", "b")}
    };
  }

  @Test(dataProvider = "equivalentPairs")
  public void test(
      final SymmetricImmutablePair<String> lhs,
      final SymmetricImmutablePair<String> rhs) {

    assertEquals(lhs.compareTo(lhs), 0);
    assertEquals(rhs.compareTo(rhs), 0);
    assertEquals(lhs.compareTo(rhs), 0);
    assertEquals(rhs.compareTo(lhs), 0);

    assertTrue(lhs.equals(lhs));
    assertTrue(rhs.equals(rhs));
    assertTrue(lhs.equals(rhs));
    assertTrue(rhs.equals(lhs));

    assertEquals(lhs.hashCode(), rhs.hashCode());

    final Object2IntMap<SymmetricImmutablePair<String>> map =
      new Object2IntOpenHashMap<>();

    map.put(lhs, 1);
    assertEquals(map.getInt(rhs), 1);

    map.put(rhs, 2);
    assertEquals(map.getInt(lhs), 2);
  }

  public SymmetricImmutablePair<String> build(
      final String first,
      final String second) {
    return new SymmetricImmutablePair<String>(first, second);
  }
}
