package com.illine.weather.geomagnetic.model.base;

import com.illine.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@UnitTest
@DisplayName("IndexType Unit Test")
class IndexTypeTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("indexOf(): returns 'NONE' when '0'")
    void successfulIndexOf0() {
        assertEquals(IndexType.NONE, IndexType.indexOf(0));
    }

    @Test
    @DisplayName("indexOf(): returns 'LOW' when '1'")
    void successfulIndexOf1() {
        assertEquals(IndexType.LOW, IndexType.indexOf(1));
    }

    @Test
    @DisplayName("indexOf(): returns 'QUITE' when '2'")
    void successfulIndexOf2() {
        assertEquals(IndexType.QUITE, IndexType.indexOf(2));
    }

    @Test
    @DisplayName("indexOf(): returns 'UNSETTLED' when '3'")
    void successfulIndexOf3() {
        assertEquals(IndexType.UNSETTLED, IndexType.indexOf(3));
    }

    @Test
    @DisplayName("indexOf(): returns 'ACTIVE' when '4'")
    void successfulIndexOf4() {
        assertEquals(IndexType.ACTIVE, IndexType.indexOf(4));
    }

    @Test
    @DisplayName("indexOf(): returns 'MINOR_STORM' when '5'")
    void successfulIndexOf5() {
        assertEquals(IndexType.MINOR_STORM, IndexType.indexOf(5));
    }

    @Test
    @DisplayName("indexOf(): returns 'MODERATE_STORM' when '6'")
    void successfulIndexOf6() {
        assertEquals(IndexType.MODERATE_STORM, IndexType.indexOf(6));
    }

    @Test
    @DisplayName("indexOf(): returns 'STRONG_STORM' when '7'")
    void successfulIndexOf7() {
        assertEquals(IndexType.STRONG_STORM, IndexType.indexOf(7));
    }

    @Test
    @DisplayName("indexOf(): returns 'SEVERE_STORM' when '8'")
    void successfulIndexOf8() {
        assertEquals(IndexType.SEVERE_STORM, IndexType.indexOf(8));
    }

    @Test
    @DisplayName("indexOf(): returns 'EXTREME_STORM' when '9'")
    void successfulIndexOf9() {
        assertEquals(IndexType.EXTREME_STORM, IndexType.indexOf(9));
    }

    @DisplayName("toString(): returns a correct value")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void successfulToString(Integer arg) {
        var index = IndexType.indexOf(arg);

        var expected = String.valueOf(arg);
        var actual = index.toString();
        assertEquals(expected, actual);
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("indexOf(): throws NoSuchElementException when a 'null' Integer")
    void failIndexOfIntegerNull() {
        assertThrows(NoSuchElementException.class, () -> IndexType.indexOf((Integer) null));
    }

    @Test
    @DisplayName("indexOf(): throws IllegalArgumentException when a 'null' String")
    void failIndexOfStringNull() {
        assertThrows(IllegalArgumentException.class, () -> IndexType.indexOf((String) null));
    }

    @Test
    @DisplayName("indexOf(): throws IllegalArgumentException when an empty String")
    void failIndexOfStringEmpty() {
        assertThrows(IllegalArgumentException.class, () -> IndexType.indexOf(""));
    }

    @Test
    @DisplayName("indexOf(): throws NoSuchElementException when an invalid value")
    void failIndexOf() {
        assertThrows(NoSuchElementException.class, () -> IndexType.indexOf(-1));
    }
}