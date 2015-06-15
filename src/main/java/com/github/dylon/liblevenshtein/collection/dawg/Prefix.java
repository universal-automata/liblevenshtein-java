package com.github.dylon.liblevenshtein.collection.dawg;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Prefix<DictionaryNode> {
  DictionaryNode node;
  String value;
}
