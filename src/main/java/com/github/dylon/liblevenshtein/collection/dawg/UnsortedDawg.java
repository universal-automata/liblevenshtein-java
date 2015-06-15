package com.github.dylon.liblevenshtein.collection.dawg;

import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.chars.CharIterator;

import com.github.dylon.liblevenshtein.collection.dawg.factory.IDawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.IPrefixFactory;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class UnsortedDawg extends AbstractDawg {

  public UnsortedDawg(
      @NonNull final IPrefixFactory<DawgNode> prefixFactory,
      @NonNull final IDawgNodeFactory<DawgNode> factory) {
    super(prefixFactory, factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(@NonNull final String term) {
    throw new UnsupportedOperationException(
        "add(String) is not currently supported");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(final Object object) {
    throw new UnsupportedOperationException(
        "remove(Object) is not currently supported");
  }
}
