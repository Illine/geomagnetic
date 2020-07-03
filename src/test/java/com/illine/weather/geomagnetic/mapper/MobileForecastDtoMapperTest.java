package com.illine.weather.geomagnetic.mapper;

import com.illine.weather.geomagnetic.mapper.impl.MobileForecastDtoMapper;
import com.illine.weather.geomagnetic.model.dto.ForecastDto;
import com.illine.weather.geomagnetic.test.helper.generator.CommonGeneratorHelper;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneOffset;

import static com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateForecastDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringMockTest
@DisplayName("ForecastDtoMapper Spring Mock Test")
class MobileForecastDtoMapperTest {

    private static final Long DEFAULT_ID = CommonGeneratorHelper.generateLong();

    @Autowired
    private MobileForecastDtoMapper forecastMobileMapper;

    private ForecastDto testForecastDto;

    @BeforeEach
    void setUp() {
        testForecastDto = DtoGeneratorHelper.generateForecastDto(DEFAULT_ID);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): returns a valid destination")
    void successfulConvertToDestination() {
        var actual = forecastMobileMapper.convertToDestination(testForecastDto);
        assertNotNull(actual);
        Assertions.assertEquals(DEFAULT_ID, actual.getId());
        Assertions.assertEquals(testForecastDto.getIndex(), actual.getIndex());
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