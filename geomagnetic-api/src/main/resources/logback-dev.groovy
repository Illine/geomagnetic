statusListener(NopStatusListener)

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d{ISO8601} [%thread] [%-5level] [%logger{36}] - %msg%n"
    }
}

logger("GEOMAGNETIC-EXCEPTION-HANDLER", WARN, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-UTIL", INFO, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-MAPPER", INFO, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-ACCESS", INFO, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-SERVICE", INFO, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-SQL", INFO, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-CLIENT", INFO, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-EVENT", INFO, ["FILE", "CONSOLE"], false)
logger("GEOMAGNETIC-SCHEDULE", INFO, ["FILE", "CONSOLE"], false)
root(INFO, ["CONSOLE"])