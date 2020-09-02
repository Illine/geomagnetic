package ru.illine.weather.geomagnetic.mapper;

import ru.illine.weather.geomagnetic.mapper.impl.MobileForecastDtoMapper;
import ru.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import ru.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@SpringMockTest
@DisplayName("ForecastDtoMapper Spring Mock Test")
class MobileForecastDtoMapperTest {

    @Autowired
    private MobileForecastDtoMapper forecastMobileMapper;

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): returns a valid destination")
    void successfulConvertToDestination() {
        var testForecastDto = DtoGeneratorHelper.generateForecastDto();
        var actual = forecastMobileMapper.convertToDestination(testForecastDto);
        assertNotNull(actual);
        Assertions.assertEquals(testForecastDto.getIndex(), actual.getIndex());
        assertEquals(Instant.ofEpochMilli(actual.getTime()).atZone(ZoneOffset.UTC).toLocalDate(), testForecastDto.getForecastDate());
        assertEquals(Instant.ofEpochMilli(actual.getTime()).atZone(ZoneOffset.UTC).toLocalTime(), testForecastDto.getForecastTime());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): throws IllegalArgumentException when an arg destination is null")
    void failConvertToDestinationNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastMobileMapper.convertToDestination(null));
    }
}