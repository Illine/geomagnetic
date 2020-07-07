statusListener(NopStatusListener)

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d{ISO8601} [%thread] [%-5level] [%logger{36}] - %msg%n"
    }
}

root(INFO, ["CONSOLE"])