package com.github.liblevenshtein.collection.dictionary.factory;

import java.io.Serializable;

import com.github.liblevenshtein.collection.dictionary.IDawgNode;
import com.github.liblevenshtein.collection.dictionary.Transition;

/**
 * Builds {@link Transition}s that link nodes together, under character labels.
 * @param <NodeType> Kind of nodes linked by the tranistions.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class TransitionFactory<NodeType extends IDawgNode<NodeType>>
  implements ITransitionFactory<NodeType>, Serializable {

  private static final long serialVersionUID = 1L;

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
