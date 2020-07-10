package com.illine.weather.geomagnetic.util.logger;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j(topic = "GEOMAGNETIC-SQL")
public class P6SpyLogger extends FormattedLogger {

    @Override
    public void logException(Exception e) {
        LOGGER.error(e.getMessage(), e);
    }

    @Override
    public void logText(String text) {
        LOGGER.info(text);
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        var message = strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url);
        log(category, message);
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        if (Objects.equals(Category.ERROR, category)) {
            return LOGGER.isErrorEnabled();
        } else if (Objects.equals(Category.WARN, category)) {
            return LOGGER.isWarnEnabled();
        } else if (Objects.equals(Category.DEBUG, category)) {
            return LOGGER.isDebugEnabled();
        } else {
            return LOGGER.isInfoEnabled();
        }
    }

    private void log(Category category, String message) {
        if (Objects.equals(Category.ERROR, category)) {
            LOGGER.error(message);
        } else if (Objects.equals(Category.WARN, category)) {
            LOGGER.warn(message);
        } else if (Objects.equals(Category.DEBUG, category)) {
            LOGGER.debug(message);
        } else {
            LOGGER.info(message);
        }
    }
}