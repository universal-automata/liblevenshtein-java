package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.Queue;
import java.util.ArrayDeque;

import com.github.dylon.liblevenshtein.levenshtein.Intersection;
import com.github.dylon.liblevenshtein.levenshtein.IState;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class IntersectionFactory<DictionaryNode>
  implements IIntersectionFactory<DictionaryNode> {

  private final Queue<Intersection<DictionaryNode>>
    intersections = new ArrayDeque<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public Intersection<DictionaryNode> build(
      final String candidate,
      final DictionaryNode dictionaryNode,
      final IState levenshteinState) {

    Intersection<DictionaryNode> intersection = intersections.poll();

    if (null == intersection) {
      intersection = new Intersection<DictionaryNode>();
    }

    intersection.candidate(candidate);
    intersection.dictionaryNode(dictionaryNode);
    intersection.levenshteinState(levenshteinState);
    return intersection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recycle(final Intersection<DictionaryNode> intersection) {
    intersection.candidate(null);
    intersection.dictionaryNode(null);
    intersection.levenshteinState(null);
    intersections.offer(intersection);
  }
}
