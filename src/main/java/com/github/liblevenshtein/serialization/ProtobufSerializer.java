package com.github.liblevenshtein.serialization;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectSortedMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import com.google.protobuf.CodedInputStream;

import com.github.liblevenshtein.collection.dawg.DawgNode;
import com.github.liblevenshtein.collection.dawg.FinalDawgNode;
import com.github.liblevenshtein.collection.dawg.SortedDawg;
import com.github.liblevenshtein.proto.LibLevenshteinProtos;
import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Transducer;
import com.github.liblevenshtein.transducer.TransducerAttributes;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;

/**
 * (De)Serializer for Google's Protocol Buffer, data interchange format.
 */
@Slf4j
@ToString(callSuper = false)
@SuppressWarnings("unchecked")
@EqualsAndHashCode(callSuper = false)
public class ProtobufSerializer extends AbstractSerializer {

  /**
   * Format of error messages about unknown types.
   */
  private static final String UNKNOWN_TYPE_S = "Unknown type [%s]";

  // Serializers
  // ---------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      @NonNull final Serializable object,
      @NonNull final OutputStream stream) throws Exception {

    log.info("Deserializing an instance of [{}] from a stream", object.getClass());

    if (object instanceof SortedDawg) {
      final SortedDawg dawg = (SortedDawg) object;
      final LibLevenshteinProtos.Dawg proto = protoOf(dawg);
      proto.writeTo(stream);
      return;
    }

    if (object instanceof Transducer) {
      final Transducer<DawgNode, Object> transducer =
        (Transducer<DawgNode, Object>) object;
      final LibLevenshteinProtos.Transducer proto = protoOf(transducer);
      proto.writeTo(stream);
      return;
    }

    final String message = String.format("Unknown type of [%s]", object);
    throw new IllegalArgumentException(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] serialize(@NonNull final Serializable object) throws Exception {
    log.info("Serializing an instance of [{}] to a byte array", object.getClass());

    if (object instanceof SortedDawg) {
      final SortedDawg dawg = (SortedDawg) object;
      final LibLevenshteinProtos.Dawg proto = protoOf(dawg);
      return proto.toByteArray();
    }

    if (object instanceof Transducer) {
      final Transducer<DawgNode, Object> transducer =
        (Transducer<DawgNode, Object>) object;
      final LibLevenshteinProtos.Transducer proto = protoOf(transducer);
      return proto.toByteArray();
    }

    final String message = String.format(UNKNOWN_TYPE_S, object.getClass());
    throw new IllegalArgumentException(message);
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

    final CodedInputStream protoStream = CodedInputStream.newInstance(stream);
    protoStream.setRecursionLimit(Integer.MAX_VALUE);
    protoStream.setSizeLimit(Integer.MAX_VALUE);

    if (SortedDawg.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.Dawg proto =
        LibLevenshteinProtos.Dawg.parseFrom(protoStream);
      return (Type) modelOf(proto);
    }

    if (Transducer.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.Transducer proto =
        LibLevenshteinProtos.Transducer.parseFrom(protoStream);
      return (Type) modelOf(proto);
    }

    final String message = String.format(UNKNOWN_TYPE_S, type);
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

  // Models
  // ---------------------------------------------------------------------------

  /**
   * Returns the node of the prototype.
   * @param proto Prototype of the node.
   * @return Node of the prototype.
   */
  protected DawgNode modelOf(final LibLevenshteinProtos.DawgNode proto) {
    final Char2ObjectSortedMap<DawgNode> edges = new Char2ObjectRBTreeMap<>();
    for (final LibLevenshteinProtos.DawgNode.Edge edge : proto.getEdgeList()) {
      final char label = (char) edge.getCharKey();
      edges.put(label, modelOf(edge.getValue()));
    }
    if (proto.getIsFinal()) {
      return new FinalDawgNode(edges);
    }
    return new DawgNode(edges);
  }

  /**
   * Returns the dictionary of the prototype.
   * @param proto Prototype of the dictionary.
   * @return Dictionary of the prototype.
   */
  protected SortedDawg modelOf(final LibLevenshteinProtos.Dawg proto) {
    final DawgNode root = modelOf(proto.getRoot());
    return new SortedDawg(proto.getSize(), root);
  }

  /**
   * Returns the transducer of the prototype.
   * @param proto Prototype of the transducer.
   * @return Transducer of the prototype.
   */
  protected Transducer<DawgNode, Object> modelOf(final LibLevenshteinProtos.Transducer proto) {
    return (Transducer<DawgNode, Object>)
      new TransducerBuilder()
        .dictionary(modelOf(proto.getDictionary()))
        .algorithm(modelOf(proto.getAlgorithm()))
        .defaultMaxDistance(proto.getDefaultMaxDistance())
        .includeDistance(proto.getIncludeDistance())
        .maxCandidates(proto.getMaxCandidates())
        .build();
  }

  /**
   * Returns the Levenshtein algorithm for the prototype.
   * @param proto Levenshtein algorithm prototype.
   * @return Levenshtein algorithm for the prototype.
   */
  protected Algorithm modelOf(final LibLevenshteinProtos.Transducer.Algorithm proto) {
    switch (proto) {
      case STANDARD:
        return Algorithm.STANDARD;
      case TRANSPOSITION:
        return Algorithm.TRANSPOSITION;
      case MERGE_AND_SPLIT:
        return Algorithm.MERGE_AND_SPLIT;
      default:
        final String message = String.format("Unknown Algorithm [%s]", proto);
        throw new IllegalArgumentException(message);
    }
  }

  // Prototypes
  // ---------------------------------------------------------------------------

  /**
   * Returns the prototype of a transducer.
   * @param transducer Transducer whose prototype is to be returned.
   * @return Prototype of the transducer.
   */
  protected LibLevenshteinProtos.Transducer protoOf(final Transducer<DawgNode, Object> transducer) {
    final TransducerAttributes<DawgNode, Object> attributes =
      transducer.attributes();
    return LibLevenshteinProtos.Transducer.newBuilder()
      .setDefaultMaxDistance(attributes.maxDistance())
      .setIncludeDistance(attributes.includeDistance())
      .setMaxCandidates(attributes.maxCandidates())
      .setAlgorithm(protoOf(attributes.algorithm()))
      .setDictionary(protoOf(attributes.dictionary()))
      .build();
  }

  /**
   * Returns the prototype of the Levenshtein algorithm.
   * @param algorithm Levenshtein algorithm whose prototype is to be returned.
   * @return Prototype of the Levenshtein algorithm.
   */
  protected LibLevenshteinProtos.Transducer.Algorithm protoOf(final Algorithm algorithm) {
    switch (algorithm) {
      case STANDARD:
        return LibLevenshteinProtos.Transducer.Algorithm.STANDARD;
      case TRANSPOSITION:
        return LibLevenshteinProtos.Transducer.Algorithm.TRANSPOSITION;
      case MERGE_AND_SPLIT:
        return LibLevenshteinProtos.Transducer.Algorithm.MERGE_AND_SPLIT;
      default:
        final String message = String.format("Unknown Algorithm [%s]", algorithm);
        throw new IllegalArgumentException(message);
    }
  }

  /**
   * Returns the prototype of the dictionary.
   * @param dawg Dictionary whose prototype is to be returned.
   * @return Prototype of the dictionary.
   */
  protected LibLevenshteinProtos.Dawg protoOf(final SortedDawg dawg) {
    return LibLevenshteinProtos.Dawg.newBuilder()
      .setSize(dawg.size())
      .setRoot(protoOf(dawg.root()))
      .build();
  }

  /**
   * Returns the prototype of a node.
   * @param node Node whose prototype is to be returned.
   * @return The prototype of the node.
   */
  protected LibLevenshteinProtos.DawgNode protoOf(final DawgNode node) {
    final LibLevenshteinProtos.DawgNode.Builder builder =
      LibLevenshteinProtos.DawgNode.newBuilder();
    builder.setIsFinal(node.isFinal());
    for (final Char2ObjectMap.Entry<DawgNode> edge : node.edges().char2ObjectEntrySet()) {
      builder.addEdge(protoOf(edge.getCharKey(), edge.getValue()));
    }
    return builder.build();
  }

  /**
   * Returns the prototype of an edge.
   * @param label Annotation leading out of the current {@link DawgNode} to the
   * target {@link DawgNode}.
   * @param node Target {@link DawgNode} for the transition.
   * @return The prototype of an edge.
   */
  protected LibLevenshteinProtos.DawgNode.Edge protoOf(
      final char label,
      final DawgNode node) {
    return LibLevenshteinProtos.DawgNode.Edge.newBuilder()
      .setCharKey(label)
      .setValue(protoOf(node))
      .build();
  }
}
