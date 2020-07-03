package com.illine.weather.geomagnetic.config.logger;


import com.p6spy.engine.logging.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j(topic = "GEOMAGNETIC-SQL")
public enum LevelType {

    ERROR {
        @Override
        boolean isEnabled() {
            return LOGGER.isErrorEnabled();
        }

        @Override
        void log(String message) {
            LOGGER.error(message);
        }
    },
    WARN {
        @Override
        boolean isEnabled() {
            return LOGGER.isWarnEnabled();
        }

        @Override
        void log(String message) {
            LOGGER.warn(message);
        }
    },
    DEBUG {
        @Override
        boolean isEnabled() {
            return LOGGER.isDebugEnabled();
        }

        @Override
        void log(String message) {
            LOGGER.debug(message);
        }
    },
    INFO,
    UNKNOWN;

    public static boolean isLevelEnabled(Category category) {
        Assert.notNull(category, "A category shouldn't be null!");
        return of(category).isEnabled();
    }

    public static void toLog(String message, Category category, Predicate<Category> predicate) {
        if (predicate.test(category)) {
            LevelType.of(category).log(message);
        }
    }

    private static LevelType of(Category category) {
        return EnumSet.allOf(LevelType.class)
                .stream()
                .filter(level -> Objects.equals(level.name().toLowerCase(), category.getName()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    boolean isEnabled() {
        return LOGGER.isInfoEnabled();
    }

    void log(String message) {
        LOGGER.info(message);
    }
}
