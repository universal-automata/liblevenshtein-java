package com.github.dylon.liblevenshtein.collection.dawg.factory;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import com.github.dylon.liblevenshtein.collection.dawg.DawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.SortedDawg;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class DawgFactory implements IDawgFactory<DawgNode, SortedDawg> {
  @Setter private IDawgNodeFactory<DawgNode> factory;

  /**
   * Builds and recycles prefix objects, which are used to generate terms from
   * the dictionary's root.
   */
  @Setter private IPrefixFactory<DawgNode> prefixFactory;

	/**
	 * Builds (and recycles for memory efficiency) Transition objects
	 */
  @Setter private ITransitionFactory<DawgNode> transitionFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public SortedDawg build(@NonNull final Collection<String> terms) {
    return new SortedDawg(prefixFactory, factory, transitionFactory, terms);
  }
}
