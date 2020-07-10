import org.springframework.boot.logging.logback.ColorConverter

statusListener(NopStatusListener)

conversionRule("clr", ColorConverter)
appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%clr(%d{ISO8601}){faint} %clr(${System.getProperty("PID") ?: ''}){magenta} %clr([%level{5}]) %clr(---){faint} %clr([%logger{40}]){cyan} %clr(-){faint} %m%n%ex"
    }
}

root(INFO, ["CONSOLE"])