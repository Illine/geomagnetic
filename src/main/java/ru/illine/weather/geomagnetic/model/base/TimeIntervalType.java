package ru.illine.weather.geomagnetic.model.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public enum TimeIntervalType {

    INTERVAL_00_03("00-03UT", LocalTime.of(0, 0), Set.of(0, 1, 2)),
    INTERVAL_03_06("03-06UT", LocalTime.of(3, 0), Set.of(3, 4, 5)),
    INTERVAL_06_09("06-09UT", LocalTime.of(6, 0), Set.of(6, 7, 8)),
    INTERVAL_09_12("09-12UT", LocalTime.of(9, 0), Set.of(9, 10, 11)),
    INTERVAL_12_15("12-15UT", LocalTime.of(12, 0), Set.of(12, 13, 14)),
    INTERVAL_15_18("15-18UT", LocalTime.of(15, 0), Set.of(15, 16, 17)),
    INTERVAL_18_21("18-21UT", LocalTime.of(18, 0), Set.of(18, 19, 20)),
    INTERVAL_21_00("21-00UT", LocalTime.of(21, 0), Set.of(21, 22, 23));

    @Getter
    private final String interval;
    @Getter
    private final LocalTime timeInterval;
    private final Set<Integer> timeIntervals;

    public static TimeIntervalType intervalOf(String interval) {
        return EnumSet.allOf(TimeIntervalType.class)
                .stream()
                .filter(it -> Objects.equals(it.interval, interval))
                .findFirst()
                .orElseThrow();
    }

    public static LocalTime timeIntervalOf(Integer interval) {
        return EnumSet.allOf(TimeIntervalType.class)
                .stream()
                .filter(it -> it.timeIntervals.contains(interval))
                .findFirst()
                .orElseThrow()
                .getTimeInterval();
    }

    @Override
    public String toString() {
        return interval;
    }
}