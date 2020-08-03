package com.illine.weather.geomagnetic.mapper;

import com.illine.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringIntegrationTest
@DisplayName("TxtForecastDtoMapper Spring Integration Test")
class TxtForecastDtoMapperTest {

    @Autowired
    private TxtForecastDtoMapper txtForecastDtoMapper;

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToSource(): returns a valid dto")
    void successfulConvertToSource() {
        var destination = DtoGeneratorHelper.generateTxtForecastDto();
        var actual = txtForecastDtoMapper.convertToSource(destination);
        assertNotNull(actual);
        Assertions.assertEquals(destination.getIndex(), actual.getIndex());
        Assertions.assertEquals(DtoGeneratorHelper.DEFAULT_DATE, actual.getForecastDate());
        Assertions.assertEquals(destination.getInterval().getTimeInterval(), actual.getForecastTime());
    }

    @Test
    @DisplayName("convertToDestinations(): returns a valid collection of destinations")
    void successfulConvertToDestinations() {
        var destination = DtoGeneratorHelper.generateTxtForecastDto();
        var destinations = Collections.singleton(destination);
        var actual = txtForecastDtoMapper.convertToSources(destinations);
        assertNotNull(actual);
        assertThat(actual, hasSize(destinations.size()));
        assertThat(actual, hasItem(DtoGeneratorHelper.generateForecastDto(destination.getForecastDate(), destination.getInterval().getTimeInterval())));
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): throws IllegalArgumentException when an arg source is null")
    void failConvertToDestinationNull() {
        assertThrows(IllegalArgumentException.class, () -> txtForecastDtoMapper.convertToSource(null));
    }

    @Test
    @DisplayName("convertToDestinations(): throws IllegalArgumentException when an arg destination is null")
    void failConvertToDestinationsNull() {
        assertThrows(IllegalArgumentException.class, () -> txtForecastDtoMapper.convertToDestinations(null));
    }
}