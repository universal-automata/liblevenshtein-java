package com.github.dylon.liblevenshtein.levenshtein;

import java.util.Queue;
import java.util.ArrayDeque;

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
      final IState levenshteinState,
      final int distance) {

    Intersection<DictionaryNode> intersection = intersections.poll();

    if (null == intersection) {
      intersection = new Intersection<DictionaryNode>();
    }

    intersection.candidate(candidate);
    intersection.dictionaryNode(dictionaryNode);
    intersection.levenshteinState(levenshteinState);
    intersection.distance(distance);
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
    //intersection.distance(0); // primitive type: leave alone
    intersections.offer(intersection);
  }
}
