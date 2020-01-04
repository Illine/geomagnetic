package net.c7j.weather.geomagnetic.mapper;

import net.c7j.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import net.c7j.weather.geomagnetic.model.dto.TxtForecastDto;
import net.c7j.weather.geomagnetic.test.tag.LocalTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static net.c7j.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.DEFAULT_DATE;
import static net.c7j.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateTxtForecastDto;
import static org.junit.jupiter.api.Assertions.*;

@LocalTest
@DisplayName("TxtForecastDtoMapper Local Test")
class TxtForecastDtoMapperTest {

    @Autowired
    private TxtForecastDtoMapper txtForecastDtoMapper;

    private TxtForecastDto textDestination;

    @BeforeEach
    void setUp() {
        textDestination = generateTxtForecastDto();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): returns a valid dto")
    void successfulConvertToDestination() {
        var actual = txtForecastDtoMapper.convertToSource(textDestination);
        assertNotNull(actual);
        assertEquals(textDestination.getIndex(), actual.getIndex());
        assertEquals(DEFAULT_DATE, actual.getForecastDate());
        assertEquals(textDestination.getInterval().getTimeInterval(), actual.getForecastTime());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("convertToDestination(): throws IllegalArgumentException when an arg source is null")
    void unsuccessfulConvertToDestinationNull() {
        assertThrows(IllegalArgumentException.class, () -> txtForecastDtoMapper.convertToSource(null));
    }
}