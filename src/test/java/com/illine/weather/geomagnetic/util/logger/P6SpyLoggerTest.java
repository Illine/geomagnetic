package com.illine.weather.geomagnetic.util.logger;

import com.illine.weather.geomagnetic.test.helper.ReflectionHelper;
import com.illine.weather.geomagnetic.test.tag.UnitTest;
import com.p6spy.engine.logging.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static com.illine.weather.geomagnetic.test.helper.generator.CommonGeneratorHelper.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@UnitTest
@DisplayName("P6SpyLogger Unit Test")
class P6SpyLoggerTest {

    private Logger loggerMock;
    private P6SpyLogger p6SpyLogger;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        p6SpyLogger = new P6SpyLogger();
        loggerMock = Mockito.mock(Logger.class);
        ReflectionHelper.setStaticFinalField(p6SpyLogger, "LOGGER", loggerMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("logException(): a successful call the method executes an logger.error")
    void successfulLogException() {
        p6SpyLogger.logException(new Exception("Some error message"));
        verify(loggerMock).error(anyString(), any(Exception.class));
    }

    @Test
    @DisplayName("logText(): a successful call the method executes an logger.info")
    void successfulLogText() {
        p6SpyLogger.logText("");
        verify(loggerMock).info(anyString());
    }

    @Test
    @DisplayName("logSQL(): a successful call the method executes an logger.error when Category.ERROR")
    void successfulLogSQLError() {
        p6SpyLogger.logSQL(generateInteger(), generateString(), generateLong(), Category.ERROR, generateString(), generateString(), generateString());
        verify(loggerMock).error(anyString());
    }

    @Test
    @DisplayName("logSQL(): a successful call the method executes an logger.warn when Category.WARN")
    void successfulLogSQLWarn() {
        p6SpyLogger.logSQL(generateInteger(), generateString(), generateLong(), Category.WARN, generateString(), generateString(), generateString());
        verify(loggerMock).warn(anyString());
    }

    @Test
    @DisplayName("logSQL(): a successful call the method executes an logger.debug when Category.DEBUG")
    void successfulLogSQLDebug() {
        p6SpyLogger.logSQL(generateInteger(), generateString(), generateLong(), Category.DEBUG, generateString(), generateString(), generateString());
        verify(loggerMock).debug(anyString());
    }

    @Test
    @DisplayName("logSQL(): a successful call the method executes an logger.info when Category.INFO")
    void successfulLogSQLInfo() {
        p6SpyLogger.logSQL(generateInteger(), generateString(), generateLong(), Category.INFO, generateString(), generateString(), generateString());
        verify(loggerMock).info(anyString());
    }

    @Test
    @DisplayName("isCategoryEnabled(): returns true when Category.ERROR")
    void successfulIsCategoryEnabledError() {
        when(loggerMock.isErrorEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(p6SpyLogger.isCategoryEnabled(Category.ERROR));
    }

    @Test
    @DisplayName("isCategoryEnabled(): returns true when Category.WARN")
    void successfulIsCategoryEnabledWarn() {
        when(loggerMock.isWarnEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(p6SpyLogger.isCategoryEnabled(Category.WARN));
    }

    @Test
    @DisplayName("isCategoryEnabled(): returns true when Category.DEBUG")
    void successfulIsCategoryEnabledDebug() {
        when(loggerMock.isDebugEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(p6SpyLogger.isCategoryEnabled(Category.DEBUG));
    }

    @Test
    @DisplayName("isCategoryEnabled(): returns true when Category.INFO")
    void successfulIsCategoryEnabledInfo() {
        when(loggerMock.isInfoEnabled()).thenReturn(Boolean.TRUE);
        assertTrue(p6SpyLogger.isCategoryEnabled(Category.INFO));
    }
}