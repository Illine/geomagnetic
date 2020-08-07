package com.illine.weather.geomagnetic.util;

import com.illine.weather.geomagnetic.test.tag.UnitTest;
import com.illine.weather.geomagnetic.util.logger.P6SpyLogger;
import com.p6spy.engine.logging.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class P6SpyLoggerTest {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "Test Exception";
    private static final String DEFAULT_SQL_TEXT = "Test Text";

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("logException(): executing without exception")
    void successfulLogException() {
        assertDoesNotThrow(() -> new P6SpyLogger().logException(new RuntimeException(DEFAULT_EXCEPTION_MESSAGE)));
    }

    @Test
    @DisplayName("logText(): executing without exception")
    void successfulLogText() {
        assertDoesNotThrow(() -> new P6SpyLogger().logText(DEFAULT_SQL_TEXT));
    }

    @Test
    @DisplayName("isCategoryEnabled(): returns 'true' when Category is 'INFO")
    void successfulIsCategoryEnabled() {
        assertTrue(assertDoesNotThrow(() -> new P6SpyLogger().isCategoryEnabled(Category.INFO)));
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("isCategoryEnabled(): returns 'false' when Category isn't 'INFO'")
    void failIsCategoryEnabled() {
        assertFalse(assertDoesNotThrow(() -> new P6SpyLogger().isCategoryEnabled(Category.DEBUG)));
    }
}