package com.illine.weather.geomagnetic.config.logger;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class JdbcLoggerFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed,
                                String category, String prepared, String sql, String url) {
        return SqlCategory.of(category).format(elapsed, sql);
    }
}
