package com.illine.weather.geomagnetic.client;

import com.illine.weather.geomagnetic.config.property.RestProperties;
import com.illine.weather.geomagnetic.exception.SwpcNoaaException;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringMockTest
@DisplayName("SwpcNoaaClient Spring Mock Test")
class SwpcNoaaClientTest {

    private static final String MOCK_BODY = "Mock";

    @Mock
    private RestTemplate swpcNoaaRestTemplateMock;

    @Autowired
    private RestProperties properties;

    @Autowired
    private SwpcNoaaClient swpcNoaaClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(swpcNoaaClient, "swpcNoaaRestTemplate", swpcNoaaRestTemplateMock);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(swpcNoaaRestTemplateMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("get3DayGeomagneticForecast(): a successful call returns a HttpStatus.OK status and a body is not blank")
    void successfulGet3DayGeomagneticForecast() {
        when(swpcNoaaRestTemplateMock.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(MOCK_BODY));
        var actual = assertDoesNotThrow(swpcNoaaClient::get3DayGeomagneticForecast);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(StringUtils.isNotBlank(actual.getBody()));
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("get3DayGeomagneticForecast(): a fail call after retry")
    void failGet3DayGeomagneticForecast() {
        when(swpcNoaaRestTemplateMock.getForEntity(anyString(), any())).thenThrow(new RestClientException("Some error status http"));
        assertThrows(SwpcNoaaException.class, swpcNoaaClient::get3DayGeomagneticForecast);
        verify(swpcNoaaRestTemplateMock, times(properties.getRetry().getMaxAttempts())).getForEntity(anyString(), any());
    }
}