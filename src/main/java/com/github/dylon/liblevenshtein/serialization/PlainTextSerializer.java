package com.github.dylon.liblevenshtein.serialization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.TreeSet;

import com.google.common.base.Joiner;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
      @NonNull final Serializable object,
      @NonNull final OutputStream stream) throws Exception {

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

  /**
   * Returns a sorted {@link Collection} of the dictionary.
   * @param dictionary {@link Collection} to sort.
   * @return Sorted version of dictionary.
   */
  private Collection<String> dictionaryFor(@NonNull final SortedDawg dictionary) {
    return isSorted
      ? new TreeSet<>(dictionary)
      : dictionary;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] serialize(@NonNull final Serializable object) throws Exception {
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
      @NonNull final Class<Type> type,
      @NonNull final InputStream stream) throws Exception {

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
      @NonNull final Class<Type> type,
      @NonNull final byte[] bytes) throws Exception {
    log.info("Deserializing an instance of [{}] from a byte array", type);
    try (final InputStream stream = new ByteArrayInputStream(bytes)) {
      return deserialize(type, stream);
    }
  }

  /**
   * Extension methods for {@link Properties}.
   */
  public static class PropertiesExtensions {

    /**
     * Returns the property mapped-to by key.
     * @param self Contains the property value to return.
     * @param key Name of the property to return.
     * @return Property value mapped-to by key.
     * @throws IllegalArgumentException When no value is mapped-to by key.
     */
    public static String getValue(final Properties self, final String key) {
      final String value = self.getProperty(key);
      assertNotNull(key, value);
      return value;
    }

    /**
     * Sets an integer property.
     * @param self Contains the integer property.
     * @param key Name of the integer property.
     * @param value Integer value to set.
     * @return self for fluency.
     */
    public static Properties setInteger(
        final Properties self,
        final String key,
        final int value) {
      self.setProperty(key, Integer.toString(value));
      return self;
    }

    /**
     * Returns an integer property.
     * @param self Contains the integer property.
     * @param key Name of the integer property.
     * @return Integer mapped-to by key.
     * @throws IllegalArgumentException When no valid integer is mapped-to by
     * key.
     */
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

    /**
     * Sets a boolean property.
     * @param self Contains the boolean property.
     * @param key Name of the boolean property.
     * @param value Boolean to set.
     * @return self for fluency.
     */
    public static Properties setBoolean(
        final Properties self,
        final String key,
        final boolean value) {
      self.setProperty(key, Boolean.toString(value));
      return self;
    }

    /**
     * Returns a boolean property.
     * @param self Contains the boolean property.
     * @param key Name of the boolean property.
     * @return Boolean mapped-to by key.
     * @throws IllegalArgumentException When no valid boolean is mapped-to by
     * key.
     */
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

    /**
     * Sets a {@link Collection} property.
     * @param self Contains the {@link Collection} property.
     * @param key Name of the {@link Collection} property.
     * @param collection {@link Collection} to set.
     * @return self for fluency.
     */
    public static Properties setCollection(
        final Properties self,
        final String key,
        final Collection<String> collection) {
      self.setProperty(key, Joiner.on("\\n").join(collection));
      return self;
    }

    /**
     * Returns a {@link Collection} property.
     * @param self Contains the {@link Collection} property.
     * @param key Name of the {@link Collection} property.
     * @return {@link Collection} mapped-to by key.
     * @throws IllegalArgumentException When no {@link Collection} is mapped-to
     * by key.
     */
    public static Collection<String> getCollection(
        final Properties self,
        final String key) {
      final String value = getValue(self, key);
      final String[] collection = value.split("\\\\n");
      return Arrays.asList(collection);
    }

    /**
     * Sets an {@link Algorithm} property.
     * @param self Contains the {@link Algorithm} property.
     * @param key Name of the {@link Algorithm} property.
     * @param algorithm {@link Algorithm} to set.
     * @return self for fluency.
     */
    public static Properties setAlgorithm(
        final Properties self,
        final String key,
        final Algorithm algorithm) {
      self.setProperty(key, algorithm.name());
      return self;
    }

    /**
     * Returns an {@link Algorithm} property.
     * @param self Contains the {@link Algorithm} property.
     * @param key Name of the {@link Algorithm} property.
     * @return {@link Algorithm} mapped-to by key.
     * @throws IllegalArgumentException When no {@link Algorithm} is mapped-to
     * by key.
     */
    public static Algorithm getAlgorithm(
        final Properties self,
        final String key) {
      final String value = getValue(self, key);
      return Algorithm.valueOf(value);
    }

    /**
     * Asserts that value is not null.
     * @param key Identifier for the value.
     * @param value Value to assert-against.
     * @throws IllegalArgumentException When value is null.
     */
    private static void assertNotNull(final String key, final String value) {
      if (null == value) {
        final String message =
          String.format("No value defined for property [%s]", key);
        throw new IllegalArgumentException(message);
      }
    }
  }
}
