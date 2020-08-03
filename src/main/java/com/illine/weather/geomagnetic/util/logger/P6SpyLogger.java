package com.illine.weather.geomagnetic.util.logger;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Generated
@Slf4j(topic = "GEOMAGNETIC-SQL")
public class P6SpyLogger extends FormattedLogger {

    @Override
    public void logException(Exception e) {
        LOGGER.error(e.getMessage(), e);
    }

    @Override
    public void logText(String text) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(text);
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(text);
        }
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        return Objects.equals(category, Category.INFO);
    }
}