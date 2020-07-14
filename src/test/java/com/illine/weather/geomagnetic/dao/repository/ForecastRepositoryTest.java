package com.illine.weather.geomagnetic.dao.repository;

import com.illine.weather.geomagnetic.model.base.ActiveType;
import com.illine.weather.geomagnetic.test.helper.generator.EntityGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.INFERRED;

@SpringIntegrationTest
@DisplayName("ForecastRepository Spring Integration Test")
class ForecastRepositoryTest {

    private static final int EXPECTED_SIZE_BY_DATE = 8;
    private static final int EXPECTED_SIZE_BY_DATE_AND_TIME = 2;

    @Autowired
    private ForecastRepository forecastRepository;

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("findAllByForecastDateBetween(): returns a collection has 8 elements")
    @Transactional
    @Sql(scripts = "classpath:sql/ForecastRepository/fill.sql", config = @SqlConfig(transactionMode = INFERRED))
    @Sql(scripts = "classpath:sql/ForecastRepository/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = INFERRED))
    void successfulFindAllByForecastDateBetween() {
        var date = LocalDate.now();
        var actual = forecastRepository.findAllByForecastDateBetween(date, date);
        assertEquals(EXPECTED_SIZE_BY_DATE, actual.size());
    }

    @Test
    @DisplayName("findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(): returns a collection has 2 elements")
    @Transactional
    @Sql(scripts = "classpath:sql/ForecastRepository/fill.sql", config = @SqlConfig(transactionMode = INFERRED))
    @Sql(scripts = "classpath:sql/ForecastRepository/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = INFERRED))
    void successfulFindAllByForecastDateBetweenAndForecastTimeGreaterThanEqual() {
        var date = LocalDate.now();
        var afterSixTime = LocalTime.of(18, 0);
        var actual = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(date, date, afterSixTime);
        assertEquals(EXPECTED_SIZE_BY_DATE_AND_TIME, actual.size());
    }

    @Test
    @DisplayName("save(): returns a saved entity")
    @Transactional
    @Sql(scripts = "classpath:sql/ForecastRepository/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = INFERRED))
    void successfulSave() {
        var testEntity = EntityGeneratorHelper.generateForecastEntity();
        var actual = assertDoesNotThrow(() -> forecastRepository.save(testEntity));

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getUpdated());
        assertNotNull(actual.getActive());
    }

    @Test
    @DisplayName("delete(): updates an entity status to DISABLED")
    @Transactional
    @Sql(scripts = "classpath:sql/ForecastRepository/fill.sql", config = @SqlConfig(transactionMode = INFERRED))
    @Sql(scripts = "classpath:sql/ForecastRepository/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = INFERRED))
    void successfulDelete() {
        var testEntity = EntityGeneratorHelper.generateForecastEntity();
        var entity = assertDoesNotThrow(() -> forecastRepository.save(testEntity));
        forecastRepository.flush();

        var expectedModified = entity.getUpdated();
        assertDoesNotThrow(() -> forecastRepository.delete(entity));
        forecastRepository.flush();

        assertEquals(ActiveType.DISABLED, entity.getActive());
        assertNotEquals(expectedModified, entity.getUpdated());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("findAllByForecastDateBetween(): returns an empty set")
    void failFindAllByForecastDateBetween() {
        var date = LocalDate.of(1, 1, 1);
        var actual = forecastRepository.findAllByForecastDateBetween(date, date);
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(): returns an empty set")
    void failFindAllByForecastDateBetweenAndForecastTimeGreaterThanEqual() {
        var date = LocalDate.now().minusYears(1);
        var afterSixTime = LocalTime.of(18, 0);
        var actual = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(date, date, afterSixTime);
        assertTrue(actual.isEmpty());
    }
}