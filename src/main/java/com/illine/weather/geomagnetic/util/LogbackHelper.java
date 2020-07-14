package com.illine.weather.geomagnetic.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogbackHelper {

    public static Logger getLogger(String name) {
        Assert.hasText(name, "The 'name' shouldn't be null or empty!");
        return getLoggerContext().getLogger(name);
    }

    public static Logger getLogger(String name, Level level) {
        Assert.notNull(level, "The 'level' shouldn't be null");
        var logger = getLogger(name);
        logger.setLevel(level);
        return logger;
    }

    public static void switchLoggerLevel(String name, Level level) {
        Assert.hasText(name, "The 'name' shouldn't be null or empty!");
        Assert.notNull(level, "The 'level' shouldn't be null");
        getLoggerContext().getLogger(name).setLevel(level);
    }

    public static LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }
}
