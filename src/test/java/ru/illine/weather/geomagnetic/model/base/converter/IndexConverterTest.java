package ru.illine.weather.geomagnetic.model.base.converter;

import ru.illine.weather.geomagnetic.model.base.IndexType;
import ru.illine.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
@DisplayName("IndexConverter Unit Test")
class IndexConverterTest {

    private IndexConverter indexConverter;

    @BeforeEach
    void setup() {
        indexConverter = new IndexConverter();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("convertToDatabaseColumn(): returns all valid values")
    void successfulConvertToDatabaseColumn() {
        var expected = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        var actual = EnumSet.allOf(IndexType.class)
                .stream()
                .map(indexConverter::convertToDatabaseColumn)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("convertToEntityAttribute(): returns all valid values")
    void successfulConvertToEntityAttribute() {
        var expected = Set.of(
                IndexType.NONE, IndexType.LOW, IndexType.QUITE, IndexType.UNSETTLED, IndexType.ACTIVE, IndexType.MINOR_STORM,
                IndexType.MODERATE_STORM, IndexType.STRONG_STORM, IndexType.SEVERE_STORM, IndexType.EXTREME_STORM
        );
        var actual = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .stream()
                .map(indexConverter::convertToEntityAttribute)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
}