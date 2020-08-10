package com.illine.weather.geomagnetic.util.logger;

import com.p6spy.engine.spy.appender.Slf4JLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

@Slf4j(topic = "GEOMAGNETIC-SQL")
public class P6SpyLogger extends Slf4JLogger {

    private static final String SLF4J_LOGGER_NAME = "log";

    public P6SpyLogger() {
        reflectionSetLogger();
    }

    private void reflectionSetLogger() {
        var logField = ReflectionUtils.findField(super.getClass(), SLF4J_LOGGER_NAME);
        ReflectionUtils.makeAccessible(Objects.requireNonNull(logField));
        ReflectionUtils.setField(logField, this, LOGGER);
    }
}