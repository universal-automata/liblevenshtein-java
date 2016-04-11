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
 * Generates the README.md for this project.
 */
@Slf4j
public class GenerateReadme extends Action {

  /**
   * Name of this action.
   */
  @Getter(onMethod = @_(@Override), value = AccessLevel.PROTECTED)
  private final String name = "GenerateReadme";

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
    final String groupId = cli.getOptionValue("group-id");
    final String artifactId = cli.getOptionValue("artifact-id");
    final String artifactVersion = cli.getOptionValue("artifact-version");

    final String gradleVersion = cli.getOptionValue("gradle-version");
    final String javaSourceVersion = cli.getOptionValue("java-source-version");
    final String javaTargetVersion = cli.getOptionValue("java-target-version");

    final Path readmePath = Paths.get(cli.getOptionValue("readme-path"));

    final String groupName = "stringtemplate";
    final String templateName = "README";

    final STGroup group = new STGroupDir(groupName, '$', '$');
    final ST template = group.getInstanceOf(templateName);

    if (null == template) {
      final String message = String.format("Cannot find template [%s/%s]",
        groupName, templateName);
      throw new IllegalStateException(message);
    }

    template.add("groupId", groupId);
    template.add("artifactId", artifactId);
    template.add("version", artifactVersion);
    template.add("gradleVersion", gradleVersion);
    template.add("javaSourceVersion", javaSourceVersion);
    template.add("javaTargetVersion", javaTargetVersion);

    log.info("Rendering template [{}/{}] to [{}]",
      groupName, templateName, readmePath);
    final String readme = template.render() + "\n";
    Files.write(readmePath, readme.getBytes(StandardCharsets.UTF_8));
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
