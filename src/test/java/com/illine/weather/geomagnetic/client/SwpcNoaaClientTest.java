package com.illine.weather.geomagnetic.client;

import com.illine.weather.geomagnetic.config.property.RestRetryProperties;
import com.illine.weather.geomagnetic.exception.SwpcNoaaException;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringIntegrationTest
@DisplayName("SwpcNoaaClient Spring Integration Test")
class SwpcNoaaClientTest {

    @Autowired
    private RestRetryProperties properties;

    @Autowired
    private RestTemplate swpcNoaaRestTemplate;

    @Autowired
    private SwpcNoaaClient swpcNoaaClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("get3DayGeomagneticForecast(): a successful call returns a HttpStatus.OK status and a body is not blank")
    void successfulGet3DayGeomagneticForecast() {
        var actual = assertDoesNotThrow(swpcNoaaClient::get3DayGeomagneticForecast);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(StringUtils.isNotBlank(actual.getBody()));
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("get3DayGeomagneticForecast(): a fail call after retry")
    void failGet3DayGeomagneticForecast() {
        var swpcNoaaRestTemplateMock = mock(RestTemplate.class);
        ReflectionTestUtils.setField(swpcNoaaClient, "swpcNoaaRestTemplate", swpcNoaaRestTemplateMock);

        when(swpcNoaaRestTemplateMock.getForEntity(anyString(), any())).thenThrow(new RestClientException("Some error status http"));
        assertThrows(SwpcNoaaException.class, swpcNoaaClient::get3DayGeomagneticForecast);
        verify(swpcNoaaRestTemplateMock, times(properties.getMaxAttempts())).getForEntity(anyString(), any());

        ReflectionTestUtils.setField(swpcNoaaClient, "swpcNoaaRestTemplate", swpcNoaaRestTemplate);
    }
}