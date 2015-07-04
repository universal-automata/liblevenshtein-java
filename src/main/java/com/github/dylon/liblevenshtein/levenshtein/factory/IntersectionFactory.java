package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.Queue;
import java.util.ArrayDeque;

import lombok.val;

import com.github.dylon.liblevenshtein.levenshtein.Intersection;
import com.github.dylon.liblevenshtein.levenshtein.IState;

/**
 * Builds intersections between a dictionary automaton and a Levenshtein
 * automaton.
 * @param <DictionaryNode> Kind of nodes in the dictionary automaton.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class IntersectionFactory<DictionaryNode>
  implements IIntersectionFactory<DictionaryNode> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Intersection<DictionaryNode> build(
      final String candidate,
      final DictionaryNode dictionaryNode,
      final IState levenshteinState) {

    val intersection = new Intersection<DictionaryNode>();
    intersection.candidate(candidate);
    intersection.dictionaryNode(dictionaryNode);
    intersection.levenshteinState(levenshteinState);
    return intersection;
  }
}
