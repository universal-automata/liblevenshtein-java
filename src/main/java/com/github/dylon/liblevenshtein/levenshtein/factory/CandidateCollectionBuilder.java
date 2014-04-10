package com.github.dylon.liblevenshtein.levenshtein.factory;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
import com.github.dylon.liblevenshtein.levenshtein.ICandidateCollection;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class CandidateCollectionBuilder<Type>
  implements ICandidateCollectionBuilder<Type> {

  @Setter(onMethod=@_({@Override}))
  int maxCandidates;

  /**
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class WithDistance extends CandidateCollectionBuilder<Candidate> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ICandidateCollection<Candidate> build() {
      return new CandidateCollection.WithDistance(maxCandidates);
    }
  }

  /**
    * @author Dylon Edwards
    * @since 2.1.0
    */
  public static class WithoutDistance extends CandidateCollectionBuilder<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ICandidateCollection<String> build() {
      return new CandidateCollection.WithoutDistance(maxCandidates);
    }
  }
}
