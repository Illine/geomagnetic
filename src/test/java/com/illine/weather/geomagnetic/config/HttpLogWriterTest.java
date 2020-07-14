package com.illine.weather.geomagnetic.config;

import ch.qos.logback.classic.Level;
import com.illine.weather.geomagnetic.config.property.LogbookProperties;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import static com.illine.weather.geomagnetic.test.helper.generator.CommonGeneratorHelper.generateString;
import static com.illine.weather.geomagnetic.util.LogbackHelper.switchLoggerLevel;
import static org.junit.jupiter.api.Assertions.*;

@SpringMockTest
@DisplayName("HttpLogWriter Spring Mock Test")
class HttpLogWriterTest {

    @Autowired
    private LogbookProperties properties;

    @Autowired
    private HttpLogWriter geomagneticHttpLogWriter;

    //  -----------------------   successful tests   -------------------------

    @DisplayName("isActive(): returns true when logger has level DEBUG, INFO or WARN")
    @ParameterizedTest
    @ValueSource(strings = {"DEBUG", "INFO", "WARN"})
    void successfulIsActive(String stringLevel) {
        switchLoggerLevel(properties.getName(), Level.valueOf(stringLevel));
        assertTrue(geomagneticHttpLogWriter.isActive());
    }

    @DisplayName("write(precorrelation, request): a successful call")
    @ParameterizedTest
    @ValueSource(strings = {"DEBUG", "INFO", "WARN"})
    void successfulWriteRequest(String stringLevel) {
        switchLoggerLevel(properties.getName(), Level.valueOf(stringLevel));
        assertDoesNotThrow(() -> geomagneticHttpLogWriter.write((Precorrelation) null, generateString()));
    }

    @DisplayName("write(correlation, response): a successful call")
    @ParameterizedTest
    @ValueSource(strings = {"DEBUG", "INFO", "WARN"})
    void successfulWriteResponse(String stringLevel) {
        switchLoggerLevel(properties.getName(), Level.valueOf(stringLevel));
        assertDoesNotThrow(() -> geomagneticHttpLogWriter.write(null, generateString()));
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("isActive(): returns false when logger has level OFF")
    void failIsActive() {
        switchLoggerLevel(properties.getName(), Level.OFF);
        assertFalse(geomagneticHttpLogWriter.isActive());
    }
}