package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.IPositionTransitionFunction;

@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public abstract class AbstractPositionTransitionFactory
  implements IPositionTransitionFactory {

  Queue<IPositionTransitionFunction> transitions = new ArrayDeque<>();

  @Override
  public IPositionTransitionFunction build() {
    IPositionTransitionFunction transition = transitions.poll();

    if (null == transition) {
      transition = create();
    }

    return transition;
  }

  protected abstract IPositionTransitionFunction create();

  @Override
  public void recycle(final IPositionTransitionFunction transition) {
    transitions.offer(transition);
  }
}
