package com.github.dylon.liblevenshtein.levenshtein;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractTransducerTest {

  protected Collection<String> readLines(final InputStream istream) throws IOException {
    final Reader istreamReader = new InputStreamReader(istream, StandardCharsets.UTF_8);
    final BufferedReader reader = new BufferedReader(istreamReader);

    final List<String> lines = new LinkedList<>();

    String line;
    while (null != (line = reader.readLine())) {
      lines.add(line);
    }

    return lines;
  }
}
