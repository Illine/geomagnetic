package ru.illine.weather.geomagnetic.rest.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import ru.illine.weather.geomagnetic.exception.SwpcNoaaException;
import ru.illine.weather.geomagnetic.model.dto.BaseResponse;
import ru.illine.weather.geomagnetic.service.EtlService;
import ru.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringIntegrationTest
@DisplayName("ApplicationController Spring Integration Test")
class ServiceControllerTest extends AbstractControllerTest {

    private static final String URI_UPDATE_FORECASTS = "/services/forecasts";

    @Mock
    private EtlService forecastEtlServiceMock;

    @Autowired
    private ServiceController controller;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(controller, "forecastEtlService", forecastEtlServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(forecastEtlServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("updateForecasts(): returns 200 and a success body")
    void successfulUpdateForecasts() {
        doNothing().when(forecastEtlServiceMock).updateForecasts();
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, buildDefaultHeaders(), URI_UPDATE_FORECASTS));
        assertCall().accept(actual, HttpStatus.OK);
        verify(forecastEtlServiceMock).updateForecasts();
    }

    @Test
    @DisplayName("updateForecasts(): returns 200 when a security is disabled")
    void successfulUpdateForecastsSecurityDisabled() {
        apiKeyProperties.setEnabled(false);
        doNothing().when(forecastEtlServiceMock).updateForecasts();
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, null, URI_UPDATE_FORECASTS));
        assertCall().accept(actual, HttpStatus.OK);
        verify(forecastEtlServiceMock).updateForecasts();
        apiKeyProperties.setEnabled(true);
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("updateForecasts(): returns 503 when SWPC NOAA isn't availability")
    void failUpdateForecastsServiceUnavailable() {
        Mockito.doThrow(SwpcNoaaException.class).when(forecastEtlServiceMock).updateForecasts();
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, buildDefaultHeaders(), URI_UPDATE_FORECASTS));
        assertCall().accept(actual, HttpStatus.SERVICE_UNAVAILABLE);
        verify(forecastEtlServiceMock).updateForecasts();
    }

    @Test
    @DisplayName("updateForecasts(): returns 500 when an any unknown exception is thrown")
    void failUpdateForecastsInternalServerError() {
        doThrow(RuntimeException.class).when(forecastEtlServiceMock).updateForecasts();
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, buildDefaultHeaders(), URI_UPDATE_FORECASTS));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastEtlServiceMock).updateForecasts();
    }

    @Test
    @DisplayName("updateForecasts(): returns 403 when a request isn't authorized")
    void failUpdateForecastsForbidden() {
        var headers = new HttpHeaders();
        headers.set(apiKeyProperties.getHeaderName(), UUID.randomUUID().toString());
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, headers, URI_UPDATE_FORECASTS));
        assertCall().accept(actual, HttpStatus.FORBIDDEN);
        verify(forecastEtlServiceMock, never()).updateForecasts();
    }
}