package com.illine.weather.geomagnetic.rest.controller;

import com.illine.weather.geomagnetic.test.helper.AssertionHelper;
import com.illine.weather.geomagnetic.test.helper.ControllerHelper;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.exception.NotFoundException;
import com.illine.weather.geomagnetic.exception.ParseException;
import com.illine.weather.geomagnetic.model.dto.MobileForecastResponse;
import com.illine.weather.geomagnetic.rest.presenter.ForecastPresenter;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Objects;

import static com.illine.weather.geomagnetic.test.helper.AssertionHelper.assertCall;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringIntegrationTest
@DisplayName("ForecastController Spring Integration Test")
class ForecastControllerTest {

    private static final String URI_GET_DIURNAL = "/forecasts/diurnal";
    private static final String URI_GET_CURRENT = "/forecasts/current";
    private static final String URI_GET_THREE_DAY = "/forecasts/three-day";

    @LocalServerPort
    private int port;

    @Mock
    private ForecastPresenter forecastPresenterMock;

    @Autowired
    private ForecastController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(controller, "forecastPresenter", forecastPresenterMock);
    }

    @AfterEach
    void tearDown() {
        reset(forecastPresenterMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): returns 200 and a success body")
    void successfulGetDiurnal() {
        when(forecastPresenterMock.getDiurnal()).thenReturn(DtoGeneratorHelper.generateMobileForecastResponse());
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        AssertionHelper.assertCall(true).accept(actual, HttpStatus.OK);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getCurrent(): returns 200 and a success body")
    void successfulGetCurrent() {
        when(forecastPresenterMock.getCurrent()).thenReturn(DtoGeneratorHelper.generateMobileForecastResponse());
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        AssertionHelper.assertCall(true).accept(actual, HttpStatus.OK);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getThreeDay(): returns 200 and a success body")
    void successfulThreeDays() {
        when(forecastPresenterMock.getThreeDays()).thenReturn(DtoGeneratorHelper.generateMobileForecastResponse());
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        AssertionHelper.assertCall(true).accept(actual, HttpStatus.OK);
        verify(forecastPresenterMock).getThreeDays();
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): returns 404 when a forecast not found")
    void unsuccessfulGetDiurnalNotFound() {
        when(forecastPresenterMock.getDiurnal()).thenThrow(NotFoundException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.NOT_FOUND);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getDiurnal(): returns 500 when ParseException is thrown")
    void unsuccessfulGetDiurnalParseInternalServerError() {
        when(forecastPresenterMock.getDiurnal()).thenThrow(ParseException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getDiurnal(): returns 500 when an any unknown exception is thrown")
    void unsuccessfulGetDiurnalInternalServerError() {
        when(forecastPresenterMock.getDiurnal()).thenThrow(RuntimeException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getCurrent(): returns 404 when a forecast not found")
    void unsuccessfulGetCurrentNotFound() {
        when(forecastPresenterMock.getCurrent()).thenThrow(NotFoundException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.NOT_FOUND);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getCurrent(): returns 500 when ParseException is thrown")
    void unsuccessfulGetCurrentParseInternalServerError() {
        when(forecastPresenterMock.getCurrent()).thenThrow(ParseException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getCurrent(): returns 500 when an any unknown exception is thrown")
    void unsuccessfulGetCurrentInternalServerError() {
        when(forecastPresenterMock.getCurrent()).thenThrow(RuntimeException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getThreeDay(): returns 404 when a forecast not found")
    void unsuccessfulGetThreeDaysNotFound() {
        when(forecastPresenterMock.getThreeDays()).thenThrow(NotFoundException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.NOT_FOUND);
        verify(forecastPresenterMock).getThreeDays();
    }

    @Test
    @DisplayName("getThreeDay(): returns 500 when ParseException is thrown")
    void unsuccessfulThreeDaysParseInternalServerError() {
        when(forecastPresenterMock.getThreeDays()).thenThrow(ParseException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getForecasts().isEmpty());
        verify(forecastPresenterMock).getThreeDays();
    }

    @Test
    @DisplayName("getThreeDay(): returns 500 when an any unknown exception is thrown")
    void unsuccessfulThreeDaysInternalServerError() {
        when(forecastPresenterMock.getThreeDays()).thenThrow(RuntimeException.class);
        var actual = assertDoesNotThrow(() -> ControllerHelper.exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        AssertionHelper.assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getThreeDays();
    }
}