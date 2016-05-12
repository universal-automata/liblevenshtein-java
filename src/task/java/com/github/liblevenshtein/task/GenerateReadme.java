package com.github.liblevenshtein.task;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import lombok.extern.slf4j.Slf4j;

/**
 * Generates the README.md for this project.
 */
@Slf4j
public class GenerateReadme extends Action {

  /**
   * "readme-path" literal for accessors.
   */
  private static final String README_PATH = "readme-path";

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
    final Path readmePath = Paths.get(cli.getOptionValue(README_PATH));
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
        .longOpt(README_PATH)
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
  @SuppressWarnings("checkstyle:uncommentedmain")
  public static void main(final String... args) {
    final GenerateReadme action = new GenerateReadme(args);
    action.run();
  }
}
