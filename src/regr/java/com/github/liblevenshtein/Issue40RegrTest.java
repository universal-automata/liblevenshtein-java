package com.github.liblevenshtein;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.liblevenshtein.collection.dictionary.Dawg;
import com.github.liblevenshtein.collection.dictionary.SortedDawg;
import com.github.liblevenshtein.collection.dictionary.factory.DawgFactory;
import com.github.liblevenshtein.serialization.ProtobufSerializer;
import com.github.liblevenshtein.serialization.Serializer;

import static com.github.liblevenshtein.assertion.SetAssertions.assertThat;

/**
 * Regression test for issue #40, "ProtobufSerializer throws
 * InvalidProtocolBufferException when dictionary depth &gt; 100".
 * @see https://github.com/universal-automata/liblevenshtein-java/issues/40
 */
public class Issue40RegrTest {

  private Dawg dictionary;

  @BeforeClass
  public void setUp() {
    final List<String> terms = new ArrayList<>(5);
    final StringBuilder buffer = new StringBuilder();
    for (final char c : new char[] {'a', 'b', 'c', 'd', 'e'}) {
      buffer.setLength(0);
      for (int i = 0; i < 200; i += 1) {
        buffer.append(c);
      }
      terms.add(buffer.toString());
    }
    final DawgFactory factory = new DawgFactory();
    this.dictionary = factory.build(terms, true);
  }

  @Test
  public void verifyProtobufSerializerDeserializesDepthsGreaterThan100() throws Exception {
    final Serializer serializer = new ProtobufSerializer();
    final byte[] bytes = serializer.serialize(dictionary);
    final Dawg deserialized = serializer.deserialize(SortedDawg.class, bytes);
    assertThat(deserialized).isEqualTo(dictionary);
  }
}
