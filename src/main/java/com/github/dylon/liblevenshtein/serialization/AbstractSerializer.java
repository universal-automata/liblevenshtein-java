package com.github.dylon.liblevenshtein.serialization;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Common, serialization routines.
 */
@Slf4j
public abstract class AbstractSerializer implements Serializer {

  /**
   * Operates on filesystem paths.
   * -- SETTER --
   * Operates on filesystem paths.
   * @param fileSystem Operates on filesystem paths.
   */
  @Setter
  @NonNull
  private FileSystem fileSystem = FileSystems.getDefault();

  // Serializers
  // ---------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      @NonNull final Serializable object,
      @NonNull final Path path) throws Exception {
    log.info("Serializing instance of class [{}] to path [{}]",
      object.getClass(), path);
    try (final OutputStream stream = Files.newOutputStream(path)) {
      serialize(object, stream);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      @NonNull final Serializable object,
      @NonNull final File file) throws Exception {
    log.info("Serializing instance of class [{}] to file [{}]",
      object.getClass(), file);
    serialize(object, file.toPath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      @NonNull final Serializable object,
      @NonNull final String path) throws Exception {
    log.info("Serializing instance of class [{}] to path [{}]",
      object.getClass(), path);
    serialize(object, fileSystem.getPath(path));
  }

  // Deserializers
  // ---------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      @NonNull final Class<Type> type,
      @NonNull final Path path) throws Exception {
    log.info("Deserilizing instance of [{}] from path [{}]",
      type, path);
    try (final InputStream stream = Files.newInputStream(path)) {
      return deserialize(type, stream);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      @NonNull final Class<Type> type,
      @NonNull final File file) throws Exception {
    log.info("Deserilizing instance of [{}] from file [{}]",
      type, file);
    return deserialize(type, file.toPath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      @NonNull final Class<Type> type,
      @NonNull final URL url) throws Exception {
    log.info("Deserilizing instance of [{}] from url [{}]",
      type, url);
    return deserialize(type, url.openStream());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      @NonNull final Class<Type> type,
      @NonNull final URI uri) throws Exception {
    log.info("Deserilizing instance of [{}] from uri [{}]",
      type, uri);
    return deserialize(type, uri.toURL());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      @NonNull final Class<Type> type,
      @NonNull final String pathOrUri) throws Exception {
    try {
      log.info("Attempting to deserialize instance of [{}] from uri [{}]",
        type, pathOrUri);
      return deserialize(type, URI.create(pathOrUri));
    }
    catch (final IllegalArgumentException exception) {
      log.warn("Invalid uri [{}]", pathOrUri);
    }

    log.info("Attempting to deserialize instance of [{}] from path [{}]",
      type, pathOrUri);
    return deserialize(type, fileSystem.getPath(pathOrUri));
  }
}
