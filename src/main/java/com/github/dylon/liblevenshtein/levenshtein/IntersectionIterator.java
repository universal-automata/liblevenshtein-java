package com.github.dylon.liblevenshtein.levenshtein;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.AbstractIterator;
import com.github.dylon.liblevenshtein.collection.IFinalFunction;
import com.github.dylon.liblevenshtein.collection.IPrefixFactory;
import com.github.dylon.liblevenshtein.collection.ITransitionFunction;
import com.github.dylon.liblevenshtein.collection.Prefix;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class IntersectionIterator<DictionaryNode>
	extends AbstractIterator<Intersection<DictionaryNode>> {

  Queue<Prefix<DictionaryNode>> prefixes = new ArrayDeque<>();

	/**
	 * Builds and recycles prefix objects, which are used to generate spelling
	 * candidates from the relative root.
	 */
  IPrefixFactory<DictionaryNode> prefixFactory;

  /**
   * Transition function for dictionary nodes.
   */
  ITransitionFunction<DictionaryNode> dictionaryTransition;

  /**
   * Returns whether a dictionary node is the final character in some term.
   */
  IFinalFunction<DictionaryNode> isFinal;

  /**
   * Returns instances of a data structure used for maintaining information
   * regarding each step in intersecting the dictionary automaton with the
   * Levenshtein automaton.
   */
  IIntersectionFactory<DictionaryNode> intersectionFactory;

	/**
	 * Distance corresponding to the root prefix, which should be returned for all
	 * generated strings.
	 */
  int distance;

	/**
	 * Accepting state of the corresponding Levenshtein automaton, of the prefix
	 * denoted by the relative root.
	 */
  int[][] levenshteinState;

  public IntersectionIterator(
  		final IPrefixFactory<DictionaryNode> prefixFactory,
  		final ITransitionFunction<DictionaryNode> dictionaryTransition,
  		final IFinalFunction<DictionaryNode> isFinal,
  		final IIntersectionFactory<DictionaryNode> intersectionFactory,
  		final DictionaryNode relativeRoot,
  		final String prefix,
  		final int distance,
  		final int[][] levenshteinState) {
  	this.prefixFactory = prefixFactory;
  	this.dictionaryTransition = dictionaryTransition;
  	this.isFinal = isFinal;
  	this.intersectionFactory = intersectionFactory;
  	this.distance = distance;
  	this.levenshteinState = levenshteinState;
  	prefixes.offer(prefixFactory.build(relativeRoot, prefix));
  }

  @Override
  protected void advance() {
    if (null == next && !prefixes.isEmpty()) {
      DictionaryNode node = null;
      String candidate = null;

      do {
        final Prefix<DictionaryNode> prefix = prefixes.poll();
        node = prefix.node();
        candidate = prefix.value();
        final CharIterator iter = dictionaryTransition.of(node);
        while (iter.hasNext()) {
          final char label = iter.nextChar();
          final DictionaryNode nextNode = dictionaryTransition.of(node, label);
          final String nextCandidate = candidate + label;
          if (!prefixes.offer(prefixFactory.build(nextNode, nextCandidate))) {
            throw new IllegalStateException(
                "Failed to enqueue prefix candidate: " + nextCandidate);
          }
        }
        prefixFactory.recycle(prefix);
      }
      while (!isFinal.at(node));

      this.next =
      	intersectionFactory.build(
      			candidate,
      			node,
      			levenshteinState,
      			distance);
    }
  }
}
