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
 * Generates the Wiki for this project.
 */
@Slf4j
public class GenerateWikidoc extends Action {

  /**
   * Name of this action.
   */
  @Getter(onMethod = @_(@Override), value = AccessLevel.PROTECTED)
  private final String name = "GenerateWikidoc";

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

    final String groupName = "stringtemplate/wiki/";

    final String[] templateNames = {
      "installation",
      "building",
      "testing",
      "usage",
    };

    for (final String templateName : templateNames) {
      final Path wikidocPath = wikidocDir.resolve(templateName + ".java.md");
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
