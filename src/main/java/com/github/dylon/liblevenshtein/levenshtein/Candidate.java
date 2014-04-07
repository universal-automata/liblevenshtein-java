package com.github.dylon.liblevenshtein.levenshtein;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Candidate {
	@Getter String term;
	@Getter String lowerTerm;
	@Getter @Setter int distance;

	public Candidate term(final String term) {
		this.term = term;
		this.lowerTerm = term.toLowerCase();
		return this;
	}
}
