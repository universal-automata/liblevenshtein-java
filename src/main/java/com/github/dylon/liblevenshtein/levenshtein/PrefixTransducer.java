package com.github.dylon.liblevenshtein.levenshtein;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.val;

import it.unimi.dsi.fastutil.PriorityQueue;

import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;

/**
 * Transduces on prefixes of dictionary terms.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class PrefixTransducer<DictionaryNode>
  extends AbstractTransducer<DictionaryNode> {

  /**
   * Builds and recycles prefix objects, which are used to generate spelling
   * candidates from some relative root of the dictionary automaton.
   */
  IPrefixFactory<DictionaryNode> prefixFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void enqueueAll(
      final PriorityQueue<Intersection<DictionaryNode>> nearestCandidates,
      final ICandidateCollection<DictionaryNode> candidates,
      final String candidate,
      final DictionaryNode dictionaryNode,
      final int[][] levenshteinState,
      final int distance,
      final int maxDistance) {

    if (distance <= maxDistance) {
      if (isFinal.at(dictionaryNode)) {
        if (!candidates.offer(candidate, distance)) {
          throw new QueueFullException();
        }
      }
      else {
        val intersections = new IntersectionIterator<DictionaryNode>(
            prefixFactory,
            dictionaryTransition,
            isFinal,
            intersectionFactory,
            dictionaryNode,
            candidate,
            distance,
            levenshteinState);

        while (intersections.hasNext()) {
          // Enqueue all the generated, spelling candiates so they may be ranked
          // according to nearestCandidates' comparator before adding them to
          // the collection of spelling candidates.
          nearestCandidates.enqueue(intersections.next());
        }
      }
    }
    else {
      nearestCandidates.enqueue(
          intersectionFactory.build(
            candidate,
            dictionaryNode,
            levenshteinState,
            distance));
    }
  }
}
