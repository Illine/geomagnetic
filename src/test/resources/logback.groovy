statusListener(NopStatusListener)

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d{ISO8601} [%thread] [%-5level] [%logger{36}] - %msg%n"
    }
}

logger("GEOMAGNETIC-EXCEPTION-HANDLER", DEBUG, ["CONSOLE"], false)
logger("GEOMAGNETIC-UTIL", DEBUG, ["CONSOLE"], false)
logger("GEOMAGNETIC-MAPPER", DEBUG, ["CONSOLE"], false)
logger("GEOMAGNETIC-ACCESS", DEBUG, ["CONSOLE"], false)
logger("GEOMAGNETIC-SERVICE", DEBUG, ["CONSOLE"], false)
logger("GEOMAGNETIC-SQL", DEBUG, ["CONSOLE"], false)
logger("GEOMAGNETIC-CLIENT", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC_EVENT", INFO, ["CONSOLE"], false)
logger("GEOMAGNETIC_SCHEDULE", INFO, ["CONSOLE"], false)
root(INFO, ["CONSOLE"])