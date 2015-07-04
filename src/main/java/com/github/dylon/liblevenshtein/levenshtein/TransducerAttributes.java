package com.github.dylon.liblevenshtein.levenshtein;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.collection.dawg.IFinalFunction;
import com.github.dylon.liblevenshtein.collection.dawg.ITransitionFunction;
import com.github.dylon.liblevenshtein.levenshtein.factory.ICandidateFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IIntersectionFactory;
import com.github.dylon.liblevenshtein.levenshtein.factory.IStateTransitionFactory;

/**
 * Attributes required for this transducer to search the dictionary.
 * @author Dylon Edwards
 * @since 2.1.2
 * @param <DictionaryNode> Kind of nodes of the dictionary automaton.
 * @param <CandidateType> Kind of the spelling candidates returned from the
 */
@Data
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PROTECTED)
public class TransducerAttributes<DictionaryNode, CandidateType> {

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
  int maxDistance = Integer.MAX_VALUE;

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
  @NonNull ICandidateFactory<CandidateType> candidateFactory;

  /**
   * Returns state-transition functions for specific, max edit distances
   * -- GETTER --
   * Returns state-transition functions for specific, max edit distances
   * @return Returns state-transition functions for specific, max edit distances
   * -- SETTER --
   * Returns state-transition functions for specific, max edit distances
   * @param stateTransitionFactory Returns state-transition functions for
   * specific, max edit distances
   * @return This {@link TransducerAttributes} for fluency.
   */
  @NonNull IStateTransitionFactory stateTransitionFactory;

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
  @NonNull IIntersectionFactory<DictionaryNode> intersectionFactory;

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
  @NonNull IDistanceFunction minDistance;

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
  @NonNull IFinalFunction<DictionaryNode> isFinal;

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
  @NonNull ITransitionFunction<DictionaryNode> dictionaryTransition;

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
  @NonNull IState initialState;

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
  @NonNull DictionaryNode dictionaryRoot;
}
