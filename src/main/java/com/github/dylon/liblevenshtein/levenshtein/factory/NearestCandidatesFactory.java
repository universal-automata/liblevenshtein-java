package com.github.dylon.liblevenshtein.levenshtein.factory;

import java.util.ArrayDeque;
import java.util.Queue;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectHeapPriorityQueue;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.DistanceComparator;
import com.github.dylon.liblevenshtein.levenshtein.IMergeFunction;
import com.github.dylon.liblevenshtein.levenshtein.Intersection;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class NearestCandidatesFactory<DictionaryNode>
  implements INearestCandidatesFactory<DictionaryNode> {

  /**
   * Ranking function for intersection nodes corresponding to correction
   * candidates.
   */
  @Setter @NonNull DistanceComparator comparator;

  /**
   * Ranks intersection nodes corresponding to correction candidates.
   */
  Queue<PriorityQueue<Intersection<DictionaryNode>>>
    nearestCandidates = new ArrayDeque<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public PriorityQueue<Intersection<DictionaryNode>> build(
      @NonNull final String term) {

    PriorityQueue<Intersection<DictionaryNode>>
      nearestCandidates = this.nearestCandidates.poll();

    if (null == nearestCandidates) {
      nearestCandidates =
        new ObjectHeapPriorityQueue<Intersection<DictionaryNode>>(
            comparator.build());
    }

    ((DistanceComparator) nearestCandidates.comparator()).term(term);
    return nearestCandidates;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recycle(
      @NonNull final PriorityQueue<Intersection<DictionaryNode>> nearestCandidates) {
    nearestCandidates.clear();
    this.nearestCandidates.offer(nearestCandidates);
  }
}
