package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.Iterator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.github.dylon.liblevenshtein.collection.IDawgFactory;
import com.github.dylon.liblevenshtein.collection.IDawgNodeFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class DawgFactory implements IDawgFactory<DawgNode, Dawg> {
  @Setter private IDawgNodeFactory<DawgNode> factory;

  /**
   * {@inheritDoc}
   */
  @Override
  public Dawg build(@NonNull final Iterator<String> terms) {
    return new Dawg(factory, terms);
  }
}
