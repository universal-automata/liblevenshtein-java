package com.github.liblevenshtein.transducer;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import com.github.liblevenshtein.collection.dictionary.Dawg;
import com.github.liblevenshtein.collection.dictionary.IFinalFunction;
import com.github.liblevenshtein.collection.dictionary.ITransitionFunction;
import com.github.liblevenshtein.transducer.factory.ICandidateFactory;
import com.github.liblevenshtein.transducer.factory.IIntersectionFactory;
import com.github.liblevenshtein.transducer.factory.IStateTransitionFactory;

/**
 * Attributes required for this transducer to search the dictionary.
 * @author Dylon Edwards
 * @since 2.1.2
 * @param <DictionaryNode> Kind of nodes of the dictionary automaton.
 * @param <CandidateType> Kind of the spelling candidates returned from the
 */
@Data
@NoArgsConstructor
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
   * -- SETTER --
   * @param maxDistance Maximum number of spelling errors candidates may have
   * from the query term.
   * @return This {@link TransducerAttributes} for fluency.
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
   * -- SETTER --
   * Generates spelling candidates of the requested type. The candidates  which
   * may optionally include the Levenshtein distance between their dictionary
   * terms and the query term.
   * @param candidateFactory Generates spelling candidates of the requested type.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected ICandidateFactory<CandidateType> candidateFactory;

  /**
   * Returns state-transition functions for specific, max edit distances.
   * -- GETTER --
   * Returns state-transition functions for specific, max edit distances.
   * @return Returns state-transition functions for specific, max edit distances.
   * -- SETTER --
   * Returns state-transition functions for specific, max edit distances.
   * @param stateTransitionFactory Returns state-transition functions for
   * specific, max edit distances.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected IStateTransitionFactory stateTransitionFactory;

  /**
   * Returns instances of a data structure used for maintaining information
   * regarding each step in intersecting the dictionary automaton with the
   * Levenshtein automaton.
   * -- GETTER --
   * Returns instances of a data structure used for maintaining information
   * regarding each step in intersecting the dictionary automaton with the
   * Levenshtein automaton.
   * @return Returns instances of a data structure used for maintaining
   * information regarding each step in intersecting the dictionary automaton
   * with the Levenshtein automaton.
   * -- SETTER --
   * Returns instances of a data structure used for maintaining information
   * regarding each step in intersecting the dictionary automaton with the
   * Levenshtein automaton.
   * @param intersectionFactory Returns instances of a data structure used for
   * maintaining information regarding each step in intersecting the dictionary
   * automaton with the Levenshtein automaton.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected IIntersectionFactory<DictionaryNode> intersectionFactory;

  /**
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   * -- GETTER --
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   * @return Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   * -- SETTER --
   * Determines the minimum distance at which a Levenshtein state may be
   * considered from the query term, based on its length.
   * @param minDistance Determines the minimum distance at which a Levenshtein
   * state may be considered from the query term, based on its length.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected IDistanceFunction minDistance;

  /**
   * Returns whether a dictionary node is the final character in some term.
   * -- GETTER --
   * Returns whether a dictionary node is the final character in some term.
   * @return Returns whether a dictionary node is the final character in some
   * term.
   * -- SETTER --
   * Returns whether a dictionary node is the final character in some term.
   * @param isFinal Returns whether a dictionary node is the final character in
   * some term.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected IFinalFunction<DictionaryNode> isFinal;

  /**
   * Transition function for dictionary nodes.
   * -- GETTER --
   * Transition function for dictionary nodes.
   * @return Transition function for dictionary nodes.
   * -- SETTER --
   * Transition function for dictionary nodes.
   * @param dictionaryTransition Transition function for dictionary nodes.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected ITransitionFunction<DictionaryNode> dictionaryTransition;

  /**
   * State at which to begin traversing the Levenshtein automaton.
   * -- GETTER --
   * State at which to begin traversing the Levenshtein automaton.
   * @return State at which to begin traversing the Levenshtein automaton.
   * -- SETTER --
   * State at which to begin traversing the Levenshtein automaton.
   * @param initialState State at which to begin traversing the Levenshtein
   * automaton.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected IState initialState;

  /**
   * Root node of the dictionary, at which to begin searching for spelling
   * candidates.
   * -- GETTER --
   * @return Root node of the dictionary, at which to begin searching for
   * spelling candidates.
   * -- SETTER --
   * Root node of the dictionary, at which to begin searching for spelling
   * candidates.
   * @param dictionaryRoot Root node of the dictionary, at which to begin
   * searching for spelling candidates.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected DictionaryNode dictionaryRoot;

  /**
   * Dictionary of this transducer.
   * -- GETTER --
   * Dictionary of this transducer.
   * @return Dictionary of this transducer.
   * -- SETTER --
   * Dictionary of this transducer.
   * @param dictionary Dictionary of this transducer.
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull
  protected Dawg dictionary;

  /**
   * Transduction algorithm.
   * -- GETTER --
   * Transduction algorithm.
   * @return Transduction algorithm.
   * -- SETTER --
   * Transduction algorithm.
   * @param algorithm Transduction algorithm.
   * @return This {@link TransducerAttributes} for fluency.
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
   * -- SETTER --
   * Whether to include the number of errors from the query term with the
   * candidate terms.
   * @param includeDistance Whether to include the number of errors from the
   * query term with the candidate terms.
   * @return This {@link TransducerAttributes} for fluency.
   */
  protected boolean includeDistance;
}
