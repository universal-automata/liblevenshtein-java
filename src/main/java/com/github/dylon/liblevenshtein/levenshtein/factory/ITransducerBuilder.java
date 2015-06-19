package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.Collection;
import java.util.Comparator;

import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ITransducerBuilder {

  ITransducerBuilder dictionary(Collection<String> dictionary);

  ITransducerBuilder dictionary(Collection<String> dictionary, boolean isSorted);

  ITransducerBuilder algorithm(Algorithm algorithm);

  ITransducerBuilder defaultMaxDistance(int defaultMaxDistance);

  ITransducerBuilder includeDistance(boolean includeDistance);

  ITransducerBuilder maxCandidates(int includeDistance);

  <CandidateType> ITransducer<CandidateType> build();
}
