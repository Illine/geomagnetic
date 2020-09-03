package ru.illine.weather.geomagnetic.mapper;

import ru.illine.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import ru.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import ru.illine.weather.geomagnetic.test.helper.generator.EntityGeneratorHelper;
import ru.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
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
        assertEquals(source.getIndex(), actual.getIndex());
        assertEquals(source.getForecastDate(), actual.getForecastDate());
        assertEquals(source.getForecastTime(), actual.getForecastTime());
    }

    @Test
    @DisplayName("convertToDestinations(): returns a valid collection of destinations")
    void successfulConvertToDestinations() {
        var source = EntityGeneratorHelper.generateForecastEntity();
        var sources = Collections.singleton(source);
        var actual = forecastMapper.convertToDestinations(sources);
        assertNotNull(actual);
        assertThat(actual, hasSize(sources.size()));
        assertThat(actual, Matchers.hasItem(DtoGeneratorHelper.generateForecastDto()));
    }

    @Test
    @DisplayName("convertToSource(): returns a valid source")
    void successfulConvertToSource() {
        var destination = DtoGeneratorHelper.generateForecastDto();
        var actual = forecastMapper.convertToSource(destination);
        assertNotNull(actual);
        assertEquals(destination.getIndex(), actual.getIndex());
        assertEquals(destination.getForecastDate(), actual.getForecastDate());
        assertEquals(destination.getForecastTime(), actual.getForecastTime());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): throws IllegalArgumentException when an arg destination is null")
    void failConvertToDestinationNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastMapper.convertToDestination(null));
    }

    @Test
    @DisplayName("convertToDestinations(): throws IllegalArgumentException when an arg destination is null")
    void failConvertToDestinationsNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastMapper.convertToDestinations(null));
    }

    @Test
    @DisplayName("convertToSource(): throws IllegalArgumentException when an arg source is null")
    void failConvertToSourceNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastMapper.convertToSource(null));
    }
}