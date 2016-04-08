package com.github.dylon.liblevenshtein.task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.ST;

public class GenerateSyntasticConfig {

  private GenerateSyntasticConfig() {}

  public static void main(final String... args) throws IOException {
    final String classpath = args[0];
    final Path configPath = Paths.get(args[1]);

    final STGroup group = new STGroupDir("stringtemplate");
    final ST template = group.getInstanceOf("syntastic");

    if (null == template) {
    	throw new IllegalStateException("Cannot find template [stringtemplate/syntastic]");
    }

    template.add("classpath", classpath);

    String configText = template.render();
    if ('\n' != configText.charAt(configText.length() - 1)) {
      configText += "\n"; // make sure it ends with a newline
    }

    Files.write(configPath, configText.getBytes(StandardCharsets.UTF_8));
  }
}
