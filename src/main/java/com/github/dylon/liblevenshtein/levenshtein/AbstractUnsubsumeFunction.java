package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public abstract class AbstractUnsubsumeFunction
	implements IUnsubsumeFunction {

	@Setter
	protected ISubsumesFunction subsumes;
}
