package net.c7j.weather.geomagnetic.client;

import net.c7j.weather.geomagnetic.test.helper.AssertionHelper;
import net.c7j.weather.geomagnetic.test.helper.FileHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@IntegrationTest
@DisplayName("SwpNoaaClient Integration Test")
class SwpNoaaClientIntegrationTest {

    private static final String EXPECTED_SWPC_URI = "https://services.swpc.noaa.gov/text/3-day-geomag-forecast.txt";
    private static final String DEFAULT_TXT_FORECAST_PATH = "forecast/Geomagnetic_Forecast_0.txt";

    @Spy
    private SwpNoaaClient swpcNoaaClientSpy;

    @Mock
    @Qualifier("swpNoaaRestTemplate")
    private RestTemplate swpNoaaRestTemplateMock;

    private String expectedTextForecast;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(swpcNoaaClientSpy, "swpNoaaRestTemplate", swpNoaaRestTemplateMock);
        var path = FileHelper.getPath(DEFAULT_TXT_FORECAST_PATH, getClass());
        expectedTextForecast = FileHelper.getFileContent(path);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("get3DayGeomagForecast(): a successful call returns a HttpStatus.OK status and a body is not blank")
    void successfulGet3DayGeomagForecast() {
        var expected = HttpStatus.OK;
        when(swpNoaaRestTemplateMock.getForEntity(EXPECTED_SWPC_URI, String.class)).thenReturn(ResponseEntity.ok(expectedTextForecast));

        var actual = swpcNoaaClientSpy.get3DayGeomagForecast();
        AssertionHelper.assertCall(StringUtils::isBlank).accept(actual, expected);
        verify(swpcNoaaClientSpy).get3DayGeomagForecast();
    }

    @Test
    @DisplayName("get3DayGeomagForecast(): a successful call returns a valid text forecast")
    void successfulValidGet3DayGeomagForecast() {
        var expected = HttpStatus.OK;
        when(swpNoaaRestTemplateMock.getForEntity(EXPECTED_SWPC_URI, String.class)).thenReturn(ResponseEntity.ok(expectedTextForecast));

        var actual = swpcNoaaClientSpy.get3DayGeomagForecast();
        AssertionHelper.assertCall(StringUtils::isBlank).accept(actual, expected);
        verify(swpcNoaaClientSpy).get3DayGeomagForecast();
        assertEquals(expectedTextForecast, actual.getBody());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("get3DayGeomagForecast(): an unsuccessful call returns a body is blank")
    void unsuccessfulEmptyGet3DayGeomagForecast() {
        var expected = HttpStatus.OK;
        when(swpNoaaRestTemplateMock.getForEntity(EXPECTED_SWPC_URI, String.class)).thenReturn(new ResponseEntity<>(expected));

        AssertionHelper.assertCall(StringUtils::isNotBlank).accept(swpcNoaaClientSpy.get3DayGeomagForecast(), expected);
        verify(swpcNoaaClientSpy).get3DayGeomagForecast();
    }

    @Test
    @DisplayName("get3DayGeomagForecast(): an unsuccessful call returns a HttpStatus.BAD_GATEWAY and a body is blank")
    void unsuccessful502Get3DayGeomagForecast() {
        var expected = HttpStatus.BAD_GATEWAY;
        when(swpNoaaRestTemplateMock.getForEntity(EXPECTED_SWPC_URI, String.class)).thenReturn(new ResponseEntity<>(expected));

        AssertionHelper.assertCall(StringUtils::isNotBlank).accept(swpcNoaaClientSpy.get3DayGeomagForecast(), expected);
        verify(swpcNoaaClientSpy).get3DayGeomagForecast();
    }

    @Test
    @DisplayName("get3DayGeomagForecast(): an unsuccessful call returns a HttpStatus.GATEWAY_TIMEOUT and a body is blank")
    void unsuccessful504Get3DayGeomagForecast() {
        var expected = HttpStatus.GATEWAY_TIMEOUT;
        when(swpNoaaRestTemplateMock.getForEntity(EXPECTED_SWPC_URI, String.class)).thenReturn(new ResponseEntity<>(expected));

        AssertionHelper.assertCall(StringUtils::isNotBlank).accept(swpcNoaaClientSpy.get3DayGeomagForecast(), expected);
        verify(swpcNoaaClientSpy).get3DayGeomagForecast();
    }
}