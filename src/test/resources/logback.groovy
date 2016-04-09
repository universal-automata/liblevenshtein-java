// =============================================================================
// [ONTE] :: This must be at the root of the Jar for Logback to pick it up
// =============================================================================

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.OFF

appender('CONSOLE', ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    // Colorized, console output !!!
    pattern = '%d{HH:mm:ss.SSS} [%thread] %highlight(%-5level) %cyan(%logger{36}) - %msg%n'
  }
}

root(DEBUG, ['CONSOLE'])
