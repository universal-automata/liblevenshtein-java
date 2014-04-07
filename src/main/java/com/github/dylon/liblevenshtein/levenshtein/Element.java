package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Element<Type> {
	@Setter @Getter Element<Type> prev = null;
	@Setter @Getter Element<Type> next = null;
	@Setter @Getter Type value = null;
}
