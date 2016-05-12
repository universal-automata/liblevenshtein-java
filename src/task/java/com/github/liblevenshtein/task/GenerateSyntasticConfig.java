package com.github.liblevenshtein.task;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.stringtemplate.v4.ST;

import lombok.extern.slf4j.Slf4j;

/**
 * Generates the Syntastic config for this project.
 */
@Slf4j
public class GenerateSyntasticConfig extends Action {

  /**
   * "classpath" literal for accessors.
   */
  @SuppressWarnings("checkstyle:multiplestringliterals")
  private static final String CLASSPATH = "classpath";

  /**
   * "config-path" literal for accessors.
   */
  private static final String CONFIG_PATH = "config-path";

  /**
   * Constructs a new {@link GenerateSyntasticConfig} from the command-line args.
   * @param args Command-line args for this generator.
   */
  public GenerateSyntasticConfig(final String[] args) {
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void runInternal() throws Exception {
    final Path configPath = Paths.get(cli.getOptionValue(CONFIG_PATH));
    renderTemplate("stringtemplate", "syntastic", configPath);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("checkstyle:multiplestringliterals")
  protected void initTemplate(final ST template) throws Exception {
    super.initTemplate(template);
    template.add("classpath", cli.getOptionValue(CLASSPATH));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Options options() {
    final Options options = super.options();
    options.addOption(
      Option.builder()
        .longOpt(CLASSPATH)
        .desc("Java classpath of dependencies for src/main, src/task, and src/test")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt(CONFIG_PATH)
        .desc("Path to write the Syntastic config (e.g. \".vim.local\")")
        .hasArg()
        .required()
        .build());
    return options;
  }

  /**
   * Generates the Syntastic config for this project.
   * @param args Command-line arguments.
   */
  @SuppressWarnings("checkstyle:uncommentedmain")
  public static void main(final String... args) {
    final GenerateSyntasticConfig action = new GenerateSyntasticConfig(args);
    action.run();
  }
}
