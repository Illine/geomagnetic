package net.c7j.weather.geomagnetic.dao.access;

import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
@DisplayName("ForecastAccessService Integration Test")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/ForecastAccessService/fill_forecastAccessService.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/ForecastAccessService/clear_forecastAccessService.sql")
})
class ForecastAccessServiceIntegrationTest {

    private static final LocalDate TESTING_NON_EXISTENT_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate TESTING_DATE_2019_01_01 = LocalDate.of(2019, 1, 1);

    private static final LocalDateTime TESTING_NON_EXISTENT_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime TESTING_DATE_TIME_2019_01_01_07_00 = LocalDateTime.of(2019, 1, 1, 7, 0);

    private static final int EXCEPTED_COUNT_DIURNAL_FORECAST = 8;
    private static final int EXCEPTED_COUNT_CURRENT_FORECAST = 6;
    private static final int EXCEPTED_COUNT_THREE_DAY_FORECAST = 24;
    private static final int EXPECTED_EMPTY = 0;

    @Autowired
    private ForecastAccessService forecastAccessService;

    //  -----------------------   successful tests   -------------------------

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findDiurnal(): a successful call returns a forecast stream has size 8")
    void successfulFindDiurnal() {
        try (var actual = forecastAccessService.findDiurnal(TESTING_DATE_2019_01_01)) {
            assertEquals(EXCEPTED_COUNT_DIURNAL_FORECAST, actual.count());
        }
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findDiurnal(): a successful call returns a correct date of a forecast")
    void successfulCorrectDateFindDiurnal() {
        var actual = forecastAccessService.findDiurnal(TESTING_DATE_2019_01_01)
                .map(ForecastDto::getTime)
                .map(it -> Instant.ofEpochSecond(it).atZone(ZoneOffset.UTC))
                .map(ZonedDateTime::toLocalDate)
                .collect(Collectors.toSet());
        assertEquals(Collections.singleton(TESTING_DATE_2019_01_01), actual);
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findCurrent(): a successful call returns a forecast stream has size 6")
    void successfulFindCurrent() {
        try (var actual = forecastAccessService.findCurrent(TESTING_DATE_TIME_2019_01_01_07_00)) {
            assertEquals(EXCEPTED_COUNT_CURRENT_FORECAST, actual.count());
        }
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findCurrent(): a successful call returns a correct date and time of a forecast")
    void successfulCorrectDateTimeFindCurrent() {
        var expected = Set.of(
                LocalDateTime.of(2019, 1, 1, 6, 0), LocalDateTime.of(2019, 1, 1, 9, 0), LocalDateTime.of(2019, 1, 1, 12, 0),
                LocalDateTime.of(2019, 1, 1, 15, 0), LocalDateTime.of(2019, 1, 1, 18, 0), LocalDateTime.of(2019, 1, 1, 21, 0)
        );
        var actual = forecastAccessService.findCurrent(TESTING_DATE_TIME_2019_01_01_07_00)
                .map(ForecastDto::getTime)
                .map(it -> LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneOffset.UTC))
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findThreeDay(): a successful call returns a forecast stream has size 24")
    void successfulFindThreeDay() {
       try  (var actual = forecastAccessService.findThreeDay(TESTING_DATE_2019_01_01)) {
           assertEquals(EXCEPTED_COUNT_THREE_DAY_FORECAST, actual.count());
       }
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findThreeDay(): a successful call returns a correct date of a forecast")
    void successfulCorrectDateFindThreeDay() {
        var expected = Set.of(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 2), LocalDate.of(2019, 1, 3));
        var actual = forecastAccessService.findThreeDay(TESTING_DATE_2019_01_01)
                .map(ForecastDto::getTime)
                .map(it -> Instant.ofEpochSecond(it).atZone(ZoneOffset.UTC))
                .map(ZonedDateTime::toLocalDate)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findDiurnal(): a unsuccessful call returns an empty stream")
    void unsuccessfulFindDiurnal() {
        try (var actual = forecastAccessService.findDiurnal(TESTING_NON_EXISTENT_DATE)) {
            assertEquals(EXPECTED_EMPTY, actual.count());
        }
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findCurrent(): a unsuccessful call returns an empty stream")
    void unsuccessfulFindCurrent() {
        var actual = forecastAccessService.findCurrent(TESTING_NON_EXISTENT_DATE_TIME);
        assertEquals(EXPECTED_EMPTY, actual.count());
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findThreeDay(): a unsuccessful call returns an empty stream")
    void unsuccessfulFindThreeDay() {
        try (var actual = forecastAccessService.findThreeDay(TESTING_NON_EXISTENT_DATE)) {
            assertEquals(EXPECTED_EMPTY, actual.count());
        }
    }

    @Test
    @DisplayName("findDiurnal(): a unsuccessful call throws IllegalArgumentException when an arg is null")
    void unsuccessfulNullDateFindDiurnal() {
        assertThrows(IllegalArgumentException.class, () -> forecastAccessService.findDiurnal(null));
    }

    @Test
    @DisplayName("findCurrent(): a unsuccessful call throws IllegalArgumentException when an arg is null")
    void unsuccessfulNullDateTimeFindCurrent() {
        assertThrows(IllegalArgumentException.class, () -> forecastAccessService.findCurrent(null));
    }

    @Test
    @DisplayName("findThreeDay(): a unsuccessful call throws IllegalArgumentException when an arg is null")
    void unsuccessfulNullDateFindThreeDay() {
        assertThrows(IllegalArgumentException.class, () -> forecastAccessService.findThreeDay(null));
    }
}