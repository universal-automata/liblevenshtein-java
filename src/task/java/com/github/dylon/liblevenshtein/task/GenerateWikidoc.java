package com.github.dylon.liblevenshtein.task;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
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
    final String groupId = cli.getOptionValue("group-id");
    final String artifactId = cli.getOptionValue("artifact-id");
    final String artifactVersion = cli.getOptionValue("artifact-version");

    final String gradleVersion = cli.getOptionValue("gradle-version");
    final String javaSourceVersion = cli.getOptionValue("java-source-version");
    final String javaTargetVersion = cli.getOptionValue("java-target-version");

    final Path wikidocDir = Paths.get(cli.getOptionValue("wiki-path"));

    final String groupName = "stringtemplate/wiki/";
    final STGroup group = new STGroupDir(groupName, '$', '$');

    final String[] templateNames = {
      "installation",
      "building",
      "testing",
      "usage",
    };

    for (final String templateName : templateNames) {
      final ST template = group.getInstanceOf(templateName);

      if (null == template) {
        final String message =
          String.format("Cannot find template [%s/%s]", groupName, templateName);
        throw new IllegalStateException(message);
      }

      template.add("groupId", groupId);
      template.add("artifactId", artifactId);
      template.add("version", artifactVersion);
      template.add("gradleVersion", gradleVersion);
      template.add("javaSourceVersion", javaSourceVersion);
      template.add("javaTargetVersion", javaTargetVersion);

      final String wikidoc = template.render() + "\n";
      final Path wikidocPath = wikidocDir.resolve(templateName + ".java.md");
      log.info("Rendering template [{}/{}] to [{}]",
        groupName, templateName, wikidocPath);
      Files.write(wikidocPath, wikidoc.getBytes(StandardCharsets.UTF_8));
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
