package net.c7j.weather.geomagnetic.dao.base.converter;

import net.c7j.weather.geomagnetic.dao.base.IndexType;
import net.c7j.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.c7j.weather.geomagnetic.dao.base.IndexType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
@DisplayName("IndexConverter Unit Test")
class IndexConverterUnitTest {

    private IndexConverter indexConverter;

    @BeforeEach
    void setup() {
        indexConverter = new IndexConverter();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDatabaseColumn(): a successful call returns all valid values")
    void successfulAllConvertToDatabaseColumn() {
        var expected = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        var actual = EnumSet.allOf(IndexType.class)
                .stream()
                .map(indexConverter::convertToDatabaseColumn)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("convertToEntityAttribute(): a successful call returns all valid values")
    void successfulAllConvertToEntityAttribute() {
        var expected = Set.of(
                NONE, LOW, QUITE, UNSETTLED, ACTIVE, MINOR_STORM,
                MODERATE_STORM, STRONG_STORM, SEVERE_STORM, EXTREME_STORM
        );
        var actual = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .stream()
                .map(indexConverter::convertToEntityAttribute)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
}