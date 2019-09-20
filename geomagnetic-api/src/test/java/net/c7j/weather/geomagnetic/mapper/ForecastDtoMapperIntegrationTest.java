package net.c7j.weather.geomagnetic.mapper;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.model.dto.ForecastDto;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@DisplayName("ForecastDtoMapper Integration Test")
class ForecastDtoMapperIntegrationTest {

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long EXPECTED_MILLIS_BY_TESTING_DATE_TIME = 1546300800L;

    @Autowired
    private ForecastDtoMapper forecastDtoMapper;

    private ForecastEntity testEntity;
    private ForecastDto testDto;

    @BeforeEach
    void setUp() {
        testEntity = GeneratorHelper.generateForecastEntity(DEFAULT_ENTITY_ID);
        testDto = GeneratorHelper.generateForecastDto(IndexType.EXTREME_STORM, false);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDto(): a successful call returns a valid dto")
    void successfulConvertToDto() {
        var actual = forecastDtoMapper.convertToDto(testEntity);
        assertNotNull(actual);
        assertEquals(EXPECTED_MILLIS_BY_TESTING_DATE_TIME, actual.getTime());
        assertEquals(testEntity.getIndex(), actual.getIndex());
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a valid dto when an update")
    void successfulUpdateConvertToDto() {
        var toUpdate = GeneratorHelper.generateForecastDto(IndexType.EXTREME_STORM, false);
        var actual = forecastDtoMapper.convertToDto(testEntity, toUpdate);
        assertNotEquals(testDto, actual);
        assertEquals(testEntity.getIndex(), actual.getIndex());
        assertNotNull(actual.getTime());
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a collection of dtos when arg is a collection")
    void successfulCollectionConvertToDto() {
        var exceptedSize = 1;
        var actual = forecastDtoMapper.convertToDtos(Collections.singletonList(testEntity));
        assertFalse(actual.isEmpty());
        assertEquals(exceptedSize, actual.size());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("convertToDto(): an unsuccessful call throws IllegalArgumentException when an arg dto is null")
    void unsuccessfulEmptyConvertToDto() {
        assertThrows(IllegalArgumentException.class, () -> forecastDtoMapper.convertToDto(null));
    }

    @Test
    @DisplayName("convertToDto(): an unsuccessful call throws IllegalArgumentException when an arg entity is null")
    void unsuccessfulNullEntityConvertToDto() {
        assertThrows(IllegalArgumentException.class, () -> forecastDtoMapper.convertToDto(null, testDto));
    }

    @Test
    @DisplayName("convertToDto(): an unsuccessful call throws IllegalArgumentException when an arg dto is null")
    void unsuccessfulNullDtoConvertToDto() {
        assertThrows(IllegalArgumentException.class, () -> forecastDtoMapper.convertToDto(testEntity, null));
    }
}