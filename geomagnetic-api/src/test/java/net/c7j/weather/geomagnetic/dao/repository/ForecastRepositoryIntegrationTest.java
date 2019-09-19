package net.c7j.weather.geomagnetic.dao.repository;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.model.base.ActiveType;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@DisplayName("ForecastRepository Integration Test")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/ForecastRepository/fill_forecastRepository.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/ForecastRepository/clear_forecastRepository.sql")
})
class ForecastRepositoryIntegrationTest {

    private static final int EXPECTED_SIZE_BY_DATE = 8;
    private static final int EXPECTED_SIZE_BY_DATE_AND_TIME = 2;
    private static final int EXPECTED_EMPTY = 0;

    @Autowired
    private ForecastRepository forecastRepository;

    private ForecastEntity testEntity;

    @BeforeEach
    void setUp() {
        testEntity = GeneratorHelper.generateForecastEntity();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("findAll(): a successful call returns enabled entities")
    void successfulFindAll() {
        var allEntities = forecastRepository.findAll();
        assertFalse(allEntities.isEmpty());

        boolean onlyEnabled =
                allEntities.stream()
                        .allMatch(it -> Objects.equals(it.getActive(), ActiveType.ENABLED));
        assertTrue(onlyEnabled);
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findAllByForecastDateBetween(): a successful call returns a filled stream")
    void successfulFindAllByForecastDateBetween() {
        var date = LocalDate.of(2019, 1, 1);
        var actual = forecastRepository.findAllByForecastDateBetween(date, date);
        assertEquals(EXPECTED_SIZE_BY_DATE, actual.count());
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(): a successful call returns a filled stream")
    void successfulFindAllByForecastDateBetweenAndForecastTimeGreaterThanEqual() {
        var date = LocalDate.of(2019, 1, 1);
        var afterSixTime = LocalTime.of(18, 0);
        var actual = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(date, date, afterSixTime);
        assertEquals(EXPECTED_SIZE_BY_DATE_AND_TIME, actual.count());
    }

    @Test
    @DisplayName("save(): a successful call returns a saved entity")
    void successfulSave() {
        var actual = assertDoesNotThrow(() -> forecastRepository.save(testEntity));

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getModified());
        assertNotNull(actual.getActive());
    }

    @Test
    @DisplayName("save(): a successful call return an updated entity")
    void successfulUpdateSave() {
        var entity = assertDoesNotThrow(() -> forecastRepository.save(testEntity));

        var expectedModified = entity.getModified();
        var expectedIndex = entity.getIndex();

        entity.setIndex(IndexType.EXTREME_STORM);
        var actual = assertDoesNotThrow(() -> forecastRepository.save(entity));

        assertNotEquals(expectedIndex, actual.getIndex());
        assertNotEquals(expectedModified, actual.getModified());
    }

    @Transactional
    @Test
    @DisplayName("delete(): a successful call changes a status of an entity")
    void successfulDelete() {
        var entity = assertDoesNotThrow(() -> forecastRepository.save(testEntity));

        var expectedModified = entity.getModified();
        assertDoesNotThrow(() -> forecastRepository.delete(entity));
        forecastRepository.flush();

        assertEquals(ActiveType.DELETED, entity.getActive());
        assertNotEquals(expectedModified, entity.getModified());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findAllByForecastDateBetween(): a unsuccessful call returns an empty stream")
    void unsuccessfulFindAllByForecastDateBetween() {
        var date = LocalDate.of(1, 1, 1);
        var actual = forecastRepository.findAllByForecastDateBetween(date, date);
        assertNotEquals(EXPECTED_EMPTY, actual.count());
    }

    @Transactional(readOnly = true)
    @Test
    @DisplayName("findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(): a unsuccessful call returns an empty stream")
    void unsuccessfulFindAllByForecastDateBetweenAndForecastTimeGreaterThanEqual() {
        var date = LocalDate.of(2019, 1, 1);
        var afterSixTime = LocalTime.of(18, 0);
        var actual = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(date, date, afterSixTime);
        assertNotEquals(EXPECTED_EMPTY, actual.count());
    }
}