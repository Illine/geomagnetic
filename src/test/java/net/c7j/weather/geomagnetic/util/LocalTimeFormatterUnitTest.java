package net.c7j.weather.geomagnetic.util;

import net.c7j.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static net.c7j.weather.geomagnetic.util.LocalTimeFormatter.formatToString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
@DisplayName("LocalTimeFormatter Unit Test")
class LocalTimeFormatterUnitTest {

    private static final LocalDate DEFAULT_LOCAL_DATE = LocalDate.of(2001, 2, 3);
    private static final LocalTime DEFAULT_LOCAL_TIME = LocalTime.of(1, 2, 3);
    private static final LocalDateTime DEFAULT_LOCAL_DATE_TIME = LocalDateTime.of(DEFAULT_LOCAL_DATE, DEFAULT_LOCAL_TIME);

    private static final String EXPECTED_LOCAL_DATE = "03.02.2001";
    private static final String EXPECTED_LOCAL_TIME = "01:02:03";
    private static final String EXPECTED_LOCAL_DATE_TIME = "01:02:03 03.02.2001";

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("formatToString(LocalDate): a successful call returns a correct string")
    void successfulDateFormatToString() {
        var actual = formatToString(DEFAULT_LOCAL_DATE);
        assertEquals(EXPECTED_LOCAL_DATE, actual);
    }

    @Test
    @DisplayName("formatToString(LocalTime): a successful call returns a correct string")
    void successfulTimeFormatToString() {
        var actual = formatToString(DEFAULT_LOCAL_TIME);
        assertEquals(EXPECTED_LOCAL_TIME, actual);
    }

    @Test
    @DisplayName("formatToString(LocalDateTime): a successful call returns a correct string")
    void successfulDateTimeFormatToString() {
        var actual = formatToString(DEFAULT_LOCAL_DATE_TIME);
        assertEquals(EXPECTED_LOCAL_DATE_TIME, actual);
    }

    @Test
    @DisplayName("formatToString(LocalDate, Formatter): a successful call returns a correct string")
    void successfulDateFormatFormatterToString() {
        var expected = "2001.02.03";
        var actual = formatToString(DEFAULT_LOCAL_DATE, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("formatToString(LocalTime, Formatter): a successful call returns a correct string")
    void successfulTimeFormatFormatterToString() {
        var expected = "03:02:01";
        var actual = formatToString(DEFAULT_LOCAL_TIME, DateTimeFormatter.ofPattern("ss:mm:HH"));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("formatToString(LocalDateTime, Formatter): a successful call returns a correct string")
    void successfulDateTimeFormatFormatterToString() {
        var expected = "03:02:01 2001.02.03";
        var actual = formatToString(DEFAULT_LOCAL_DATE_TIME, DateTimeFormatter.ofPattern("ss:mm:HH yyyy.mm.dd"));
        assertEquals(expected, actual);
    }
}