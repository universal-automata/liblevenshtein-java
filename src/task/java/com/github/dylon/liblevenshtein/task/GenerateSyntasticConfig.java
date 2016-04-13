package com.github.dylon.liblevenshtein.task;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.stringtemplate.v4.ST;

/**
 * Generates the Syntastic config for this project.
 */
@Slf4j
public class GenerateSyntasticConfig extends Action {

  /**
   * Name of this action.
   */
  @Getter(onMethod = @_(@Override), value = AccessLevel.PROTECTED)
  private final String name = "GenerateSyntasticConfig";

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
    final Path configPath = Paths.get(cli.getOptionValue("config-path"));
    renderTemplate("stringtemplate", "syntastic", configPath);
  }

	/**
	 * {@inheritDoc}
	 */
  @Override
  protected void initTemplate(final ST template) {
    template.add("classpath", cli.getOptionValue("classpath"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Options options() {
    final Options options = super.options();
    options.addOption(
      Option.builder()
        .longOpt("classpath")
        .desc("Java classpath of dependencies for src/main, src/task, and src/test")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("config-path")
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
  public static void main(final String... args) {
    final GenerateSyntasticConfig action = new GenerateSyntasticConfig(args);
    action.run();
  }
}
