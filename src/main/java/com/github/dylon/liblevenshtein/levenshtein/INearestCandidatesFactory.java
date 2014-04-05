package com.github.dylon.liblevenshtein.levenshtein;

import it.unimi.dsi.fastutil.PriorityQueue;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface INearestCandidatesFactory<DictionaryNode> {

  PriorityQueue<Intersection<DictionaryNode>> build(String term);

  void recycle(PriorityQueue<Intersection<DictionaryNode>> nearestCandidates);
}
