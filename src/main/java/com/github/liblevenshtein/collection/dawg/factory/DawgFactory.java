package com.github.liblevenshtein.collection.dawg.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.github.liblevenshtein.collection.dawg.AbstractDawg;
import com.github.liblevenshtein.collection.dawg.DawgNode;
import com.github.liblevenshtein.collection.dawg.IFinalFunction;
import com.github.liblevenshtein.collection.dawg.ITransitionFunction;
import com.github.liblevenshtein.collection.dawg.SortedDawg;

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
        return build(new ArrayList<>(terms), false);
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
