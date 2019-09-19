package net.c7j.weather.geomagnetic.model.base.converter;

import net.c7j.weather.geomagnetic.model.base.ActiveType;
import net.c7j.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
@DisplayName("ActiveConverter Unit Test")
class ActiveConverterUnitTest {

    private ActiveConverter activeConverter;

    @BeforeEach
    void setup() {
        activeConverter = new ActiveConverter();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDatabaseColumn(): a successful call returns all valid values")
    void successfulAllConvertToDatabaseColumn() {
        var expected = Set.of(Boolean.TRUE, Boolean.FALSE);
        var actual = EnumSet.allOf(ActiveType.class)
                .stream()
                .map(activeConverter::convertToDatabaseColumn)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convertToEntityAttribute(): a successful call returns all valid values")
    void successfulAllConvertToEntityAttribute() {
        var expected = Set.of(ActiveType.ENABLED, ActiveType.DELETED);
        var actual = Set.of(Boolean.TRUE, Boolean.FALSE)
                .stream()
                .map(activeConverter::convertToEntityAttribute)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
}