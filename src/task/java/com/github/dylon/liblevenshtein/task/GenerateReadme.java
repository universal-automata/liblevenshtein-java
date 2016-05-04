package com.github.dylon.liblevenshtein.task;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Generates the README.md for this project.
 */
@Slf4j
public class GenerateReadme extends Action {

  /**
   * Constructs a new {@link GenerateReadme} from the command-line args.
   * @param args Command-line args for this generator.
   */
  public GenerateReadme(final String[] args) {
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void runInternal() throws Exception {
    final Path readmePath = Paths.get(cli.getOptionValue("readme-path"));
    renderTemplate("stringtemplate", "readme", readmePath);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Options options() {
    final Options options = super.options();
    options.addOption(
      Option.builder()
        .longOpt("readme-path")
        .desc("Path to write the README.md")
        .hasArg()
        .required()
        .build());
    return options;
  }

  /**
   * Generates the README.md for this project.
   * @param args Command-line arguments.
   */
  public static void main(final String... args) {
    final GenerateReadme action = new GenerateReadme(args);
    action.run();
  }
}
