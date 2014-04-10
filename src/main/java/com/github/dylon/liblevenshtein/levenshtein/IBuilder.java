package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Collection;
import java.util.Comparator;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface IBuilder {

  IBuilder dictionary(Collection<String> dictionary);

  IBuilder dictionary(Collection<String> dictionary, boolean isSorted);

  IBuilder algorithm(Algorithm algorithm);

  IBuilder nearestCandidatesComparator(DistanceComparator nearestCandidatesComparator);

  IBuilder caseInsensitiveSort(boolean caseInsensitiveSort);

  IBuilder defaultMaxDistance(int defaultMaxDistance);

  IBuilder strategy(Match strategy);

  IBuilder includeDistance(boolean includeDistance);

  ITransducer build();
}
