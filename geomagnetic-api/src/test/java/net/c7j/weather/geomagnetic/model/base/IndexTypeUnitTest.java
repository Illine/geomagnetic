package net.c7j.weather.geomagnetic.model.base;

import net.c7j.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@UnitTest
@DisplayName("IndexType Unit Test")
class IndexTypeUnitTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("indexOf(): a successful call returns 'NONE' when '0'")
    void successful0IndexOf() {
        assertEquals(IndexType.NONE, IndexType.indexOf(0));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'LOW' when '1'")
    void successful1IndexOf() {
        assertEquals(IndexType.LOW, IndexType.indexOf(1));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'QUITE' when '2'")
    void successful2IndexOf() {
        assertEquals(IndexType.QUITE, IndexType.indexOf(2));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'UNSETTLED' when '3'")
    void successful3IndexOf() {
        assertEquals(IndexType.UNSETTLED, IndexType.indexOf(3));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'ACTIVE' when '4'")
    void successful4IndexOf() {
        assertEquals(IndexType.ACTIVE, IndexType.indexOf(4));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'MINOR_STORM' when '5'")
    void successful5IndexOf() {
        assertEquals(IndexType.MINOR_STORM, IndexType.indexOf(5));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'MODERATE_STORM' when '6'")
    void successful6IndexOf() {
        assertEquals(IndexType.MODERATE_STORM, IndexType.indexOf(6));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'STRONG_STORM' when '7'")
    void successful7IndexOf() {
        assertEquals(IndexType.STRONG_STORM, IndexType.indexOf(7));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'SEVERE_STORM' when '8'")
    void successful8IndexOf() {
        assertEquals(IndexType.SEVERE_STORM, IndexType.indexOf(8));
    }

    @Test
    @DisplayName("indexOf(): a successful call returns 'EXTREME_STORM' when '9'")
    void successful9IndexOf() {
        assertEquals(IndexType.EXTREME_STORM, IndexType.indexOf(9));
    }

    @DisplayName("toString(): a successful call returns a correct value")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void successfulToString(Integer arg) {
        var index = IndexType.indexOf(arg);

        var expected = String.valueOf(arg);
        var actual = index.toString();
        assertEquals(expected, actual);
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("indexOf(): a unsuccessful call throws NoSuchElementException when a 'null' Integer")
    void unsuccessfulIntegerNullIndexOf() {
        assertThrows(NoSuchElementException.class, () -> IndexType.indexOf((Integer) null));
    }

    @Test
    @DisplayName("indexOf(): a unsuccessful call throws IllegalArgumentException when a 'null' String")
    void unsuccessfulStringNullIndexOf() {
        assertThrows(IllegalArgumentException.class, () -> IndexType.indexOf((String) null));
    }

    @Test
    @DisplayName("indexOf(): a unsuccessful call throws IllegalArgumentException when an empty String")
    void unsuccessfulStringEmptyIndexOf() {
        assertThrows(IllegalArgumentException.class, () -> IndexType.indexOf(""));
    }

    @Test
    @DisplayName("indexOf(): a unsuccessful call throws NoSuchElementException when an invalid value")
    void unsuccessfulInvalidIndexOf() {
        assertThrows(NoSuchElementException.class, () -> IndexType.indexOf(-1));
    }
}