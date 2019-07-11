package net.c7j.weather.geomagnetic.mapper;

import net.c7j.weather.geomagnetic.dao.base.IndexType;
import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        var dto = forecastDtoMapper.convertToDto(testEntity);
        assertTrue(dto.isPresent());

        var actual = dto.get();
        assertEquals(EXPECTED_MILLIS_BY_TESTING_DATE_TIME, actual.getTime());
        assertEquals(testEntity.getIndex(), actual.getIndex());
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a valid dto when an update")
    void successfulUpdateConvertToDto() {
        var toUpdate = GeneratorHelper.generateForecastDto(IndexType.EXTREME_STORM, false);
        var actual = forecastDtoMapper.convertToDto(testEntity, toUpdate).orElseThrow();
        assertNotEquals(testDto, actual);
        assertEquals(testEntity.getIndex(), actual.getIndex());
        assertNotNull(actual.getTime());
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a stream when arg is a stream")
    void successfulStreamConvertToDto() {
        var dtoStream = forecastDtoMapper.convertToDto(Stream.of(testEntity));
        assertNotNull(dtoStream);

        var actual = dtoStream.collect(Collectors.toList());
        assertFalse(actual.isEmpty());
        assertEquals(testEntity.getIndex(), actual.get(0).getIndex());
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a stream when arg is a collection")
    void successfulCollectionConvertToDto() {
        var dtoStream = forecastDtoMapper.convertToDto(Collections.singletonList(testEntity));
        assertNotNull(dtoStream);

        var actual = dtoStream.collect(Collectors.toList());
        assertFalse(actual.isEmpty());
        assertEquals(testEntity.getIndex(), actual.get(0).getIndex());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("convertToDto(): an unsuccessful call returns an empty optional when an arg dto is null")
    void unsuccessfulEmptyConvertToDto() {
        var actual = assertDoesNotThrow(() -> forecastDtoMapper.convertToDto((ForecastEntity) null));
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("convertToDto(): an unsuccessful call returns an unchanged dto when an arg entity is null")
    void unsuccessfulNullEntityConvertToDto() {
        var optionalDto = assertDoesNotThrow(() -> forecastDtoMapper.convertToDto(null, testDto));
        assertTrue(optionalDto.isPresent());

        var actual = optionalDto.get();
        assertEquals(testDto, actual);
    }

    @Test
    @DisplayName("convertToDto(): an unsuccessful call returns a new dto when an arg dto is null")
    void unsuccessfulNullDtoConvertToDto() {
        var optionalDto = assertDoesNotThrow(() -> forecastDtoMapper.convertToDto(testEntity, null));
        assertTrue(optionalDto.isPresent());

        var actual = optionalDto.get();
        assertEquals(testEntity.getIndex(), actual.getIndex());
    }
}