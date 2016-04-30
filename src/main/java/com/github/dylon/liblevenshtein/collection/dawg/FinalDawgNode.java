package com.github.dylon.liblevenshtein.collection.dawg;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

public class FinalDawgNode extends DawgNode {

  private static final long serialVersionUID = 1L;

  public FinalDawgNode(final Char2ObjectMap<DawgNode> edges) {
    super(edges);
  }

  @Override
  public boolean isFinal() {
    return true;
  }
}
