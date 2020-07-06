package com.illine.weather.geomagnetic.dao.access;

import com.illine.weather.geomagnetic.model.dto.ForecastDto;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringIntegrationTest
@DisplayName("ForecastAccessService Spring Integration Test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/ForecastAccessService/fill.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/ForecastAccessService/clean.sql")
class ForecastAccessServiceTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.now();

    private static final int EXCEPTED_COUNT_DIURNAL_FORECAST = 8;
    private static final int EXCEPTED_COUNT_CURRENT_FORECAST = 6;
    private static final int EXCEPTED_COUNT_THREE_DAY_FORECAST = 24;

    @Autowired
    private ForecastAccessService forecastAccessService;

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("save(): saving collection of forecast dto")
    void successfulSave() {
        assertDoesNotThrow(() -> forecastAccessService.save(Collections.singleton(DtoGeneratorHelper.generateForecastDto())));
    }

    @Test
    @DisplayName("findDiurnal(): returns a forecast set has size 8")
    void successfulFindDiurnal() {
        var actual = forecastAccessService.findDiurnal(DEFAULT_DATE);
        assertEquals(EXCEPTED_COUNT_DIURNAL_FORECAST, actual.size());
    }

    @Test
    @DisplayName("findDiurnal(): returns a correct date of a forecast")
    void successfulCorrectDateFindDiurnal() {
        var actual =
                forecastAccessService.findDiurnal(DEFAULT_DATE)
                        .stream()
                        .map(ForecastDto::getForecastDate)
                        .collect(Collectors.toSet());
        assertEquals(Collections.singleton(DEFAULT_DATE), actual);
    }

    @Test
    @DisplayName("findCurrent(): returns a forecast set has size 6")
    void successfulFindCurrent() {
        var sevenTime = LocalTime.of(7, 0);
        var actual = forecastAccessService.findCurrent(LocalDateTime.of(DEFAULT_DATE, sevenTime));
        assertEquals(EXCEPTED_COUNT_CURRENT_FORECAST, actual.size());
    }

    @Test
    @DisplayName("findCurrent(): returns a correct date and time of a forecast")
    void successfulCorrectDateTimeFindCurrent() {
        var expected = Set.of(
                LocalDateTime.of(DEFAULT_DATE, LocalTime.of(6, 0)),
                LocalDateTime.of(DEFAULT_DATE, LocalTime.of(9, 0)),
                LocalDateTime.of(DEFAULT_DATE, LocalTime.of(12, 0)),
                LocalDateTime.of(DEFAULT_DATE, LocalTime.of(15, 0)),
                LocalDateTime.of(DEFAULT_DATE, LocalTime.of(18, 0)),
                LocalDateTime.of(DEFAULT_DATE, LocalTime.of(21, 0))
        );
        var sevenTime = LocalTime.of(7, 0);
        var actual =
                forecastAccessService.findCurrent(LocalDateTime.of(DEFAULT_DATE, sevenTime))
                        .stream()
                        .map(it -> LocalDateTime.of(it.getForecastDate(), it.getForecastTime()))
                        .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("findThreeDay(): returns a forecast set has size 24")
    void successfulFindThreeDay() {
        var actual = forecastAccessService.findThreeDays(DEFAULT_DATE);
        assertEquals(EXCEPTED_COUNT_THREE_DAY_FORECAST, actual.size());
    }

    @Test
    @DisplayName("findThreeDay(): returns a correct date of a forecast")
    void successfulCorrectDateFindThreeDay() {
        var expected = Set.of(LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        var actual =
                forecastAccessService.findThreeDays(DEFAULT_DATE)
                        .stream()
                        .map(ForecastDto::getForecastDate)
                        .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("save(): throws IllegalArgumentException when collection is null")
    void failSave() {
        assertThrows(IllegalArgumentException.class, () -> forecastAccessService.save(null));
    }

    @Test
    @DisplayName("findDiurnal(): returns an empty set")
    void failFindDiurnal() {
        var actual = forecastAccessService.findDiurnal(LocalDate.now().minusYears(1));
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("findCurrent(): returns an empty set")
    void failFindCurrent() {
        var actual = forecastAccessService.findCurrent(LocalDateTime.now().minusYears(1));
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("findThreeDay(): returns an empty set")
    void failFindThreeDay() {
        var actual = forecastAccessService.findThreeDays(LocalDate.now().minusYears(1));
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("findDiurnal(): throws IllegalArgumentException when an arg is null")
    void failFindDiurnalNullDate() {
        assertThrows(IllegalArgumentException.class, () -> forecastAccessService.findDiurnal(null));
    }

    @Test
    @DisplayName("findCurrent(): throws IllegalArgumentException when an arg is null")
    void failFindCurrentNullDateTime() {
        assertThrows(IllegalArgumentException.class, () -> forecastAccessService.findCurrent(null));
    }

    @Test
    @DisplayName("findThreeDay(): throws IllegalArgumentException when an arg is null")
    void failThreeDayNullDateFind() {
        assertThrows(IllegalArgumentException.class, () -> forecastAccessService.findThreeDays(null));
    }
}