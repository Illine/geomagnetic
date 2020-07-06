package com.illine.weather.geomagnetic.mapper;

import com.illine.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import com.illine.weather.geomagnetic.model.dto.TxtForecastDto;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringMockTest
@DisplayName("TxtForecastDtoMapper Spring Mock Test")
class TxtForecastDtoMapperTest {

    @Autowired
    private TxtForecastDtoMapper txtForecastDtoMapper;

    private TxtForecastDto textDestination;

    @BeforeEach
    void setUp() {
        textDestination = DtoGeneratorHelper.generateTxtForecastDto();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): returns a valid dto")
    void successfulConvertToDestination() {
        var actual = txtForecastDtoMapper.convertToSource(textDestination);
        assertNotNull(actual);
        Assertions.assertEquals(textDestination.getIndex(), actual.getIndex());
        Assertions.assertEquals(DtoGeneratorHelper.DEFAULT_DATE, actual.getForecastDate());
        Assertions.assertEquals(textDestination.getInterval().getTimeInterval(), actual.getForecastTime());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): throws IllegalArgumentException when an arg source is null")
    void failConvertToDestinationNull() {
        assertThrows(IllegalArgumentException.class, () -> txtForecastDtoMapper.convertToSource(null));
    }
}