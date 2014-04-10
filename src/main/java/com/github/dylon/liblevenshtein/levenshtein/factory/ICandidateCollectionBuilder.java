package com.github.dylon.liblevenshtein.levenshtein.factory;

import com.github.dylon.liblevenshtein.levenshtein.ICandidateCollection;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ICandidateCollectionBuilder<Type> {

  ICandidateCollectionBuilder<Type> maxCandidates(int maxCandidates);

  ICandidateCollection<Type> build();
}
