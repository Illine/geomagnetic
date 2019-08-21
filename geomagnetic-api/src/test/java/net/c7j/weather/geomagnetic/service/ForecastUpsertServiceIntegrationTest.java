package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.base.IndexType;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@DisplayName("ForecastUpsertService Integration Test")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/ForecastUpsertService/fill_forecastUpsertService.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/ForecastUpsertService/clear_forecastUpsertService.sql")
})
class ForecastUpsertServiceIntegrationTest {

    private static final LocalDate EXPECTED_DATE = LocalDate.of(2019, 1, 1);

    @Autowired
    private ForecastUpsertService forecastUpsertService;

    @Autowired
    private ForecastAccessService forecastAccessService;

    //  -----------------------   successful tests   -------------------------

    @Transactional(readOnly = true)
    @Test
    @DisplayName("upsertForecasts(): a successful call returns an updated stream of entities")
    void successfulOnlyUpdateUpsertForecast() {
        var originalIndexes = forecastAccessService.getThreeDay(EXPECTED_DATE).map(ForecastEntity::getIndex).map(IndexType::getIndex).collect(Collectors.toSet());
        var forecastEntityStream = GeneratorHelper.generateStreamForecastEntity(EXPECTED_DATE);
        var expectedIndexes = GeneratorHelper.generateStreamForecastEntity(EXPECTED_DATE).map(ForecastEntity::getIndex).map(IndexType::getIndex).collect(Collectors.toSet());

        var actualIndexes =
                assertDoesNotThrow(() -> forecastUpsertService.upsertForecasts(forecastEntityStream, EXPECTED_DATE))
                        .map(ForecastEntity::getIndex).map(IndexType::getIndex).collect(Collectors.toSet());

        assertNotEquals(originalIndexes, actualIndexes);
        assertEquals(expectedIndexes, actualIndexes);
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("upsertForecasts(): a successful call returns an upsert stream of entities")
    void successfulWithSaveUpsertForecast() {
        var date = EXPECTED_DATE.plusDays(1);
        var forecastEntityStream = GeneratorHelper.generateStreamForecastEntity(date);

        var actualStream = assertDoesNotThrow(() -> forecastUpsertService.upsertForecasts(forecastEntityStream, EXPECTED_DATE));
        var actualMap = actualStream.collect(Collectors.partitioningBy(it -> Objects.nonNull(it.getId())));

        var expectedOldForecasts = 16;
        var expectedNewForecasts = 8;
        assertEquals(expectedOldForecasts, actualMap.get(Boolean.TRUE).size());
        assertEquals(expectedNewForecasts, actualMap.get(Boolean.FALSE).size());
    }
    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("upsertForecasts(): an unsuccessful call throws IllegalArgumentException when an arg stream is null")
    void unsuccessfulNullStreamUpsertForecasts() {
        assertThrows(IllegalArgumentException.class, () -> forecastUpsertService.upsertForecasts(null, LocalDate.now()));
    }

    @Test
    @DisplayName("upsertForecasts(): an unsuccessful call throws IllegalArgumentException when an arg date is null")
    void unsuccessfulStreamNullUpsertForecasts() {
        assertThrows(IllegalArgumentException.class, () -> forecastUpsertService.upsertForecasts(Stream.empty(), null));
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("upsertForecasts(): an unsuccessful call throws NotFoundException when necessary entities aren't exist to a database")
    void unsuccessfulNotFoundUpsertForecasts() {
        assertThrows(NotFoundException.class, () -> forecastUpsertService.upsertForecasts(Stream.empty(), LocalDate.now()));
    }
}