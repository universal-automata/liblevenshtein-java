package com.github.dylon.liblevenshtein.serialization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import com.google.common.base.Joiner;

import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;

import com.github.dylon.liblevenshtein.collection.dawg.DawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.SortedDawg;
import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.PrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.TransitionFactory;
import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.Transducer;
import com.github.dylon.liblevenshtein.levenshtein.TransducerAttributes;
import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;

/**
 * (De)Serializer for plain text files.  Dictionaries have their terms
 * serialized to a newline-delimited, text file.  Transducers are serialized to
 * a plain text, property file.
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
@SuppressWarnings("unchecked")
@EqualsAndHashCode(callSuper = false)
@ExtensionMethod({PlainTextSerializer.PropertiesExtensions.class})
public class PlainTextSerializer extends AbstractSerializer {

  /**
   * Specifies that this {@link PlainTextSerializer} only deals with sorted
   * collections.  If you do not know whether the collections you'll be
   * (de)serializing are sorted, you should leave this false.
   */
  private boolean isSorted = false;

  /**
   * Serializes SortedDawg dictionaries for Transducer, Properties files.
   */
  private final Serializer serializer = new ProtobufSerializer();

  // Serializers
  // ---------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      final Serializable object,
      final OutputStream stream) throws Exception {

    log.info("Serializing instance of [{}] to stream", object.getClass());

    if (object instanceof SortedDawg) {
      final Collection<String> dictionary = dictionaryFor((SortedDawg) object);
      try (final BufferedWriter writer =
          new BufferedWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8))) {
        for (final String term : dictionary) {
          writer.write(term);
          writer.newLine();
        }
      }
      return;
    }

    if (object instanceof Transducer) {
      final Transducer<DawgNode, Object> transducer =
        (Transducer<DawgNode, Object>) object;
      final TransducerAttributes<DawgNode, Object> attributes =
        transducer.attributes();
      final Collection<String> dictionary = dictionaryFor(attributes.dictionary());
      new Properties()
        .setInteger("maxDistance", attributes.maxDistance())
        .setBoolean("includeDistance", attributes.includeDistance())
        .setInteger("maxCandidates", attributes.maxCandidates())
        .setAlgorithm("algorithm", attributes.algorithm())
        .setBoolean("isSorted", isSorted)
        .setCollection("dictionary", dictionary)
        .store(stream,
          MessageFormat.format("Serialized on {0,date,long} at {0,time,full}",
            new Date()));
      return;
    }

    final String message = String.format(
      "Unsupported type [%s] for serializer [%s]",
      object.getClass(), getClass());
    throw new IllegalArgumentException(message);
  }

  private Collection<String> dictionaryFor(final SortedDawg dictionary) {
    return isSorted
      ? new TreeSet<>(dictionary)
      : dictionary;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] serialize(final Serializable object) throws Exception {
    log.info("Serializing instance of [{}] to byte array", object.getClass());
    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      serialize(object, stream);
      return stream.toByteArray();
    }
  }

  // Deserializers
  // ---------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      final Class<Type> type,
      final InputStream stream) throws Exception {

    log.info("Deserializing an instance of [{}] from a stream", type);

    if (SortedDawg.class.isAssignableFrom(type)) {
      try (final BufferedReader reader =
          new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

        if (isSorted) {
          log.info("Assuming the dictionary is sorted for deserialization");

          final SortedDawg dictionary = new SortedDawg(
            new PrefixFactory<DawgNode>(),
            new DawgNodeFactory(),
            new TransitionFactory<DawgNode>());

          for (String term = reader.readLine(); null != term; term = reader.readLine()) {
            dictionary.add(term);
          }

          dictionary.finish();
          return (Type) dictionary;
        }

        final Collection<String> dictionary = new TreeSet<>();

        for (String term = reader.readLine(); null != term; term = reader.readLine()) {
          dictionary.add(term);
        }

        return (Type) new SortedDawg(
          new PrefixFactory<DawgNode>(),
          new DawgNodeFactory(),
          new TransitionFactory<DawgNode>(),
          dictionary);
      }
    }

    if (Transducer.class.isAssignableFrom(type)) {
      final Properties properties = new Properties();
      properties.load(stream);
      return (Type) new TransducerBuilder()
        .dictionary(
          properties.getCollection("dictionary"),
          properties.getBoolean("isSorted"))
        .algorithm(properties.getAlgorithm("algorithm"))
        .defaultMaxDistance(properties.getInteger("maxDistance"))
        .includeDistance(properties.getBoolean("includeDistance"))
        .maxCandidates(properties.getInteger("maxCandidates"))
        .build();
    }

    final String message = String.format(
      "Unsupported type [%s] for serializer [%s]",
      type, getClass());
    throw new IllegalArgumentException(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      final Class<Type> type,
      final byte[] bytes) throws Exception {
    log.info("Deserializing an instance of [{}] from a byte array", type);
    try (final InputStream stream = new ByteArrayInputStream(bytes)) {
      return deserialize(type, stream);
    }
  }

  public static class PropertiesExtensions {

    public static String getValue(final Properties self, final String key) {
      final String value = self.getProperty(key);
      assertNotNull(key, value);
      return value;
    }

    public static Properties setInteger(
        final Properties self,
        final String key,
        final int value) {
      self.setProperty(key, Integer.toString(value));
      return self;
    }

    public static int getInteger(final Properties self, final String key) {
      final String value = getValue(self, key);
      try {
        return Integer.parseInt(value);
      }
      catch (final NumberFormatException exception) {
        final String message =
          String.format("Invalid integer [%s] for property [%s]",
            value, key);
        throw new IllegalArgumentException(message, exception);
      }
    }

    public static Properties setBoolean(
        final Properties self,
        final String key,
        final boolean value) {
      self.setProperty(key, Boolean.toString(value));
      return self;
    }

    public static boolean getBoolean(final Properties self, final String key) {
      final String value = getValue(self, key);
      if ("true".equals(value)) {
        return true;
      }
      if ("false".equals(value)) {
        return false;
      }
      final String message =
        String.format("Invalid boolean [%s] for property [%s]",
          value, key);
      throw new IllegalArgumentException(message);
    }

    public static Properties setCollection(
        final Properties self,
        final String key,
        final Collection<String> collection) {
      self.setProperty(key, Joiner.on("\\n").join(collection));
      return self;
    }

    public static Collection<String> getCollection(
        final Properties self,
        final String key) {
      final String value = getValue(self, key);
      final String[] collection = value.split("\\\\n");
      return Arrays.asList(collection);
    }

    public static Properties setAlgorithm(
        final Properties self,
        final String key,
        final Algorithm algorithm) {
      self.setProperty(key, algorithm.name());
      return self;
    }

    public static Algorithm getAlgorithm(
        final Properties self,
        final String key) {
      final String value = getValue(self, key);
      return Algorithm.valueOf(value);
    }

    private static void assertNotNull(final String key, final String value) {
      if (null == value) {
        final String message =
          String.format("No value defined for property [%s]", key);
        throw new IllegalArgumentException(message);
      }
    }
  }
}
