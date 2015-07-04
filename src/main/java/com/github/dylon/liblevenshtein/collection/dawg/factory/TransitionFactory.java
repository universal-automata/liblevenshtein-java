package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.collection.dawg.IDawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.Transition;

/**
 * Builds {@link Transition}s that link nodes together, under character labels.
 * @param <NodeType> Kind of nodes linked by the tranistions.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class TransitionFactory<NodeType extends IDawgNode<NodeType>>
  implements ITransitionFactory<NodeType> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Transition<NodeType> build(
      final NodeType source,
      final char label,
      final NodeType target) {

    return new Transition<NodeType>()
      .source(source)
      .label(label)
      .target(target);
  }
}
