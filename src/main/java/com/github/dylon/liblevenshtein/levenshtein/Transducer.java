package com.github.dylon.liblevenshtein.levenshtein;

import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import it.unimi.dsi.fastutil.PriorityQueue;

/**
 * Transduces on whole, dictionary terms (as compared to prefixes or substrings)
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
public class Transducer<DictionaryNode>
  extends AbstractTransducer<DictionaryNode> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void enqueueAll(
      final PriorityQueue<Intersection<DictionaryNode>> nearestCandidates,
      final ICandidateCollection candidates,
      final String candidate,
      final DictionaryNode dictionaryNode,
      final IState levenshteinState,
      final int distance,
      final int maxDistance) {

    nearestCandidates.enqueue(
        intersectionFactory.build(
          candidate,
          dictionaryNode,
          levenshteinState,
          distance));

    if (isFinal.at(dictionaryNode) && distance <= maxDistance) {
      if (!candidates.offer(candidate, distance)) {
        throw new QueueFullException();
      }
    }
  }
}
