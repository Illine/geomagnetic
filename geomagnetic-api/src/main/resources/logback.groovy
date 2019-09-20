import ch.qos.logback.classic.encoder.PatternLayoutEncoder

statusListener(NopStatusListener)

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d{ISO8601} [%thread] [%-5level] [%logger{36}] - %msg%n"
    }
}

logger("GEOMAGNETIC-EXCEPTION-HANDLER", WARN, ["CONSOLE"], false)
logger("GEOMAGNETIC-UTIL", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC-MAPPER", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC-ACCESS", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC-SERVICE", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC-SQL", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC-CLIENT", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC-EVENT", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC-SCHEDULE", INFO, ["CONSOLE"], false)
root(INFO, ["CONSOLE"])