package com.illine.weather.geomagnetic.config.logger;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Predicate;

import static com.p6spy.engine.logging.Category.*;

@Slf4j(topic = "GEOMAGNETIC-SQL")
public class Slf4JJdbcAppender extends FormattedLogger {

    @Override
    public void logException(Exception e) {
        LOGGER.error("SQL error", e);
    }

    @Override
    public void logText(String text) {
        LOGGER.info(text);
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        String message = strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url);

        Predicate<Category> predicate = it -> Objects.equals(BATCH, it) || Objects.equals(STATEMENT, it) || Objects.equals(ROLLBACK, it);
        LevelType.toLog(message, category, predicate);
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        return LevelType.isLevelEnabled(category);
    }
}
