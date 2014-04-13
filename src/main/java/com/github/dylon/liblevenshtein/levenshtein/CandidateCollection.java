package com.github.dylon.liblevenshtein.levenshtein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PROTECTED, makeFinal=true)
public abstract class CandidateCollection<Type>
  implements ICandidateCollection<Type> {

  /**
   * Maximum number of correction candidates that may be added to this
   * ICandidateCollection.
   */
  int maxCandidates;

  /**
   * Correction candidates that have been added to this ICandidateCollection
   */
  Collection<Type> candidates;

  /**
   * Constructs a new instance of ICandidateCollection, with a maximum threshold
   * for the number of correction candidates it will accept.
   * @param maxCandidates Maximum number of correction candidates that may be
   * added to this collection.
   */
  public CandidateCollection(final int maxCandidates) {
    this.maxCandidates = maxCandidates;
    // TODO: Consider specializing the data struction by <Type>, such as using
    // an unsorted Dawg for <Type=String>, etc.
    this.candidates = (maxCandidates < Integer.MAX_VALUE)
      ? new ArrayList<Type>(maxCandidates)
      : new ArrayList<Type>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Type> iterator() {
    return candidates.iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  /**
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class WithDistance extends CandidateCollection<Candidate> {

    /**
      * Constructs a new instance of ICandidateCollection, with a maximum
      * threshold for the number of correction candidates it will accept.
      * @param maxCandidates Maximum number of correction candidates that may be
      * added to this collection.
      */
    public WithDistance(final int maxCandidates) {
      super(maxCandidates);
    }

    /**
      * {@inheritDoc}
      */
    @Override
    public boolean offer(String term, int distance) {
      if (candidates.size() == maxCandidates) {
        return false;
      }

      candidates.add(new Candidate(term, distance));
      return true;
    }
  }

  /**
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class WithoutDistance extends CandidateCollection<String> {

    /**
      * Constructs a new instance of ICandidateCollection, with a maximum
      * threshold for the number of correction candidates it will accept.
      * @param maxCandidates Maximum number of correction candidates that may be
      * added to this collection.
      */
    public WithoutDistance(final int maxCandidates) {
      super(maxCandidates);
    }

    /**
      * {@inheritDoc}
      */
    @Override
    public boolean offer(String term, int distance) {
      if (candidates.size() == maxCandidates) {
        return false;
      }

      candidates.add(term); // NOTE: distance is ignored ...
      return true;
    }
  }
}
