import ch.qos.logback.classic.encoder.PatternLayoutEncoder

statusListener(NopStatusListener)

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{ISO8601} [%level{5}] -- [%thread{40}] [%logger{36}] - %msg%n"
    }
}

root(INFO, ["CONSOLE"])