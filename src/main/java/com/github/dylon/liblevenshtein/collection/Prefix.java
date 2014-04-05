package com.github.dylon.liblevenshtein.collection;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Prefix<DictionaryNode> {
  DictionaryNode node;
  String value;
}
