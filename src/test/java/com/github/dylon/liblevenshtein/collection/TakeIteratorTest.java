package com.github.dylon.liblevenshtein.collection;

import java.util.Arrays;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import lombok.val;

public class TakeIteratorTest {

  @Test
  public void testIterator() {
    val iterator = new TakeIterator<Integer>(3, Arrays.asList(1,2,3,4,5).iterator());
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), Integer.valueOf(1));
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), Integer.valueOf(2));
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), Integer.valueOf(3));
    assertFalse(iterator.hasNext());
  }
}
