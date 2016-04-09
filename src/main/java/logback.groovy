// =============================================================================
// [ONTE] :: This must be at the root of the Jar for Logback to pick it up
// =============================================================================

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.OFF

def appenders = []

def logdir = System.getProperty('liblevenshtein.logdir')

if (logdir != null) {
  appender('FILE', RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
      Pattern = '%d{yyyy-MM-dd HH:mm:ss zZ} %level %thread %mdc %logger - %m%n'
    }
    rollingPolicy(TimeBasedRollingPolicy) {
      def action = System.getProperty('liblevenshtein.action')
      if (action == null) {
        System.err.println('-Dliblevenshtein.action must be specified')
        System.exit(1)
      }
      FileNamePattern = "${logdir}/${action}-%d{yyyy-MM-dd}.log.gz"
    }
  }

  appenders << 'FILE'
}

def debug = System.getProperty('liblevenshtein.debug')

if (debug == 'true') {
  appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
      // Colorized, console output !!!
      pattern = '%d{HH:mm:ss.SSS} [%thread] %highlight(%-5level) %cyan(%logger{36}) - %msg%n'
    }
  }

  appenders << 'CONSOLE'
}

if (appenders.isEmpty()) {
  root(OFF, [])
}
else {
  root(DEBUG, appenders)
}
