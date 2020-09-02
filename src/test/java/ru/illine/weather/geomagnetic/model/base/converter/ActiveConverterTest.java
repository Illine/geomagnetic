package ru.illine.weather.geomagnetic.model.base.converter;

import ru.illine.weather.geomagnetic.model.base.ActiveType;
import ru.illine.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
@DisplayName("ActiveConverter Unit Test")
class ActiveConverterTest {

    private ActiveConverter activeConverter;

    @BeforeEach
    void setup() {
        activeConverter = new ActiveConverter();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDatabaseColumn(): returns all valid values")
    void successfulConvertToDatabaseColumn() {
        var expected = Set.of(Boolean.TRUE, Boolean.FALSE);
        var actual = EnumSet.allOf(ActiveType.class)
                .stream()
                .map(activeConverter::convertToDatabaseColumn)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convertToEntityAttribute(): returns all valid values")
    void successfulConvertToEntityAttribute() {
        var expected = Set.of(ActiveType.ENABLED, ActiveType.DISABLED);
        var actual = Set.of(Boolean.TRUE, Boolean.FALSE)
                .stream()
                .map(activeConverter::convertToEntityAttribute)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
}