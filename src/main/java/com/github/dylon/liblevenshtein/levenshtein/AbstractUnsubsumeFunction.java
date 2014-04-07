package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Setter;
import lombok.experimental.Accessors;

import com.github.dylon.liblevenshtein.levenshtein.factory.IPositionFactory;

@Accessors(fluent=true)
public abstract class AbstractUnsubsumeFunction
  implements IUnsubsumeFunction {

  @Setter
  protected ISubsumesFunction subsumes;

  @Setter
  protected IPositionFactory positionFactory;
}
