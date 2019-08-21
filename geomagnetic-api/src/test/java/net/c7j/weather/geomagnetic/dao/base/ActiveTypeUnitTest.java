package net.c7j.weather.geomagnetic.dao.base;

import net.c7j.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

@UnitTest
@DisplayName("ActiveType Unit Test")
class ActiveTypeUnitTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("activeOf(): a successful call returns 'ENABLED' when 'true'")
    void successfulTrueActiveOf() {
        assertEquals(ActiveType.ENABLED, ActiveType.activeOf(true));
    }

    @Test
    @DisplayName("activeOf(): a successful call returns 'DELETED' when 'false'")
    void successfulFalseActiveOf() {
        assertEquals(ActiveType.DELETED, ActiveType.activeOf(false));
    }

    @Test
    @DisplayName("toString(): a successful call returns a valid string when 'ENABLED'")
    void successfulEnabledToString() {
        var expected = String.valueOf(true);
        assertEquals(expected, ActiveType.ENABLED.toString());
    }

    @Test
    @DisplayName("toString(): a successful call returns a valid string when 'DELETED'")
    void successfulDeletedToString() {
        var expected = String.valueOf(false);
        assertEquals(expected, ActiveType.DELETED.toString());
    }

}