package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import com.github.liblevenshtein.collection.dictionary.Dawg;
import com.github.liblevenshtein.collection.dictionary.IFinalFunction;
import com.github.liblevenshtein.collection.dictionary.ITransitionFunction;
import com.github.liblevenshtein.transducer.factory.CandidateFactory;
import com.github.liblevenshtein.transducer.factory.StateTransitionFactory;

/**
 * Attributes required for this transducer to search the dictionary.
 * @author Dylon Edwards
 * @since 2.1.2
 * @param <DictionaryNode> Kind of nodes of the dictionary automaton.
 * @param <CandidateType> Kind of the spelling candidates returned from the
 */
@Getter
@Builder
@ToString(of = {
  "maxDistance",
  "dictionary",
  "algorithm",
  "includeDistance"})
@EqualsAndHashCode(of = {
  "maxDistance",
  "dictionary",
  "algorithm",
  "includeDistance"})
public class TransducerAttributes<DictionaryNode, CandidateType> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Maximum number of spelling errors candidates may have from the query term.
   * -- GETTER --
   * Maximum number of spelling errors candidates may have from the query term.
   * @return Maximum number of spelling errors candidates may have from the
   * query term.
   */
  protected int maxDistance = Integer.MAX_VALUE;

  /**
   * Generates spelling candidates of the requested type. The candidates  which
   * may optionally include the Levenshtein distance between their dictionary
   * terms and the query term.
   * -- GETTER --
   * Generates spelling candidates of the requested type. The candidates  which
   * may optionally include the Levenshtein distance between their dictionary
   * terms and the query term.
   * @return Generator of spelling candidates of the requested type.
   */
  @NonNull
  protected CandidateFactory<CandidateType> candidateFactory;

  /**
   * Returns state-transition functions for specific, max edit distances.
   * -- GETTER --
   * Returns state-transition functions for specific, max edit distances.
   * @return Returns state-transition functions for specific, max edit distances.
   */
  @NonNull
  protected StateTransitionFactory stateTransitionFactory;

  /**
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   * -- GETTER --
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   * @return Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   */
  @NonNull
  protected DistanceFunction minDistance;

  /**
   * Returns whether a dictionary node is the final character in some term.
   * -- GETTER --
   * Returns whether a dictionary node is the final character in some term.
   * @return Returns whether a dictionary node is the final character in some
   * term.
   */
  @NonNull
  protected IFinalFunction<DictionaryNode> isFinal;

  /**
   * Transition function for dictionary nodes.
   * -- GETTER --
   * Transition function for dictionary nodes.
   * @return Transition function for dictionary nodes.
   */
  @NonNull
  protected ITransitionFunction<DictionaryNode> dictionaryTransition;

  /**
   * State at which to begin traversing the Levenshtein automaton.
   * -- GETTER --
   * State at which to begin traversing the Levenshtein automaton.
   * @return State at which to begin traversing the Levenshtein automaton.
   */
  @NonNull
  protected State initialState;

  /**
   * Root node of the dictionary, at which to begin searching for spelling
   * candidates.
   * -- GETTER --
   * @return Root node of the dictionary, at which to begin searching for
   * spelling candidates.
   */
  @NonNull
  protected DictionaryNode dictionaryRoot;

  /**
   * Dictionary of this transducer.
   * -- GETTER --
   * Dictionary of this transducer.
   * @return Dictionary of this transducer.
   */
  @NonNull
  protected Dawg dictionary;

  /**
   * Transduction algorithm.
   * -- GETTER --
   * Transduction algorithm.
   * @return Transduction algorithm.
   */
  @NonNull
  protected Algorithm algorithm;

  /**
   * Whether to include the number of errors from the query term with the
   * candidate terms.
   * -- GETTER --
   * Whether to include the number of errors from the query term with the
   * candidate terms.
   * @return Whether to include the number of errors from the query term with
   * the candidate terms.
   */
  protected boolean includeDistance;
}
