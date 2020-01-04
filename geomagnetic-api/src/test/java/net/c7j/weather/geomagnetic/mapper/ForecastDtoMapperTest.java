package net.c7j.weather.geomagnetic.mapper;

import net.c7j.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import net.c7j.weather.geomagnetic.test.tag.LocalTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static net.c7j.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateForecastDto;
import static net.c7j.weather.geomagnetic.test.helper.generator.EntityGeneratorHelper.generateForecastEntity;
import static org.junit.jupiter.api.Assertions.*;

@LocalTest
@DisplayName("ForecastDtoMapper Local Test")
class ForecastDtoMapperTest {

    @Autowired
    private ForecastDtoMapper forecastMapper;

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): returns a valid destination")
    void successfulConvertToDestination() {
        var source = generateForecastEntity();
        var actual = forecastMapper.convertToDestination(source);
        assertNotNull(actual);
        assertEquals(source.getIndex(), actual.getIndex());
        assertEquals(source.getForecastDate(), actual.getForecastDate());
        assertEquals(source.getForecastTime(), actual.getForecastTime());
    }

    @Test
    @DisplayName("convertToSource(): returns a valid source")
    void successfulConvertToSource() {
        var destination = generateForecastDto();
        var actual = forecastMapper.convertToSource(destination);
        assertNotNull(actual);
        assertEquals(destination.getIndex(), actual.getIndex());
        assertEquals(destination.getForecastDate(), actual.getForecastDate());
        assertEquals(destination.getForecastTime(), actual.getForecastTime());
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