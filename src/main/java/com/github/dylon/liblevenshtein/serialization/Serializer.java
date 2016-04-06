package com.github.dylon.liblevenshtein.serialization;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * (De)Serialization utilities for various interchange formats.
 */
public interface Serializer {

  /**
   * Serializes an object to some output stream.
   * @param object Object to serialize.
   * @param stream Stream into which to write the serialization.
   * @throws Exception When the object cannot be serialized
   * (implementation-specific).
   */
  void serialize(Serializable object, OutputStream stream) throws Exception;

  /**
   * Serializes an object and returns its bytes.
   * @param object Object to serialize.
   * @return Bytes of the serialized object.
   * @throws Exception When the object cannot be serialized
   * (implementation-specific).
   */
  byte[] serialize(Serializable object) throws Exception;

  /**
   * Deserializes an object from some stream.
   * @param type Class of the deserialized object.
   * @param stream Stream from which to deserialize the object.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from the stream.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type> Type deserialize(Class<Type> type, InputStream stream) throws Exception;

  /**
   * Deserializes an object from its byes.
   * @param type Class of the deserialized object.
   * @param bytes Bytes of the serialized object.
   * @param <Type> Type of the deserialized object.
   * @return Deserialized object from its bytes.
   * @throws Exception When the object cannot be deserialized
   * (implementation-specific).
   */
  <Type> Type deserialize(Class<Type> type, byte[] bytes) throws Exception;
}
