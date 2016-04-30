package com.github.dylon.liblevenshtein.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * (De)Serializer for Java bytecode.
 */
@Slf4j
@ToString(callSuper = false)
@SuppressWarnings("unchecked")
@EqualsAndHashCode(callSuper = false)
public class BytecodeSerializer extends AbstractSerializer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      final Serializable object,
      final OutputStream stream) throws Exception {
    log.info("Serializing an instance of [{}] to a stream", object.getClass());
    final ObjectOutputStream objectStream = new ObjectOutputStream(stream);
    objectStream.writeObject(object);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] serialize(final Serializable object) throws Exception {
    log.info("Serializing an instance of [{}] to a byte array", object.getClass());
    try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
      serialize(object, stream);
      return stream.toByteArray();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type extends Serializable> Type deserialize(
      final Class<Type> type,
      final InputStream stream) throws Exception {
    log.info("Deserializing an instance of [{}] from a stream", type);
    final ObjectInputStream objectStream = new ObjectInputStream(stream);
    return (Type) objectStream.readObject();
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
}
