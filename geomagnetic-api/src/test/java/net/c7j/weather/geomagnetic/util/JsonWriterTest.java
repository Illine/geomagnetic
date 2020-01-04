package net.c7j.weather.geomagnetic.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.c7j.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
@DisplayName("JsonWriter Unit Test")
class JsonWriterTest {

    private static final String JSON_PATTERN = "{\"value\":\"%s\"}";

    //  -----------------------   successful tests   -------------------------

    @DisplayName("toStringAsJson(): a successful call returns a correct json")
    @ParameterizedTest
    @ValueSource(strings = {"first", "second", "third"})
    void successfulToStringAsJson(String arg) {
        var actual = assertDoesNotThrow(() -> JsonWriter.toStringAsJson(new ValidClassToJson(arg)));
        assertEquals(String.format(JSON_PATTERN, arg), actual);
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("toStringAsJson(): an unsuccessful call throws IllegalArgumentException when an arg is null")
    void unsuccessfulToStringAsJson() {
        assertThrows(IllegalArgumentException.class, () -> JsonWriter.toStringAsJson(null));
    }

    @DisplayName("toStringAsJson(): an unsuccessful call returns 'unknown' when an arg class is invalid")
    @ParameterizedTest
    @ValueSource(strings = {"first", "second", "third"})
    void unsuccessfulInvalidToStringAsJson(String arg) {
        var actual = JsonWriter.toStringAsJson(new InvalidClassToJson(arg));
        assertNotEquals(String.format(JSON_PATTERN, arg), actual);
    }

    @Getter
    @RequiredArgsConstructor
    private static class ValidClassToJson {

        private final String value;

    }

    @RequiredArgsConstructor
    private static class InvalidClassToJson {

        private final String value;

    }
}