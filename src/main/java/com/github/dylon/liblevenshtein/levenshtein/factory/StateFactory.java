package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.Setter;
import lombok.experimental.Accessors;

import com.github.dylon.liblevenshtein.levenshtein.IState;
import com.github.dylon.liblevenshtein.levenshtein.State;

@Accessors(fluent=true)
public class StateFactory implements IStateFactory {
  private final Queue<IState> states = new ArrayDeque<>();

  @Setter
  private IElementFactory<int[]> elementFactory;

  @Override
  public IState build(final int[]... positions) {
    IState state = states.poll();

    if (null == state) {
      state = new State(elementFactory);
    }

    for (final int[] position : positions) {
      state.add(position);
    }

    return state;
  }

  @Override
  public void recycle(final IState state) {
    state.clear();
    states.offer(state);
  }
}
