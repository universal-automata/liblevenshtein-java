package com.github.dylon.liblevenshtein.task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.AlreadySelectedException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.ST;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Boilerplate logic for task actions.
 */
@Slf4j
public abstract class Action implements Runnable {

  /**
   * Process exit code for success.
   */
  public static final int EXIT_SUCCESS = 0;

  /**
   * Process exit code for a handled exception.
   */
  public static final int EXIT_ERROR = 1;

  /**
   * Process exit code for an unhandled exception.
   */
  public static final int EXIT_FATAL = 2;

  /**
   * Returns the name of this action.
   * @return Name of this action.
   */
  protected abstract String name();

  /**
   * Header for the help text.
   */
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final String helpHeader = "";

  /**
   * Footer for the help text.
   */
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final String helpFooter = "";

  /**
   * Common, project attributes.
   */
  @Getter(
  	lazy = true,
  	value = AccessLevel.PROTECTED,
  	onMethod = @_(@SuppressWarnings("unchecked")))
  private final Map<String, Object> projectArgs =
  	new ImmutableMap.Builder<String, Object>()
  		.put("maven", new ImmutableMap.Builder<String, Object>()
  			.put("groupId", cli.getOptionValue("group-id"))
  			.put("artifactId", cli.getOptionValue("artifact-id"))
  			.put("version", cli.getOptionValue("version"))
  			.build())
  		.put("gradle", new ImmutableMap.Builder<String, Object>()
  			.put("version", cli.getOptionValue("gradle-version"))
  			.build())
  		.put("java", new ImmutableMap.Builder<String, Object>()
  			.put("sourceVersion", cli.getOptionValue("java-source-version"))
  			.put("targetVersion", cli.getOptionValue("java-target-version"))
  			.build())
  		.build();

  /**
   * Command-line parameters of this action.
   */
  protected final CommandLine cli;

  /**
   * Constructs a new action with the command-line args.
   * @param args Command-lin arguments for this action.
   */
  protected Action(final String[] args) {
    this.cli = parseCLI(args);
  }

  /**
   * Business logic of the action.
   * @throws Exception When an unhandled exception occurs in the action.
   */
  protected abstract void runInternal() throws Exception;

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    try {
      log.info("Executing task [{}]", name());
      runInternal();
      exit(EXIT_SUCCESS, "Finished executing task [%s]", name());
    }
    catch (final IllegalStateException | IllegalArgumentException exception) {
      final String message =
        String.format("Task [%s] execution failed", name());
      log.error(message, exception);
      exit(EXIT_ERROR);
    }
    catch (final Throwable thrown) {
      final String message =
        String.format("Rescued unhandled exception while executing action [%s]",
          name());
      log.error(message, thrown);
      exit(EXIT_FATAL);
    }
  }

	/**
	 * Renders a Stringtemplate to some specified path.
	 * @param groupName Name of the Stringtempalte group containing the template.
	 * @param templateName Name of the template to render.
	 * @param path Path render the template.
	 * @throws IOException When the template cannot be rendered to {@link #path}.
	 */
  protected void renderTemplate(
  		final String groupName,
  		final String templateName,
  		final Path path) throws IOException {

    final STGroup group = new STGroupDir(groupName, '$', '$');
    final ST template = group.getInstanceOf(templateName);

    if (null == template) {
      final String message = String.format("Cannot find template [%s/%s]",
        groupName, templateName);
      throw new IllegalStateException(message);
    }

    log.info("Rendering template [{}/{}] to [{}]",
      groupName, templateName, path);
    final String rendition = template.render() + "\n";
    Files.write(path, rendition.getBytes(StandardCharsets.UTF_8));
  }

	/**
	 * Initializes a template with the project arguments.
	 * @param template Stringtemplate to render.
	 */
  protected void initTemplate(final ST template) {
		template.add("project", projectArgs());
  }

  /**
   * Parses the command-line parameters into options.
   * @param args Command-lien arguments for this action.
   * @return {@link CommandLine} for this action.
   */
  protected CommandLine parseCLI(final String[] args) {
    try {
      log.info("[{}] Parsing command-line args [{}]",
        name(), Joiner.on(", ").join(args));
      final DefaultParser parser = new DefaultParser();
      final CommandLine cli = parser.parse(options(), args);
      if (cli.hasOption("help")) {
        printHelp(EXIT_SUCCESS);
      }
      return cli;
    }
    catch (final AlreadySelectedException exception) {
      final String message =
        String.format("Option specified multiple times [%s]",
          exception.getOption());
      handleParseException(message, exception);
    }
    catch (final MissingArgumentException exception) {
      final String message =
        String.format("Missing argument for option [%s]",
          exception.getOption());
      handleParseException(message, exception);
    }
    catch (final MissingOptionException exception) {
      final String message =
        String.format("The following required options were missing: %s",
          Joiner.on(", ").join(exception.getMissingOptions()));
      handleParseException(message, exception);
    }
    catch (final UnrecognizedOptionException exception) {
      final String message =
        String.format("Uncrecognized option [%s]", exception.getOption());
      handleParseException(message, exception);
    }
    catch (final ParseException exception) {
      final String message =
        String.format("Unexpected exception while parsing options [%s]",
          Joiner.on(", ").join(args));
      handleParseException(message, exception);
    }

    throw new IllegalStateException("Should be unreachable");
  }

  /**
   * Command-line options for this action.
   * @return Command-line options.
   */
  protected Options options() {
    final Options options = new Options();
    options.addOption(
      Option.builder("h")
        .longOpt("help")
        .desc("print this help text")
        .build());
    options.addOption(
      Option.builder()
        .longOpt("group-id")
        .desc("Maven, Group Id")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("artifact-id")
        .desc("Maven, Artifact Id")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("version")
        .desc("Maven, Artifact Version")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("gradle-version")
        .desc("Version of the Gradle, build tool.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("java-source-version")
        .desc("Compatible version of the Java, source code.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("java-target-version")
        .desc("Compatible version of the compiled, Java bytecode.")
        .hasArg()
        .required()
        .build());
    return options;
  }

  /**
   * Gracefully-handles exceptions from parsing the command-line paramters.
   * @param message Error message to give the user.
   * @param exception Exception for the message.
   */
  protected void handleParseException(
      final String message,
      final ParseException exception) {
    log.error(message, exception);
    printHelp(EXIT_ERROR);
  }

  /**
   * Prints the help text for this action and exits with the given code.
   * @param exitCode Exit code specifying the success of this process.
   */
  protected void printHelp(final int exitCode) {
    final HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(name(), helpHeader(), options(), helpFooter(), true);
    exit(exitCode);
  }

  /**
   * Exits this action with the given code.
   * @param exitCode Exit code specifying the success of this process.
   */
  public void exit(final int exitCode) {
    System.exit(exitCode);
  }

  /**
   * Prints a message then exits this action with the given code.
   * @param exitCode Exit code specifying the success of this process.
   * @param format String format for {@link String#format(String, Object...)}.
   * @param args Arguments to {@link String#format(String, Object...)}.
   */
  public void exit(final int exitCode, final String format, final Object... args) {
    final String message = String.format(format, args);
    if (EXIT_SUCCESS == exitCode) {
      log.info(message);
    }
    else {
      log.error(message);
    }
    exit(exitCode);
  }
}
