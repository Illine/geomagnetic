package net.c7j.weather.geomag.common;

import java.util.EnumSet;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  ForecastIntervalType {

    INTERVAL_00_03("0003"),
    INTERVAL_03_06("0306"),
    INTERVAL_06_09("0609"),
    INTERVAL_09_12("0912"),
    INTERVAL_12_15("1215"),
    INTERVAL_15_18("1518"),
    INTERVAL_18_21("1821"),
    INTERVAL_21_00("2100");

    private final String interval;

    public static ForecastIntervalType intervalOf(String interval) {
        return EnumSet.allOf(ForecastIntervalType.class)
                .stream()
                .filter(it -> Objects.equals(it.interval, interval))
                .findFirst()
                .orElseThrow();
    }
}
