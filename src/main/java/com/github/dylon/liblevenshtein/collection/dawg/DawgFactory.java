package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.Setter;
import lombok.experimental.Accessors;

import com.github.dylon.liblevenshtein.collection.IDawgFactory;
import com.github.dylon.liblevenshtein.collection.IDawgNodeFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class DawgFactory implements IDawgFactory<DawgNode, Dawg> {
  @Setter private IDawgNodeFactory<DawgNode> factory;

  /**
   * {@inheritDoc}
   */
  @Override
  public Dawg build() {
    return new Dawg(factory);
  }
}
