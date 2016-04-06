package com.github.dylon.liblevenshtein.serialization;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectSortedMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;

import com.github.dylon.liblevenshtein.collection.dawg.DawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.SortedDawg;
import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.LibLevenshteinProtos;
import com.github.dylon.liblevenshtein.levenshtein.Transducer;
import com.github.dylon.liblevenshtein.levenshtein.TransducerAttributes;
import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;

/**
 * (De)Serializer for Google's Protocol Buffer, data interchange format.
 */
@SuppressWarnings("unchecked")
public class ProtobufSerializer implements Serializer {

  // Serializers
  // ---------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(
      final Serializable object,
      final OutputStream stream) throws Exception {

    if (object instanceof DawgNode) {
      final DawgNode node = (DawgNode) object;
      final LibLevenshteinProtos.DawgNode proto = protoOf(node);
      proto.writeTo(stream);
      return;
    }

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
  public byte[] serialize(final Serializable object) throws Exception {
    if (object instanceof DawgNode) {
      final DawgNode node = (DawgNode) object;
      final LibLevenshteinProtos.DawgNode proto = protoOf(node);
      return proto.toByteArray();
    }

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

    final String message = String.format("Unknown type [%s]", object.getClass());
    throw new IllegalArgumentException(message);
  }

  // Deserializers
  // ---------------------------------------------------------------------------

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type> Type deserialize(
      final Class<Type> type,
      final InputStream stream) throws Exception {

    if (DawgNode.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.DawgNode proto =
        LibLevenshteinProtos.DawgNode.parseFrom(stream);
      return (Type) modelOf(proto);
    }

    if (SortedDawg.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.Dawg proto =
        LibLevenshteinProtos.Dawg.parseFrom(stream);
      return (Type) modelOf(proto);
    }

    if (Transducer.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.Transducer proto =
        LibLevenshteinProtos.Transducer.parseFrom(stream);
      return (Type) modelOf(proto);
    }

    final String message = String.format("Unknown type [%s]", type);
    throw new IllegalArgumentException(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <Type> Type deserialize(
      final Class<Type> type,
      final byte[] bytes) throws Exception {

    if (DawgNode.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.DawgNode proto =
        LibLevenshteinProtos.DawgNode.parseFrom(bytes);
      return (Type) modelOf(proto);
    }

    if (SortedDawg.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.Dawg proto =
        LibLevenshteinProtos.Dawg.parseFrom(bytes);
      return (Type) modelOf(proto);
    }

    if (Transducer.class.isAssignableFrom(type)) {
      final LibLevenshteinProtos.Transducer proto =
        LibLevenshteinProtos.Transducer.parseFrom(bytes);
      return (Type) modelOf(proto);
    }

    final String message = String.format("Unknown type [%s]", type);
    throw new IllegalArgumentException(message);
  }

  // Models
  // ---------------------------------------------------------------------------

  private DawgNode modelOf(final LibLevenshteinProtos.DawgNode proto) {
    final Char2ObjectSortedMap<DawgNode> edges = new Char2ObjectRBTreeMap<>();
    for (final LibLevenshteinProtos.DawgNode.Edge edge : proto.getEdgeList()) {
      final char label = (char) edge.getCharKey();
      edges.put(label, modelOf(edge.getValue()));
    }
    return new DawgNode(edges, proto.getIsFinal());
  }

  private SortedDawg modelOf(final LibLevenshteinProtos.Dawg proto) {
    final DawgNode root = modelOf(proto.getRoot());
    return new SortedDawg(proto.getSize(), root);
  }

  private Transducer<DawgNode, Object> modelOf(final LibLevenshteinProtos.Transducer proto) {
    return (Transducer<DawgNode, Object>)
      new TransducerBuilder()
        .dictionary(modelOf(proto.getDictionary()))
        .algorithm(modelOf(proto.getAlgorithm()))
        .defaultMaxDistance(proto.getDefaultMaxDistance())
        .includeDistance(proto.getIncludeDistance())
        .maxCandidates(proto.getMaxCandidates())
        .build();
  }

  private Algorithm modelOf(final LibLevenshteinProtos.Transducer.Algorithm proto) {
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

  private LibLevenshteinProtos.Transducer protoOf(final Transducer<DawgNode, Object> transducer) {
  	final TransducerAttributes<DawgNode, Object> attributes = transducer.attributes();
  	return LibLevenshteinProtos.Transducer.newBuilder()
  		.setDefaultMaxDistance(attributes.maxDistance())
  		.setIncludeDistance(attributes.includeDistance())
  		.setMaxCandidates(attributes.maxCandidates())
  		.setAlgorithm(protoOf(attributes.algorithm()))
  		.setDictionary(protoOf(attributes.dictionary()))
  		.build();
  }

  private LibLevenshteinProtos.Transducer.Algorithm protoOf(final Algorithm algorithm) {
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

  private LibLevenshteinProtos.Dawg protoOf(final SortedDawg dawg) {
    return LibLevenshteinProtos.Dawg.newBuilder()
      .setSize(dawg.size())
      .setRoot(protoOf(dawg.root()))
      .build();
  }

  private LibLevenshteinProtos.DawgNode protoOf(final DawgNode node) {
    final LibLevenshteinProtos.DawgNode.Builder builder =
      LibLevenshteinProtos.DawgNode.newBuilder();
    builder.setIsFinal(node.isFinal());
    for (final Char2ObjectMap.Entry<DawgNode> edge : node.edges().char2ObjectEntrySet()) {
      builder.addEdge(protoOf(edge.getCharKey(), edge.getValue()));
    }
    return builder.build();
  }

  private LibLevenshteinProtos.DawgNode.Edge protoOf(
      final char label,
      final DawgNode node) {
    return LibLevenshteinProtos.DawgNode.Edge.newBuilder()
      .setCharKey(label)
      .setValue(protoOf(node))
      .build();
  }
}
