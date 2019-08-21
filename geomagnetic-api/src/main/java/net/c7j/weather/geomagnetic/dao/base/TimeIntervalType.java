package net.c7j.weather.geomagnetic.dao.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum TimeIntervalType {

    INTERVAL_00_03("00-03UT", LocalTime.of(0, 0)),
    INTERVAL_03_06("03-06UT", LocalTime.of(3, 0)),
    INTERVAL_06_09("06-09UT", LocalTime.of(6, 0)),
    INTERVAL_09_12("09-12UT", LocalTime.of(9, 0)),
    INTERVAL_12_15("12-15UT", LocalTime.of(12, 0)),
    INTERVAL_15_18("15-18UT", LocalTime.of(15, 0)),
    INTERVAL_18_21("18-21UT", LocalTime.of(18, 0)),
    INTERVAL_21_00("21-00UT", LocalTime.of(21, 0));

    private final String interval;
    private final LocalTime timeInterval;

    public static TimeIntervalType intervalOf(String interval) {
        return EnumSet.allOf(TimeIntervalType.class)
                .stream()
                .filter(it -> Objects.equals(it.interval, interval))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String toString() {
        return interval;
    }
}