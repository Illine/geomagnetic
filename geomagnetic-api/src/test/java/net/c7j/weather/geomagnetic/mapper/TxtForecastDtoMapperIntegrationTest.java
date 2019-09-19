package net.c7j.weather.geomagnetic.mapper;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.model.base.TimeIntervalType;
import net.c7j.weather.geomagnetic.model.dto.TxtForecastDto;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@DisplayName("TxtForecastDtoMapper Integration Test")
class TxtForecastDtoMapperIntegrationTest {

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final LocalDateTime TESTING_LOCAL_DATE_TIME = LocalDateTime.of(2019, 1, 1, 0, 0, 0);

    @Autowired
    private TxtForecastDtoMapper txtForecastDtoMapper;

    private ForecastEntity testEntity;
    private TxtForecastDto textDto;

    @BeforeEach
    void setUp() {
        testEntity = GeneratorHelper.generateForecastEntity(DEFAULT_ENTITY_ID);
        textDto = GeneratorHelper.generateTxtForecastDto();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDto(): a successful call returns a valid dto")
    void successfulConvertToDto() {
        var entity = txtForecastDtoMapper.convertToEntity(textDto);
        assertTrue(entity.isPresent());

        var actual = entity.get();
        assertEquals(textDto.getIndex(), actual.getIndex());
        assertEquals(TESTING_LOCAL_DATE_TIME.toLocalDate(), actual.getForecastDate());
        assertEquals(textDto.getInterval().getTimeInterval(), actual.getForecastTime());
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a valid entity when an update")
    void successfulUpdateConvertToDto() {
        var dto = GeneratorHelper.generateTxtForecastDto(TimeIntervalType.INTERVAL_21_00, IndexType.EXTREME_STORM);

        var actual = txtForecastDtoMapper.convertToEntity(dto, GeneratorHelper.generateForecastEntity(1L)).orElseThrow();
        assertNotEquals(testEntity, actual);
        assertEquals(dto.getIndex(), actual.getIndex());
        assertEquals(dto.getInterval().getTimeInterval(), actual.getForecastTime());
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a stream when arg is a stream")
    void successfulStreamConvertToDto() {
        var dtoSet = GeneratorHelper.generateSetTxtForecastDto();

        var entityStream = txtForecastDtoMapper.convertToEntity(dtoSet.stream());
        assertNotNull(entityStream);

        var actual = entityStream.count();
        assertEquals(dtoSet.size(), actual);
    }

    @Test
    @DisplayName("convertToDto(): a successful call returns a stream when arg is a collection")
    void successfulCollectionConvertToDto() {
        var dtoSet = GeneratorHelper.generateSetTxtForecastDto();

        var entityStream = txtForecastDtoMapper.convertToEntity(dtoSet);
        assertNotNull(entityStream);

        var actual = entityStream.count();
        assertEquals(dtoSet.size(), actual);
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("convertToDto(): an unsuccessful call returns an empty optional when an arg dto is null")
    void unsuccessfulEmptyConvertToDto() {
        var actual = assertDoesNotThrow(() -> txtForecastDtoMapper.convertToEntity((TxtForecastDto) null));
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("convertToDto(): an unsuccessful call returns an unchanged dto when an arg entity is null")
    void unsuccessfulNullEntityConvertToDto() {

        var optionalDto = assertDoesNotThrow(() -> txtForecastDtoMapper.convertToEntity(null, testEntity));
        assertTrue(optionalDto.isPresent());

        var actual = optionalDto.get();
        assertEquals(GeneratorHelper.generateForecastEntity(1L), actual);
    }

    @Test
    @DisplayName("convertToDto(): an unsuccessful call returns a new dto when an arg dto is null")
    void unsuccessfulNullDtoConvertToDto() {
        var optionalDto = assertDoesNotThrow(() -> txtForecastDtoMapper.convertToEntity(textDto, null));
        assertTrue(optionalDto.isPresent());

        var actual = optionalDto.get();
        assertEquals(textDto.getIndex(), actual.getIndex());
    }
}