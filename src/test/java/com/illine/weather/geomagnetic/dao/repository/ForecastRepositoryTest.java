package com.illine.weather.geomagnetic.dao.repository;

import com.illine.weather.geomagnetic.test.helper.generator.EntityGeneratorHelper;
import com.illine.weather.geomagnetic.dao.entity.ForecastEntity;
import com.illine.weather.geomagnetic.model.base.ActiveType;
import com.illine.weather.geomagnetic.model.base.IndexType;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringIntegrationTest
@DisplayName("ForecastRepository Spring Integration Test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/ForecastRepository/fill.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/ForecastRepository/clean.sql")
class ForecastRepositoryTest {

    private static final int EXPECTED_SIZE_BY_DATE = 8;
    private static final int EXPECTED_SIZE_BY_DATE_AND_TIME = 2;

    @Autowired
    private ForecastRepository forecastRepository;

    private ForecastEntity testEntity;

    @BeforeEach
    void setUp() {
        testEntity = EntityGeneratorHelper.generateForecastEntity();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("findAll(): returns enabled entities")
    void successfulFindAll() {
        var allEntities = forecastRepository.findAll();
        assertFalse(allEntities.isEmpty());

        boolean onlyEnabled =
                allEntities.stream()
                        .allMatch(it -> Objects.equals(it.getActive(), ActiveType.ENABLED));
        assertTrue(onlyEnabled);
    }

    @Test
    @DisplayName("findAllByForecastDateBetween(): returns a filled set")
    void successfulFindAllByForecastDateBetween() {
        var date = LocalDate.now();
        var actual = forecastRepository.findAllByForecastDateBetween(date, date);
        assertEquals(EXPECTED_SIZE_BY_DATE, actual.size());
    }

    @Test
    @DisplayName("findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(): returns a filled set")
    void successfulFindAllByForecastDateBetweenAndForecastTimeGreaterThanEqual() {
        var date = LocalDate.now();
        var afterSixTime = LocalTime.of(18, 0);
        var actual = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(date, date, afterSixTime);
        assertEquals(EXPECTED_SIZE_BY_DATE_AND_TIME, actual.size());
    }

    @Test
    @DisplayName("save(): returns a saved entity")
    void successfulSave() {
        var actual = assertDoesNotThrow(() -> forecastRepository.save(testEntity));

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getUpdated());
        assertNotNull(actual.getActive());
    }

    @Test
    @DisplayName("save(): returns an updated entity")
    void successfulUpdateSave() {
        var entity = assertDoesNotThrow(() -> forecastRepository.save(testEntity));
        forecastRepository.flush();

        var expectedModified = entity.getUpdated();
        var expectedIndex = entity.getIndex();

        entity.setIndex(IndexType.EXTREME_STORM);
        var actual = assertDoesNotThrow(() -> forecastRepository.save(entity));
        forecastRepository.flush();

        assertNotEquals(expectedIndex, actual.getIndex());
        assertTrue(expectedModified.isBefore(actual.getUpdated()));
    }

    @Test
    @Transactional
    @DisplayName("delete(): updates a status of an entity")
    void successfulDelete() {
        var entity = assertDoesNotThrow(() -> forecastRepository.save(testEntity));
        forecastRepository.flush();

        var expectedModified = entity.getUpdated();
        assertDoesNotThrow(() -> forecastRepository.delete(entity));
        forecastRepository.flush();

        assertEquals(ActiveType.DISABLED, entity.getActive());
        assertNotEquals(expectedModified, entity.getUpdated());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("findAllByForecastDateBetween(): returns an empty set")
    void unsuccessfulFindAllByForecastDateBetween() {
        var date = LocalDate.of(1, 1, 1);
        var actual = forecastRepository.findAllByForecastDateBetween(date, date);
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(): returns an empty set")
    void unsuccessfulFindAllByForecastDateBetweenAndForecastTimeGreaterThanEqual() {
        var date = LocalDate.now().minusYears(1);
        var afterSixTime = LocalTime.of(18, 0);
        var actual = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(date, date, afterSixTime);
        assertTrue(actual.isEmpty());
    }
}