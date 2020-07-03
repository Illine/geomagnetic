package com.illine.weather.geomagnetic.config.logger;

import com.p6spy.engine.common.P6Util;

import java.util.EnumSet;
import java.util.Objects;

public enum SqlCategory {

    COMMIT {
        @Override
        public String format(long elapsed, String sql) {
            return "The query was committed";
        }
    },
    ROLLBACK {
        @Override
        public String format(long elapsed, String sql) {
            return "The query was rolled back!";
        }
    },
    BATCH,
    STATEMENT,
    UNKNOWN;

    static SqlCategory of(String category) {
        return EnumSet.allOf(SqlCategory.class)
                .stream()
                .filter(sqlCategory -> Objects.equals(sqlCategory.name().toLowerCase(), category))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public String format(long elapsed, String sql) {
        return P6Util.singleLine(sql) + " {executed in " + elapsed + " msec}";
    }

}
