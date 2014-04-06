package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@NoArgsConstructor
@Accessors(fluent=true)
public class Transition<NodeType extends IDawgNode<NodeType>> {
  NodeType source;
  char label;
  NodeType target;
}
