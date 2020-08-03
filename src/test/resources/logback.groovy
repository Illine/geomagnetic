import org.springframework.boot.logging.logback.ColorConverter

statusListener(NopStatusListener)

conversionRule("clr", ColorConverter)
appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{ISO8601} %clr([${System.getProperty("PID") ?: ''}] [%X{X-B3-TraceId:-}] [%X{X-B3-SpanId:-}]){magenta} %clr([%level{5}]) %clr(---){faint} %clr([%logger{36}]){cyan} %clr(:){faint} %m%n%ex"
    }
}

root(INFO, ["CONSOLE"])