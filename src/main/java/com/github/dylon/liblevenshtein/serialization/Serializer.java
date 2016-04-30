package com.github.dylon.liblevenshtein.serialization;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * (De)Serialization utilities for various interchange formats.
 */
public interface Serializer {

  // Serializers
  // ---------------------------------------------------------------------------

  /**
   * Serializes an object to some output stream.
   * @param object Object to serialize.
   * @param stream Stream into which to write the serialization.
   * @throws Exception When the object cannot be serialized
   * (implementation-specific).
   */
  void serialize(
      Serializable object,
      OutputStream stream) throws Exception;

  /**
   * Serializes an object and returns its bytes.
   * @param object Object to serialize.
   * @return Bytes of the serialized object.
   * @throws Exception When the object cannot be serialized
   * (implementation-specific).
   */
  byte[] serialize(Serializable object) throws Exception;

  /**
   * Serializes an object to some {@link Path}
   * @param object Object to serialize.
   * @param path Where the serialized object should be written.
   * @throws Exception When the object cannot be serialized
   * (implementation-specific).
   */
  void serialize(
      Serializable object,
      Path path) throws Exception;

  /**
   * Serializes an object to some {@link File}
   * @param object Object to serialize.
   * @param file Where the serialized object should be written.
   * @throws Exception When the object cannot be serialized
   * (implementation-specific).
   */
  void serialize(
      Serializable object,
      File file) throws Exception;

  /**
   * Serializes an object to some {@link Path}
   * @param object Object to serialize.
   * @param path Where the serialized object should be written.
   * @throws Exception When the object cannot be serialized
   * (implementation-specific).
   */
  void serialize(
      Serializable object,
      String path) throws Exception;

  // Deserializers
  // ---------------------------------------------------------------------------

  /**
   * Deserializes an object from some stream.
   * @param type Class of the deserialized object.
   * @param stream Stream from which to deserialize the object.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from the stream.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type extends Serializable>
    Type deserialize(
      Class<Type> type,
      InputStream stream) throws Exception;

  /**
   * Deserializes an object from its byes.
   * @param type Class of the deserialized object.
   * @param bytes Bytes of the serialized object.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from its bytes.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type extends Serializable>
    Type deserialize(
      Class<Type> type,
      byte[] bytes) throws Exception;

  /**
   * Deserializes an object from some file.
   * @param type Class of the deserialized object.
   * @param path Where the object should be read from.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from the stream.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type extends Serializable>
    Type deserialize(
      Class<Type> type,
      Path path) throws Exception;

  /**
   * Deserializes an object from some file.
   * @param type Class of the deserialized object.
   * @param file Where the object should be read from.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from the stream.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type extends Serializable>
    Type deserialize(
      Class<Type> type,
      File file) throws Exception;

  /**
   * Deserializes an object from a URL.
   * @param type Class of the deserialized object.
   * @param url Where the object should be read from.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from the stream.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type extends Serializable>
    Type deserialize(
      Class<Type> type,
      URL url) throws Exception;

  /**
   * Deserializes an object from a URI.
   * @param type Class of the deserialized object.
   * @param uri Where the object should be read from.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from the stream.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type extends Serializable>
    Type deserialize(
      Class<Type> type,
      URI uri) throws Exception;

  /**
   * Deserializes an object from a filesystem path or Java-compatible URI.
   * @param type Class of the deserialized object.
   * @param pathOrUri Where the object should be read from.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from the stream.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type extends Serializable>
    Type deserialize(
      Class<Type> type,
      String pathOrUri) throws Exception;
}
