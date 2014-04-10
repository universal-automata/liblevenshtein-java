package com.github.dylon.liblevenshtein.levenshtein;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent=true)
public class Element<Type> {
  Element<Type> prev;
  Element<Type> next;
  Type value;
}
