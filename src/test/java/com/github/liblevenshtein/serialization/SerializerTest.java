package com.github.liblevenshtein.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.liblevenshtein.collection.dictionary.SortedDawg;
import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Transducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;

import static com.github.liblevenshtein.assertion.SetAssertions.assertThat;

@Slf4j
public class SerializerTest {

  private final FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
  private final Path tmpDir = fs.getPath("/jimfs");

  @BeforeTest
  public void setUp() throws IOException {
    try {
      Files.createDirectory(tmpDir);
    }
    catch (final Throwable thrown) {
      log.error("Failed to create directory [{}]", tmpDir, thrown);
      throw thrown;
    }
  }

  @Getter(lazy = true)
  private final Object[][] lazySerializerParams = buildParams();

  @DataProvider(name = "serializerParams")
  public Object[][] serializerParams() {
    // I couldn't get @DataProvider to play well with @Getter ...
    return lazySerializerParams();
  }

  private Object[][] buildParams() {
    try {
      final AbstractSerializer[] serializers = {
        new ProtobufSerializer(),
        new BytecodeSerializer(),
        new PlainTextSerializer(true),
        new PlainTextSerializer(false),
      };

      final SortedDawg dictionary = buildDictionary();

      final int[] maxDistances = {0, 2, Integer.MAX_VALUE};
      final boolean[] includeDistances = {true, false};
      final int[] maxCandidatess = {10, Integer.MAX_VALUE};
      final Algorithm[] algorithms = Algorithm.values();

      final List<Object[]> provider =
        new ArrayList<>(
          serializers.length
          * maxDistances.length
          * maxCandidatess.length
          * algorithms.length);

      for (final AbstractSerializer serializer : serializers) {
        serializer.fileSystem(fs);
        for (final int maxDistance : maxDistances) {
          for (final boolean includeDistance : includeDistances) {
            for (final int maxCandidates : maxCandidatess) {
              for (final Algorithm algorithm : algorithms) {
                final Transducer<?, ?> transducer =
                  (Transducer<?, ?>) (Object)
                  new TransducerBuilder()
                    .dictionary(dictionary)
                    .algorithm(algorithm)
                    .defaultMaxDistance(maxDistance)
                    .includeDistance(includeDistance)
                    .maxCandidates(maxCandidates)
                    .build();
                provider.add(new Object[] {
                  serializer,
                  dictionary,
                  transducer,
                });
              }
            }
          }
        }
      }

      return provider.toArray(new Object[0][0]);
    }
    catch (final Throwable thrown) {
      final String message = "Failed to build @DataProvider [serializerParams]";
      log.error(message, thrown);
      throw new SkipException(message, thrown);
    }
  }

  @Test(dataProvider = "serializerParams")
  private void testSerializeBytes(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final byte[] serializedDictionary = serializer.serialize(dictionary);
    final SortedDawg deserializedDictionary =
      serializer.deserialize(SortedDawg.class, serializedDictionary);
    assertThat(deserializedDictionary).isEqualTo(dictionary);

    final byte[] serializedTranducer = serializer.serialize(transducer);
    final Transducer<?, ?> deserializedTransducer =
      serializer.deserialize(Transducer.class, serializedTranducer);
    assertThat(deserializedTransducer).isEqualTo(transducer);
  }

  @Test(dataProvider = "serializerParams")
  private void testSerializeStream(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final byte[] serializedDictionary;
    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      serializer.serialize(dictionary, stream);
      serializedDictionary = stream.toByteArray();
    }

    final SortedDawg deserializedDictionary;
    try (final InputStream stream = new ByteArrayInputStream(serializedDictionary)) {
      deserializedDictionary = serializer.deserialize(SortedDawg.class, stream);
    }

    assertThat(deserializedDictionary).isEqualTo(dictionary);

