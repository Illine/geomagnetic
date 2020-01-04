package net.c7j.weather.geomagnetic.mapper;

import net.c7j.weather.geomagnetic.mapper.impl.MobileForecastDtoMapper;
import net.c7j.weather.geomagnetic.model.dto.ForecastDto;
import net.c7j.weather.geomagnetic.test.tag.LocalTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneOffset;

import static net.c7j.weather.geomagnetic.test.helper.generator.CommonGeneratorHelper.generateLong;
import static net.c7j.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateForecastDto;
import static org.junit.jupiter.api.Assertions.*;

@LocalTest
@DisplayName("ForecastDtoMapper Local Test")
class MobileForecastDtoMapperTest {

    private static final Long DEFAULT_ID = generateLong();

    @Autowired
    private MobileForecastDtoMapper forecastMobileMapper;

    private ForecastDto testForecastDto;

    @BeforeEach
    void setUp() {
        testForecastDto = generateForecastDto(DEFAULT_ID);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): returns a valid destination")
    void successfulConvertToDestination() {
        var actual = forecastMobileMapper.convertToDestination(testForecastDto);
        assertNotNull(actual);
        assertEquals(DEFAULT_ID, actual.getId());
        assertEquals(testForecastDto.getIndex(), actual.getIndex());
        assertEquals(Instant.ofEpochMilli(actual.getTime()).atZone(ZoneOffset.UTC).toLocalDate(), testForecastDto.getForecastDate());
        assertEquals(Instant.ofEpochMilli(actual.getTime()).atZone(ZoneOffset.UTC).toLocalTime(), testForecastDto.getForecastTime());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): throws IllegalArgumentException when an arg destination is null")
    void unsuccessfulConvertToDestinationNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastMobileMapper.convertToDestination(null));
    }
}