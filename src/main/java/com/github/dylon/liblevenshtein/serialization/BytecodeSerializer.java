package com.github.dylon.liblevenshtein.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * (De)Serializer for Java bytecode.
 */
@SuppressWarnings("unchecked")
public class BytecodeSerializer implements Serializer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      final Serializable object,
      final OutputStream stream) throws Exception {
    final ObjectOutputStream objectStream = new ObjectOutputStream(stream);
    objectStream.writeObject(object);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] serialize(
      final  Serializable object) throws Exception {
    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      serialize(object, stream);
      return stream.toByteArray();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type> Type deserialize(
      final Class<Type> type,
      final InputStream stream) throws Exception {
    final ObjectInputStream objectStream = new ObjectInputStream(stream);
    return (Type) objectStream.readObject();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type> Type deserialize(
      final Class<Type> type,
      final byte[] bytes) throws Exception {
    try (final InputStream stream = new ByteArrayInputStream(bytes)) {
      return deserialize(type, stream);
    }
  }
}
