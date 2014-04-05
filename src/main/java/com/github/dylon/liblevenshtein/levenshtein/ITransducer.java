package com.github.dylon.liblevenshtein.levenshtein;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface ITransducer<DictionaryNode> {

  /**
   * Finds all terms in the dictionary that are fewer than n units of spelling
   * errors away from the query term.
   * @param term Query term whose spelling candidates should bee determined
   * @return A data structure consisting of all the spelling candidates for the
   * query term.
   */
  ICandidateCollection<DictionaryNode> transduce(String term);

  /**
   * Finds all terms in the dictionary that are fewer than n units of spelling
   * errors away from the query term.
   * @param term Query term whose spelling candidates should bee determined
   * @param maxDistance Maximum number of spelling errors the spelling
   * candidates may have from the query term.
   * @return A data structure consisting of all the spelling candidates for the
   * query term.
   */
  ICandidateCollection<DictionaryNode> transduce(String term, int maxDistance);
}
