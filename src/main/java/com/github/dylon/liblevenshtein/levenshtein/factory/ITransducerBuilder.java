package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.io.Serializable;
import java.util.Collection;

import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;

/**
 * Fluent builder of Levenshtein transducers.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ITransducerBuilder extends Serializable {

  /**
   * Specifies the collection of dictionary terms for the dictionary automaton.
   * This method assumes the collection is not sorted.
   * @param dictionary Collection of dictionary terms to consider when
   * generating spelling candidates.
   * @return This {@link ITransducerBuilder} or an equivalent one, for fluency.
   */
  ITransducerBuilder dictionary(Collection<String> dictionary);

  /**
   * Specifies the collection of dictionary terms for the dictionary automaton.
   * @param dictionary Collection of dictionary terms to consider when
   * generating spelling candidates.
   * @param isSorted Whether the dictionary is sorted.  If it is not sorted then
   * it will probably be sorted.
   * @return This {@link ITransducerBuilder} or an equivalent one, for fluency.
   */
  ITransducerBuilder dictionary(Collection<String> dictionary, boolean isSorted);

  /**
   * Specifies what Levenshtein algorithm to use for the transducer.
   * @param algorithm Levenshtein algorithm to use for the transducer.
   * @return This {@link ITransducerBuilder} or an equivalent one, for fluency.
   */
  ITransducerBuilder algorithm(Algorithm algorithm);

  /**
   * Specifies the default, maximum tolerated error of spelling candidates from
   * the query term.  This value is used if the transducer is called without
   * explicitly specifying the maximum distance.  It default to infinity, which
   * will return every term in the dictionary, so if you plan on using the
   * implicit maximum distance then you should set it here.
   * @param defaultMaxDistance Default, maximum tolerated errors between
   * spelling candidates and the query term.
   * @return This {@link ITransducerBuilder} or an equivalent one, for fluency.
   */
  ITransducerBuilder defaultMaxDistance(int defaultMaxDistance);

  /**
   * Specifies whether the distance between each spelling candidate and the
   * query term should be returned with the spelling candidate.
   * @param includeDistance Whether to include distances with spelling
   * candidates.
   * @return This {@link ITransducerBuilder} or an equivalent one, for fluency.
   */
  ITransducerBuilder includeDistance(boolean includeDistance);

  /**
   * If you only desire a subset of the spelling candidates for a query term,
   * you may specify the number of candidates to return, here.  Note that the
   * candidates are not guaranteed to be ordered such that you would get the
   * nearest candidates in the collection if you limit its size.
   * @param maxCandidates Maximum number of candidates to include in the
   * collections of spelling candidates.
   * @return This {@link ITransducerBuilder} or an equivalent one, for fluency.
   */
  ITransducerBuilder maxCandidates(int maxCandidates);

  /**
   * Builds a Levenshtein transducer according to the parameters set for this
   * {@link ITransducerBuilder}.
   * @param <CandidateType> Implicit type of the spelling candidates generated
   * by the transducer.
   * @return Levenshtein transducer for seeking spelling candidates for query
   * terms (fuzzy searching!).
   */
  <CandidateType> ITransducer<CandidateType> build();
}
