package com.illine.weather.geomagnetic.mapper;

import com.illine.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.helper.generator.EntityGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateForecastDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringMockTest
@DisplayName("ForecastDtoMapper Spring Mock Test")
class ForecastDtoMapperTest {

    @Autowired
    private ForecastDtoMapper forecastMapper;

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): returns a valid destination")
    void successfulConvertToDestination() {
        var source = EntityGeneratorHelper.generateForecastEntity();
        var actual = forecastMapper.convertToDestination(source);
        assertNotNull(actual);
        Assertions.assertEquals(source.getIndex(), actual.getIndex());
        Assertions.assertEquals(source.getForecastDate(), actual.getForecastDate());
        Assertions.assertEquals(source.getForecastTime(), actual.getForecastTime());
    }

    @Test
    @DisplayName("convertToSource(): returns a valid source")
    void successfulConvertToSource() {
        var destination = DtoGeneratorHelper.generateForecastDto();
        var actual = forecastMapper.convertToSource(destination);
        assertNotNull(actual);
        Assertions.assertEquals(destination.getIndex(), actual.getIndex());
        Assertions.assertEquals(destination.getForecastDate(), actual.getForecastDate());
        Assertions.assertEquals(destination.getForecastTime(), actual.getForecastTime());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): throws IllegalArgumentException when an arg destination is null")
    void unsuccessfulConvertToDestinationNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastMapper.convertToDestination(null));
    }

    @Test
    @DisplayName("convertToSource(): throws IllegalArgumentException when an arg source is null")
    void unsuccessfulConvertToSourceNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastMapper.convertToSource(null));
    }
}