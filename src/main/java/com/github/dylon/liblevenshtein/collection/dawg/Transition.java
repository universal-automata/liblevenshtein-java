package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@NoArgsConstructor
public class Transition<NodeType extends IDawgNode<NodeType>> {
  NodeType source;
  char label;
  NodeType target;
}
