package com.illine.weather.geomagnetic.service;

import com.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import com.illine.weather.geomagnetic.model.base.IndexType;
import com.illine.weather.geomagnetic.model.dto.ForecastDto;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringMockTest
@DisplayName("ForecastUpsertService Spring Mock Test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/ForecastUpsertService/fill.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/ForecastUpsertService/clean.sql")
class ForecastUpsertServiceTest {

    private static final LocalDate EXPECTED_DATE = LocalDate.now();

    @Autowired
    private ForecastUpsertService forecastUpsertService;

    @Autowired
    private ForecastAccessService forecastAccessService;

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("upsertForecasts(): returns an updated collection of forecast")
    void successfulUpsertForecastOnlyUpdate() {
        var originalIndexes =
                forecastAccessService.findThreeDays(EXPECTED_DATE)
                        .stream()
                        .map(ForecastDto::getIndex).map(IndexType::getIndex)
                        .collect(Collectors.toSet());
        var forecastSet = DtoGeneratorHelper.generateThreeDaysForecastDtoSet();
        var expectedIndexes =
                forecastSet
                        .stream()
                        .map(ForecastDto::getIndex).map(IndexType::getIndex)
                        .collect(Collectors.toSet());

        var actualIndexes =
                assertDoesNotThrow(() ->
                        forecastUpsertService.upsertForecasts(forecastSet, EXPECTED_DATE))
                        .stream()
                        .map(ForecastDto::getIndex).map(IndexType::getIndex).collect(Collectors.toSet()
                );

        assertNotEquals(originalIndexes, actualIndexes);
        assertEquals(expectedIndexes, actualIndexes);
    }

    @Test
    @DisplayName("upsertForecasts(): returns an upsert set of forecast")
    void successfulUpsertForecastWithSave() {
        var forecastSet = DtoGeneratorHelper.generateThreeDaysForecastDtoSet();
        for (ForecastDto dto : forecastSet) {
            if (EXPECTED_DATE.isEqual(dto.getForecastDate())) {
                dto.setForecastDate(EXPECTED_DATE.plusDays(3));
            }
        }

        var actualCollection = assertDoesNotThrow(() -> forecastUpsertService.upsertForecasts(forecastSet, EXPECTED_DATE));
        var actualMap = actualCollection.stream().collect(Collectors.partitioningBy(it -> Objects.nonNull(it.getId())));

        var expectedOldForecasts = 16;
        var expectedNewForecasts = 8;
        Assertions.assertEquals(expectedOldForecasts, actualMap.get(Boolean.TRUE).size());
        Assertions.assertEquals(expectedNewForecasts, actualMap.get(Boolean.FALSE).size());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("upsertForecasts(): throws IllegalArgumentException when an arg set is null")
    void unsuccessfulUpsertForecastsNullSet() {
        assertThrows(IllegalArgumentException.class, () -> forecastUpsertService.upsertForecasts(null, LocalDate.now()));
    }

    @Test
    @DisplayName("upsertForecasts(): throws IllegalArgumentException when an arg date is null")
    void unsuccessfulUpsertForecastsNullDate() {
        assertThrows(IllegalArgumentException.class, () -> forecastUpsertService.upsertForecasts(Collections.emptySet(), null));
    }
}