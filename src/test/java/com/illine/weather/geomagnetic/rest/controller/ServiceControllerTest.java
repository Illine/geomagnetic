package com.illine.weather.geomagnetic.rest.controller;

import com.illine.weather.geomagnetic.exception.SwpcNoaaException;
import com.illine.weather.geomagnetic.model.dto.BaseResponse;
import com.illine.weather.geomagnetic.service.EtlService;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

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
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, URI_UPDATE_FORECASTS));
        assertCall(true).accept(actual, HttpStatus.OK);
        verify(forecastEtlServiceMock).updateForecasts();
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("updateForecasts(): returns 503 when SWPC NOAA isn't availability")
    void failUpdateForecastsServiceUnavailable() {
        doThrow(SwpcNoaaException.class).when(forecastEtlServiceMock).updateForecasts();
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, URI_UPDATE_FORECASTS));
        assertCall(false).accept(actual, HttpStatus.SERVICE_UNAVAILABLE);
        verify(forecastEtlServiceMock).updateForecasts();
    }

    @Test
    @DisplayName("updateForecasts(): returns 500 when an any unknown exception is thrown")
    void failUpdateForecastsInternalServerError() {
        doThrow(RuntimeException.class).when(forecastEtlServiceMock).updateForecasts();
        ResponseEntity<BaseResponse> actual = assertDoesNotThrow(() -> patch(null, URI_UPDATE_FORECASTS));
        assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastEtlServiceMock).updateForecasts();
    }
}