    final byte[] serializedTransducer;
    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      serializer.serialize(transducer, stream);
      serializedTransducer = stream.toByteArray();
    }

    final Transducer<?, ?> deserializedTransducer;
    try (final InputStream stream = new ByteArrayInputStream(serializedTransducer)) {
      deserializedTransducer = serializer.deserialize(Transducer.class, stream);
    }

    assertThat(deserializedTransducer).isEqualTo(transducer);
  }

  @Test(dataProvider = "serializerParams")
  private void testSerializePath(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final Path dictionaryPath = createTempFile("dictionary");
    try {
      serializer.serialize(dictionary, dictionaryPath);
      final SortedDawg deserializedDictionary =
        serializer.deserialize(SortedDawg.class, dictionaryPath);
      assertThat(deserializedDictionary).isEqualTo(dictionary);
    }
    finally {
      Files.delete(dictionaryPath);
    }

    final Path transducerPath = createTempFile("transducer");
    try {
      serializer.serialize(transducer, transducerPath);
      final Transducer<?, ?> deserializedTransducer =
        serializer.deserialize(Transducer.class, transducerPath);
      assertThat(deserializedTransducer).isEqualTo(transducer);
    }
    finally {
      Files.delete(transducerPath);
    }
  }

  @Test(dataProvider = "serializerParams")
  private void testSerializeFile(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final Path dictionaryPath = createTempFile("dictionary");
    try {
      final File dictionaryFile = mock(File.class);
      when(dictionaryFile.toPath()).thenReturn(dictionaryPath);
      serializer.serialize(dictionary, dictionaryFile);
      final SortedDawg deserializedDictionary =
        serializer.deserialize(SortedDawg.class, dictionaryFile);
      assertThat(deserializedDictionary).isEqualTo(dictionary);
    }
    finally {
      Files.delete(dictionaryPath);
    }

    final Path transducerPath = createTempFile("transducer");
    try {
      final File transducerFile = mock(File.class);
      when(transducerFile.toPath()).thenReturn(transducerPath);
      serializer.serialize(transducer, transducerFile);
      final Transducer<?, ?> deserializedTransducer =
        serializer.deserialize(Transducer.class, transducerFile);
      assertThat(deserializedTransducer).isEqualTo(transducer);
    }
    finally {
      Files.delete(transducerPath);
    }
  }

  @Test(dataProvider = "serializerParams")
  private void testSerializePathString(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final Path dictionaryPath = createTempFile("dictionary");
    try {
      final String dictionaryPathString = dictionaryPath.toString();
      serializer.serialize(dictionary, dictionaryPathString);
      final SortedDawg deserializedDictionary =
        serializer.deserialize(SortedDawg.class, dictionaryPathString);
      assertThat(deserializedDictionary).isEqualTo(dictionary);
    }
    finally {
      Files.delete(dictionaryPath);
    }

    final Path transducerPath = createTempFile("transducer");
    try {
      final String transducerPathString = transducerPath.toString();
      serializer.serialize(transducer, transducerPathString);
      final Transducer<?, ?> deserializedTransducer =
        serializer.deserialize(Transducer.class, transducerPathString);
      assertThat(deserializedTransducer).isEqualTo(transducer);
    }
    finally {
      Files.delete(transducerPath);
    }
  }

  @Test(dataProvider = "serializerParams")
  private void testDeserializeUri(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final Path dictionaryPath = createTempFile("dictionary");
    try {
      final URI dictionaryUri = dictionaryPath.toUri();
      serializer.serialize(dictionary, dictionaryPath);
      final SortedDawg deserializedDictionary =
        serializer.deserialize(SortedDawg.class, dictionaryUri);
      assertThat(deserializedDictionary).isEqualTo(dictionary);
    }
    finally {
      Files.delete(dictionaryPath);
    }

    final Path transducerPath = createTempFile("transducer");
    try {
      final URI transducerUri = transducerPath.toUri();
      serializer.serialize(transducer, transducerPath);
      final Transducer<?, ?> deserializedTransducer =
        serializer.deserialize(Transducer.class, transducerUri);
      assertThat(deserializedTransducer).isEqualTo(transducer);
    }
    finally {
      Files.delete(transducerPath);
    }
  }

  @Test(dataProvider = "serializerParams")
  private void testDeserializeUriString(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final Path dictionaryPath = createTempFile("dictionary");
    try {
      final URI dictionaryUri = dictionaryPath.toUri();
      final String dictionaryUriString = dictionaryUri.toString();
      serializer.serialize(dictionary, dictionaryPath);
      final SortedDawg deserializedDictionary =
        serializer.deserialize(SortedDawg.class, dictionaryUriString);
      assertThat(deserializedDictionary).isEqualTo(dictionary);
    }
    finally {
      Files.delete(dictionaryPath);
    }

    final Path transducerPath = createTempFile("transducer");
    try {
      final URI transducerUri = transducerPath.toUri();
      final String transducerUriString = transducerUri.toString();
      serializer.serialize(transducer, transducerPath);
      final Transducer<?, ?> deserializedTransducer =
        serializer.deserialize(Transducer.class, transducerUriString);
      assertThat(deserializedTransducer).isEqualTo(transducer);
    }
    finally {
      Files.delete(transducerPath);
    }
  }

  @Test(dataProvider = "serializerParams")
  private void testDeserializeUrl(
      final Serializer serializer,
      final SortedDawg dictionary,
      final Transducer<?, ?> transducer) throws Exception {

    final byte[] serializedDictionary = serializer.serialize(dictionary);
    try (final InputStream stream = new ByteArrayInputStream(serializedDictionary)) {
      final URLConnection connection = mock(URLConnection.class);
      when(connection.getInputStream()).thenReturn(stream);
      final URLStreamHandler handler = new URLStreamHandler() {
        @Override
        protected URLConnection openConnection(final URL url) {
          return connection;
        }
      };
      final URL url =
        new URL("http", "www.example.com", 80, "/dictionary.tmp", handler);
      final SortedDawg deserializedDictionary =
        serializer.deserialize(SortedDawg.class, url);
      assertThat(deserializedDictionary).isEqualTo(dictionary);
    }

    final byte[] serializedTransducer = serializer.serialize(transducer);
    try (final InputStream stream = new ByteArrayInputStream(serializedTransducer)) {
      final URLConnection connection = mock(URLConnection.class);
      when(connection.getInputStream()).thenReturn(stream);
      final URLStreamHandler handler = new URLStreamHandler() {
        @Override
        protected URLConnection openConnection(final URL url) {
          return connection;
        }
      };
      final URL url =
        new URL("http", "www.example.com", 80, "/transducer.tmp", handler);
      final Transducer<?, ?> deserializedTransducer =
        serializer.deserialize(Transducer.class, url);
      assertThat(deserializedTransducer).isEqualTo(transducer);
    }
  }

  private Path createTempFile(final String type) throws IOException {
    final Path tempFile =
      tmpDir.resolve(String.format("%s-%s.tmp", type, UUID.randomUUID()));
    Files.createFile(tempFile);
    return tempFile;
  }

  private SortedDawg buildDictionary() throws Exception {
    final URL dictionaryUrl = getClass()
      .getResource("/top-20-most-common-english-words.protobuf.bytes");
    final Serializer serializer = new ProtobufSerializer();
    return serializer.deserialize(SortedDawg.class, dictionaryUrl);
  }
}
