package com.github.dylon.liblevenshtein.task;

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

import com.google.common.base.Joiner;

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
        .longOpt("artifact-version")
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
