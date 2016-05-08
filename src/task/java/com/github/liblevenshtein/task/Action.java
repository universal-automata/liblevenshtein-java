package com.github.liblevenshtein.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

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
import com.google.common.collect.ImmutableMap;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Boilerplate logic for task actions.
 */
@Slf4j
@SuppressWarnings("unchecked")
public abstract class Action implements Runnable {

  /**
   * Replaces the trailing output of a truncated command.
   */
  private static final String TRUNCATED = "# ... TRUNCATED ...";

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
   * Common, project attributes.
   */
  @Getter(
    lazy = true,
    value = AccessLevel.PROTECTED)
  private final Map<String, Object> projectArgs =
    new ImmutableMap.Builder<String, Object>()
      .put("meta", new ImmutableMap.Builder<String, Object>()
        .put("url", cli.getOptionValue("project-url"))
        .put("author", cli.getOptionValue("project-author"))
        .put("username", cli.getOptionValue("author-username"))
        .put("email", cli.getOptionValue("author-email"))
        .build())
      .put("github", new ImmutableMap.Builder<String, Object>()
        .put("org", cli.getOptionValue("github-org"))
        .put("repo", cli.getOptionValue("github-repo"))
        .put("uri", cli.getOptionValue("git-uri"))
        .put("demo", cli.getOptionValue("demo-url"))
        .build())
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
        .put("version",
          cli.getOptionValue("java-target-version").replaceFirst("^1\\.", ""))
        .build())
      .build();

  /**
   * Mapping of commands to their outputs.
   * -- GETTER --
   * Mapping of commands to their outputs.
   * @return Mapping of commands to their outputs.
   */
  @Getter(
    lazy = true,
    value = AccessLevel.PROTECTED)
  private final Map<String, Map.Entry<String, String>> cmdArgs = buildCmdArgs();

  /**
   * Command-line parameters of this action.
   */
  protected final CommandLine cli;

  /**
   * Constructs a new action with the command-line args.
   * @param args Command-line arguments for this action.
   */
  protected Action(final String[] args) {
    this.cli = parseCLI(args);
  }

  /**
   * Returns the name of this action.
   * @return Name of this action.
   */
  protected String name() {
    return getClass().getSimpleName();
  }

  /**
   * Header for the help text.
   * @return Header for the help text.
   */
  protected String helpHeader() {
    return "";
  }

  /**
   * Footer for the help text.
   * @return Footer for the help text.
   */
  protected String helpFooter() {
    return "";
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
   * Executes a command in the given directory.
   * @param maxLines Maximum number of lines to keep from the command
   * @param dir Where to execute the command
   * @param cmd Command to execute
   * @return Output of the command, with STDOUT and STDERR combined.
   * @throws IOException When the output of the command cannot be read.
   * @throws InterruptedException When interrupted while waiting for the command
   * to finish executing.
   */
  protected Map.Entry<String, String> exec(
      final int maxLines,
      final Path dir,
      final String... cmd) throws IOException, InterruptedException {

    log.info("Executing cmd [{}] in dir [{}]", Joiner.on(" ").join(cmd), dir);

    final Process proc = new ProcessBuilder(cmd)
      .redirectErrorStream(true) // redirect STDERR into STDOUT
      .directory(dir.toFile())
      .start();

    final StringBuilder buffer = new StringBuilder();
    int numLines = 0;

    try (final BufferedReader reader =
        new BufferedReader(
          new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8))) {

      final StringBuilder line = new StringBuilder();

      while (numLines <= maxLines && proc.isAlive()) {
        for (int c = reader.read(); numLines <= maxLines && -1 != c; c = reader.read()) {
          if ('\r' == c) {
            line.setLength(0);
          }
          else {
            if ('\n' == c) {
              numLines += 1;
              if (numLines > maxLines) {
                log.info(TRUNCATED);
                buffer.append(TRUNCATED).append('\n');
                log.info("Exceeded the maximum number of lines [{}], terminating early ...",
                  maxLines);
                proc.destroyForcibly();
                break;
              }

              log.info(line.toString());
              buffer.append(line).append('\n');
              line.setLength(0);
            }
            else {
              line.append((char) c);
            }
          }
        }
      }

      if (numLines <= maxLines) {
        for (int c = reader.read(); numLines <= maxLines && -1 != c; c = reader.read()) {
          if ('\r' == c) {
            line.setLength(0);
          }
          else {
            if ('\n' == c) {
              numLines += 1;
              if (numLines > maxLines) {
                log.info(TRUNCATED);
                buffer.append(TRUNCATED).append('\n');
                log.info("Exceeded the maximum number of lines [{}], terminating early ...",
                  maxLines);
                proc.destroyForcibly();
                break;
              }

              log.info(line.toString());
              buffer.append(line).append('\n');
              line.setLength(0);
            }
            else {
              line.append((char) c);
            }
          }
        }

        if (0 != line.length()) {
          if (numLines < maxLines) {
            log.info(line.toString());
            buffer.append(line);
          }
          else {
            buffer.append("...\n");
            log.info("Exceeded the maximum number of lines [{}]",
              maxLines);
          }
        }
      }
    }

    while (!proc.waitFor(5L, TimeUnit.SECONDS)) {
      log.info("Waiting for command to terminate ...");
    }

    if (numLines <= maxLines && 0 != proc.exitValue()) {
      exit(EXIT_ERROR, "%s\ncommand [%s] exited with status [%d] in dir [%s]",
        buffer.toString(), Joiner.on(" ").join(cmd), proc.exitValue(), dir);
    }

    final String command = Joiner.on(" ").join(cmd);
    final String output = buffer.toString();

    if (output.isEmpty()) {
      return new AbstractMap.SimpleImmutableEntry<>(command, null);
    }

    return new AbstractMap.SimpleImmutableEntry<>(command, output);
  }

  /**
   * Executes a command in the given directory.
   * @param dir Where to execute the command
   * @param cmd Command to execute
   * @return Output of the command, with STDOUT and STDERR combined.
   * @throws IOException When the output of the command cannot be read.
   * @throws InterruptedException When interrupted while waiting for the command
   * to finish executing.
   */
  protected Map.Entry<String, String> exec(final Path dir, final String... cmd)
      throws IOException, InterruptedException {
    return exec(Integer.MAX_VALUE, dir, cmd);
  }

  /**
   * Convenience method that executes the command in a clean, temporary
   * directory.
   * @param cmd Command to execute.
   * @return Output from the command, with STDOUT and STDERR combined.
   * @throws IOException When the command cannot be executed.
   * @throws InterruptedException When the thread executing the command is
   * interrupted while the command is executing.
   */
  protected Map.Entry<String, String> exec(final String... cmd)
      throws IOException, InterruptedException {
    return exec(tmpDir(), cmd);
  }

  /**
   * Creates a temporary directory.
   * @param deleteOnExit Whether to delete the file and all its contents when
   * the JVM terminates (normally).
   * @return New, temporary directory.
   * @throws IOException When the temporary directory cannot be created.
   */
  protected Path tmpDir(final boolean deleteOnExit) throws IOException {
    final Path tmpDir = Files.createTempDirectory(String.format("%s-", name()));
    if (deleteOnExit) {
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override public void run() {
          try {
            if (Files.exists(tmpDir)) {
              log.info("Deleting tmpDir [{}]", tmpDir);
              FileUtils.deleteDirectory(tmpDir.toFile());
            }
          }
          catch (final Throwable thrown) {
            log.error("Failed to delete tmpDir [{}]", tmpDir, thrown);
          }
        }
      });
    }
    return tmpDir;
  }

  /**
   * Convenience method that creates a temporary directory, and deletes it when
   * the JVM exits.
   * @return New, temporary directory.
   * @throws IOException When the temporary directory cannot be created.
   */
  protected Path tmpDir() throws IOException {
    return tmpDir(true);
  }

  /**
   * Renders a Stringtemplate to some specified path.
   * @param groupName Name of the Stringtempalte group containing the template.
   * @param templateName Name of the template to render.
   * @param path Path render the template.
   * @throws IOException When the template cannot be rendered to {@link #path}.
   */
  @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  protected void renderTemplate(
      final String groupName,
      final String templateName,
      final Path path) throws IOException {

    STGroup.verbose = true; // debugging
    final STGroup group = new STGroupDir(groupName, '$', '$');
    final ST template = group.getInstanceOf(templateName);

    if (null == template) {
      final String message = String.format("Cannot find template [%s/%s]",
        groupName, templateName);
      throw new IllegalStateException(message);
    }

    try {
      initTemplate(template);
      log.info("Rendering template [{}/{}] to [{}]",
        groupName, templateName, path);
      final String rendition = template.render() + "\n";
      Files.write(path, rendition.getBytes(StandardCharsets.UTF_8));
    }
    catch (final Exception exception) {
      final String message =
        String.format("Failed to render template [%s/%s] to [%s]",
          groupName, templateName, path);
      throw new IOException(message, exception);
    }
  }

  /**
   * Returns map of commands to their outputs.
   * @return Map of commands to their outputs.
   */
  @SneakyThrows({IOException.class, InterruptedException.class})
  private Map<String, Map.Entry<String, String>> buildCmdArgs() {
    final String githubRepo = cli.getOptionValue("github-repo");
    final String gitUri = cli.getOptionValue("git-uri");
    final String gitBranch = cli.getOptionValue("git-branch");
    final Path workingDir = tmpDir();
    final Path projDir = workingDir.resolve(githubRepo);
    return new ImmutableMap.Builder<String, Map.Entry<String, String>>()
      .put("gitCloneProj", exec(workingDir, "git", "clone", "--progress", gitUri))
      .put("cdProj", new AbstractMap.SimpleImmutableEntry<>(
        String.format("cd %s", githubRepo),
        null))
      .put("gitPull", exec(projDir, "git", "pull", "--progress"))
      .put("gitFetchTags", exec(projDir, "git", "fetch", "--progress", "--tags"))
      .put("gitCheckout", exec(projDir, "git", "checkout", "--progress", gitBranch))
      .put("gitSubmoduleInit", exec(projDir, "git", "submodule", "init"))
      .put("gitSubmoduleUpdate", exec(projDir, "git", "submodule", "update"))
      .put("gradleJar", exec(projDir, "gradle", "jar"))
      .put("treeBuildLibs", exec(projDir, "tree", "build/libs"))
      .put("gradleTest", exec(50, projDir, "gradle", "test"))
      .put("gradleCheck", exec(50, projDir, "gradle", "clean", "check"))
      .build();
  }

  /**
   * Initializes a template with the project arguments.
   * @param template Stringtemplate to render.
   * @throws Exception When the template cannot be initialized.
   */
  protected void initTemplate(final ST template) throws Exception {
    template.add("project", projectArgs());
    template.add("cmd", cmdArgs());
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
    options.addOption(
      Option.builder()
        .longOpt("github-org")
        .desc("Name of the corresponding, Github organization.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("github-repo")
        .desc("Name of the corresponding, Github repository.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("git-uri")
        .desc("URI for developers to clone the corresponding, git repository.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("git-branch")
        .desc("Git branch associated with the target source.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("project-url")
        .desc("URL for the project on Github.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("demo-url")
        .desc("URL for the demo on Github.")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("project-author")
        .desc("Name of the author of the project (i.e. my name).")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("author-username")
        .desc("Username of the author of the project (i.e. my username).")
        .hasArg()
        .required()
        .build());
    options.addOption(
      Option.builder()
        .longOpt("author-email")
        .desc("Email of the author of the project (i.e. my email).")
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
