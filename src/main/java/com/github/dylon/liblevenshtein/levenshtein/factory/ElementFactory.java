package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import com.github.dylon.liblevenshtein.levenshtein.Element;

public class ElementFactory<Type> implements IElementFactory<Type> {
	private final Queue<Element<Type>> elements = new ArrayDeque<>();

	@Override
	public Element<Type> build(final Type value) {
		Element<Type> element = elements.poll();

		if (null == element) {
			element = new Element<Type>();
		}

		element.value(value);
		return element;
	}

	@Override
	public void recycle(Element<Type> element) {
		element.prev(null);
		element.next(null);
		element.value(null);
		elements.offer(element);
	}
}
