package com.illine.weather.geomagnetic.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.illine.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.LoggerFactory;

import static com.illine.weather.geomagnetic.util.LogbackHelper.getLogger;
import static com.illine.weather.geomagnetic.util.LogbackHelper.switchLoggerLevel;
import static org.junit.jupiter.api.Assertions.*;

@UnitTest
@DisplayName("LogbackHelper Unit Test")
class LogbackHelperTest {

    private static final String DEFAULT_LOGGER_NAME = "logger";

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("getLogger(name): returns a logger has name 'logger'")
    void successfulGetLogger() {
        var actual = assertDoesNotThrow(() -> getLogger(DEFAULT_LOGGER_NAME));
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(DEFAULT_LOGGER_NAME, actual.getName());
        });
    }

    @DisplayName("getLogger(name, level): returns a logger has name 'logger' and right level")
    @ParameterizedTest
    @ValueSource(strings = {"OFF", "ERROR", "WARN", "INFO", "DEBUG", "TRACE", "ALL"})
    void successfulGetLoggerNameAndLevel(String level) {
        var currentLevel = Level.valueOf(level);
        var actual = assertDoesNotThrow(() -> getLogger(DEFAULT_LOGGER_NAME, currentLevel));
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(DEFAULT_LOGGER_NAME, actual.getName());
            assertEquals(currentLevel, actual.getLevel());
        });
    }

    @DisplayName("switchLoggerLevel(): changed a level logger")
    @ParameterizedTest
    @ValueSource(strings = {"OFF", "ERROR", "INFO", "INFO", "DEBUG", "TRACE", "ALL"})
    void successfulSwitchLoggerLevelDebug(String stringLevel) {
        var level = Level.valueOf(stringLevel);
        var logger = LoggerFactory.getLogger(DEFAULT_LOGGER_NAME);
        switchLoggerLevel(DEFAULT_LOGGER_NAME, level);
        assertEquals(level, ((Logger) logger).getLevel());
    }

    @Test
    @DisplayName("getLoggerContext(): returns a LoggerContext")
    void successfulGetLoggerContext() {
        var actual = assertDoesNotThrow(LogbackHelper::getLoggerContext);
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(LoggerContext.class, actual.getClass());
        });
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("getLogger(name): throws IllegalArgumentException when 'name' is null")
    void failGetLoggerNameNull() {
        assertThrows(IllegalArgumentException.class, () -> getLogger(null));
    }

    @Test
    @DisplayName("getLogger(name): throws IllegalArgumentException when 'name' is empty")
    void failGetLoggerNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> getLogger(""));
    }

    @Test
    @DisplayName("getLogger(name, level): throws IllegalArgumentException when 'level' is null")
    void failGetLoggerLevelNull() {
        assertThrows(IllegalArgumentException.class, () -> getLogger(DEFAULT_LOGGER_NAME, null));
    }
}
