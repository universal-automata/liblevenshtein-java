package com.github.dylon.liblevenshtein.collection.dawg.factory;

import com.github.dylon.liblevenshtein.collection.dawg.Prefix;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IPrefixFactory<DictionaryNode> {

  Prefix<DictionaryNode> build(DictionaryNode node, String value);

  void recycle(Prefix<DictionaryNode> prefix);
}
