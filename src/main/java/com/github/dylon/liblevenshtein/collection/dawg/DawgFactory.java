package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import com.github.dylon.liblevenshtein.collection.IDawgFactory;
import com.github.dylon.liblevenshtein.collection.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.IPrefixFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class DawgFactory implements IDawgFactory<DawgNode, Dawg> {
  @Setter private IDawgNodeFactory<DawgNode> factory;

	/**
	 * Builds and recycles prefix objects, which are used to generate terms from
	 * the dictionary's root.
	 */
  @Setter private IPrefixFactory<DawgNode> prefixFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public Dawg build(@NonNull final Collection<String> terms) {
    return new Dawg(prefixFactory, factory, terms);
  }
}
