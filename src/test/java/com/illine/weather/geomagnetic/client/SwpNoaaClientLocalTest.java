package com.illine.weather.geomagnetic.client;

import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringMockTest
@DisplayName("SwpNoaaClient Spring Mock Test")
class SwpNoaaClientLocalTest {

    @Spy
    @Autowired
    private SwpNoaaClient swpcNoaaClientSpy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("get3DayGeomagForecast(): a successful call returns a HttpStatus.OK status and a body is not blank")
    void successfulGet3DayGeomagForecast() {
        var actual = swpcNoaaClientSpy.get3DayGeomagForecast();
        verify(swpcNoaaClientSpy).get3DayGeomagForecast();
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(StringUtils.isNotBlank(actual.getBody()));
    }
}