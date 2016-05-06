package com.github.liblevenshtein.task;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Generates the Wiki for this project.
 */
@Slf4j
public class GenerateWikidoc extends Action {

  /**
   * Constructs a new {@link GenerateWikidoc} from the command-line args.
   * @param args Command-line args for this generator.
   */
  public GenerateWikidoc(final String[] args) {
    super(args);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void runInternal() throws Exception {
    final Path wikidocDir = Paths.get(cli.getOptionValue("wiki-path"));

    final String groupName = "stringtemplate";

    final String[] wikiNames = {
      "index",
      "installation",
      "building",
      "testing",
      "usage",
    };

    for (final String wikiName : wikiNames) {
      final String templateName = String.format("/wiki/%s", wikiName);
      final Path wikidocPath = wikidocDir.resolve(wikiName + ".md");
      renderTemplate(groupName, templateName, wikidocPath);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Options options() {
    final Options options = super.options();
    options.addOption(
      Option.builder()
        .longOpt("wiki-path")
        .desc("Path to the directory containing the wiki files")
        .hasArg()
        .required()
        .build());
    return options;
  }

  /**
   * Generates the Wiki for this project.
   * @param args Command-line arguments.
   */
  public static void main(final String... args)  {
    final GenerateWikidoc action = new GenerateWikidoc(args);
    action.run();
  }
}
