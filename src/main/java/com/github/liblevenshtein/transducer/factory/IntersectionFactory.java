package com.github.liblevenshtein.transducer.factory;

import java.io.Serializable;

import lombok.val;

import com.github.liblevenshtein.transducer.IState;
import com.github.liblevenshtein.transducer.Intersection;

/**
 * Builds intersections between a dictionary automaton and a Levenshtein
 * automaton.
 * @param <DictionaryNode> Kind of nodes in the dictionary automaton.
 * @author Dylon Edwards
 * @since 2.1.0
 */
public class IntersectionFactory<DictionaryNode>
  implements IIntersectionFactory<DictionaryNode>, Serializable {

  private static final long serialVersionUID = 1L;

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
