package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.github.dylon.liblevenshtein.collection.dawg.AbstractDawg;
import com.github.dylon.liblevenshtein.collection.dawg.DawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.IFinalFunction;
import com.github.dylon.liblevenshtein.collection.dawg.ITransitionFunction;
import com.github.dylon.liblevenshtein.collection.dawg.SortedDawg;
import com.github.dylon.liblevenshtein.serialization.BytecodeSerializer;
import com.github.dylon.liblevenshtein.serialization.ProtobufSerializer;
import com.github.dylon.liblevenshtein.serialization.Serializer;

/**
 * Constructs DAWG instances.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class DawgFactory implements IDawgFactory<DawgNode, AbstractDawg>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Builds and recycles Dawg nodes.
   * -- SETTER --
   * Builds and recycles Dawg nodes.
   * @param dawgNodeFactory Builds and recycles Dawg nodes.
   * @return This {@link DawgFactory} for fluency.
   */
  @Setter private IDawgNodeFactory<DawgNode> dawgNodeFactory;

  /**
   * Builds and recycles prefix objects, which are used to generate terms from
   * the dictionary's root.
   * -- SETTER --
   * Builds and recycles prefix objects, which are used to generate terms from
   * the dictionary's root.
   * @param prefixFactory Builds and recycles prefix objects, which are used to
   * generate terms from the dictionary's root.
   * @return This {@link DawgFactory} for fluency.
   */
  @Setter private IPrefixFactory<DawgNode> prefixFactory;

  /**
   * Builds (and recycles for memory efficiency) Transition objects.
   * -- SETTER --
   * Builds (and recycles for memory efficiency) Transition objects.
   * @param transitionFactory Builds (and recycles for memory efficiency)
   * Transition objects.
   * @return This {@link DawgFactory} for fluency.
   */
  @Setter private ITransitionFactory<DawgNode> transitionFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDawg build(@NonNull final Collection<String> terms) {
    return build(terms, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDawg build(
      @NonNull final Collection<String> terms,
      final boolean isSorted) {

    if (!isSorted) {
      if (terms instanceof List) {
        // TODO: When I implement the unsorted algorithm, return an instance of
        // the unsorted Dawg instead of sorting the terms.
        Collections.sort((List<String>) terms);
      }
      else if (!(terms instanceof SortedDawg)) {
        return build(new ArrayList<String>(terms), false);
      }
    }

    return new SortedDawg(
        prefixFactory,
        dawgNodeFactory,
        transitionFactory,
        terms);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDawg build(
      @NonNull final InputStream stream,
      final boolean isSorted) throws IOException {

    try {
      final Serializer serializer = new ProtobufSerializer();
      log.info("Attempting to deserialize stream as a Protobuf model");
      return serializer.deserialize(SortedDawg.class, stream);
    }
    catch (final Exception outer) {
      try {
        final Serializer serializer = new BytecodeSerializer();
        log.info("Attempting to deserialize the stream as Bytecode");
        return serializer.deserialize(SortedDawg.class, stream);
      }
      catch (final Exception inner) {
        log.info("Not a serialized dictionary");
      }
    }

    try (final BufferedReader reader =
        new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
      if (!isSorted) {
        final SortedSet<String> dictionary = new TreeSet<>();
        for (String term = reader.readLine(); null != term; term = reader.readLine()) {
          dictionary.add(term);
        }
        return build(dictionary, true);
      }

      final SortedDawg dawg =
        new SortedDawg(prefixFactory, dawgNodeFactory, transitionFactory);

      for (String term = reader.readLine(); null != term; term = reader.readLine()) {
        if (!dawg.add(term)) {
          final String message =
            String.format("Failed to add term [%s] to dictionary", term);
          throw new IllegalArgumentException(message);
        }
      }

      dawg.finish();
      return dawg;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDawg build(
      @NonNull final InputStream stream) throws IOException {
    return build(stream, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IFinalFunction<DawgNode> isFinal(
      @NonNull final AbstractDawg dictionary) {
    return dictionary;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ITransitionFunction<DawgNode> transition(
      @NonNull final AbstractDawg dictionary) {
    return dictionary;
  }
}
