package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.Element;

public interface IElementFactory<Type> {

  Element<Type> build(Type value);

  void recycle(Element<Type> element);
}
