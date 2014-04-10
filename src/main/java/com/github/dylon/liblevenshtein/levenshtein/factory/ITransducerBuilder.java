package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.Collection;
import java.util.Comparator;

import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;
import com.github.dylon.liblevenshtein.levenshtein.Match;
import com.github.dylon.liblevenshtein.levenshtein.DistanceComparator;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ITransducerBuilder {

  ITransducerBuilder dictionary(Collection<String> dictionary);

  ITransducerBuilder dictionary(Collection<String> dictionary, boolean isSorted);

  ITransducerBuilder algorithm(Algorithm algorithm);

  ITransducerBuilder nearestCandidatesComparator(DistanceComparator nearestCandidatesComparator);

  ITransducerBuilder caseInsensitiveSort(boolean caseInsensitiveSort);

  ITransducerBuilder defaultMaxDistance(int defaultMaxDistance);

  ITransducerBuilder strategy(Match strategy);

  ITransducerBuilder includeDistance(boolean includeDistance);

  <CandidateType> ITransducer<?> build();
}
