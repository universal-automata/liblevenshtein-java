package com.github.dylon.liblevenshtein.collection.dawg.factory;

import com.github.dylon.liblevenshtein.collection.dawg.IDawgNode;
import com.github.dylon.liblevenshtein.collection.dawg.Transition;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ITransitionFactory<NodeType extends IDawgNode<NodeType>> {

	Transition<NodeType> build(NodeType source, char label, NodeType target);

	void recycle(Transition<NodeType> transition);
}
