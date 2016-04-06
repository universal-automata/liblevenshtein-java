package com.github.dylon.liblevenshtein.levenshtein;

import java.io.Serializable;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * This wrapper around {@link LazyTransducerCollection}, which handles all the
 * heavy lifting.
 *
 * @param <DictionaryNode> Kind of nodes of the dictionary automaton.
 * @param <CandidateType> Kind of the spelling candidates returned from the
 * dictionary.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@RequiredArgsConstructor
public class Transducer<DictionaryNode, CandidateType>
  implements ITransducer<CandidateType>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Attributes required for this transducer to search the dictionary.
   * -- SETTER --
   * Attributes required for this transducer to search the dictionary.
   * @param attributes Attributes required for this transducer to search the
   * dictionary.
   * @return This {@link Transducer} for fluency.
   */
  @NonNull TransducerAttributes<DictionaryNode,CandidateType> attributes;

  /**
   * {@inheritDoc}
   */
  @Override
  public ICandidateCollection<CandidateType> transduce(@NonNull final String term) {
    return transduce(term, attributes.maxDistance());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICandidateCollection<CandidateType> transduce(
      @NonNull final String term,
      final int maxDistance) {
    return new LazyTransducerCollection<DictionaryNode,CandidateType>(
        term, maxDistance, attributes);
  }
}
