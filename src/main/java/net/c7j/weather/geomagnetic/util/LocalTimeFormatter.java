package net.c7j.weather.geomagnetic.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j(topic = "GEOMAGNETIC-UTIL")
public final class LocalTimeFormatter {

    private static final String NOT_NULL_MESSAGE = "The 'formatter' should be null!";

    /**
     * The date and time pattern: {@code HH:mm:ss dd.MM.yyyy}
     */
    public static final String DEFAULT_DATE_TIME_PATTERN = "HH:mm:ss dd.MM.yyyy";

    /**
     * The date pattern: {@code dd.MM.yyyy}
     */
    public static final String DEFAULT_DATE_PATTERN = "dd.MM.yyyy";

    /**
     * The time pattern: {@code HH:mm:ss}
     */
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    /**
     * The formatter uses {@link LocalTimeFormatter#DEFAULT_DATE_TIME_PATTERN}
     */
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

    /**
     * The formatter uses {@link LocalTimeFormatter#DEFAULT_DATE_PATTERN}
     */
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

    /**
     * The formatter uses {@link LocalTimeFormatter#DEFAULT_TIME_PATTERN}
     */
    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN);

    public static String formatToString(LocalDateTime dateTime) {
        return formatToString(dateTime, DEFAULT_DATE_TIME_FORMATTER);
    }

    public static String formatToString(LocalDate date) {
        return formatToString(date, DEFAULT_DATE_FORMATTER);
    }

    public static String formatToString(LocalTime time) {
        return formatToString(time, DEFAULT_TIME_FORMATTER);
    }

    public static String formatToString(LocalDateTime dateTime, DateTimeFormatter formatter) {
        Assert.notNull(dateTime, "The 'dateTime' should be null!");
        Assert.notNull(formatter, NOT_NULL_MESSAGE);
        return dateTime.format(formatter);
    }

    public static String formatToString(LocalDate date, DateTimeFormatter formatter) {
        Assert.notNull(date, "The 'date' should be null!");
        Assert.notNull(formatter, NOT_NULL_MESSAGE);
        return date.format(formatter);
    }

    public static String formatToString(LocalTime time, DateTimeFormatter formatter) {
        Assert.notNull(time, "The 'time' should be null!");
        Assert.notNull(formatter, NOT_NULL_MESSAGE);
        return time.format(formatter);
    }
}