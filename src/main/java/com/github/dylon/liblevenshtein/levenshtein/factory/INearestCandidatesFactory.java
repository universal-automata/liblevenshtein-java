package com.github.dylon.liblevenshtein.levenshtein.factory;

import it.unimi.dsi.fastutil.PriorityQueue;

import com.github.dylon.liblevenshtein.levenshtein.Intersection;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public interface INearestCandidatesFactory<DictionaryNode> {

  PriorityQueue<Intersection<DictionaryNode>> build(String term);

  void recycle(PriorityQueue<Intersection<DictionaryNode>> nearestCandidates);
}
