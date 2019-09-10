package net.c7j.weather.geomagnetic.controller;

import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.exception.ParseException;
import net.c7j.weather.geomagnetic.service.ViewForecastService;
import net.c7j.weather.geomagnetic.test.helper.AssertionHelper;
import net.c7j.weather.geomagnetic.test.helper.ClientHelper;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.SystemTest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SystemTest
@DisplayName("ForecastController System Test")
class ForecastControllerSystemTest {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String NOT_FOUND_MESSAGE = "NotFound";
    private static final String INTERNAL_SERVER_MESSAGE = "InternalServerError";

    @LocalServerPort
    private int port;

    @Mock
    @Autowired
    private ForecastAccessService forecastAccessServiceMock;

    @Autowired
    private ViewForecastService viewForecastService;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(viewForecastService, "forecastAccessService", forecastAccessServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("diurnal(): a successful call returns a valid response")
    void successfulDiurnal() {
        var expectedCount = 8;
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(expectedCount));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeDiurnal(restTemplate, port));
        AssertionHelper.assertCall(SUCCESS_MESSAGE).accept(actual, HttpStatus.OK);
        assertFalse(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertTrue(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("current(): a successful call returns a valid response")
    void successfulCurrent() {
        var expectedCount = 4;
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(expectedCount));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeCurrent(restTemplate, port));
        AssertionHelper.assertCall(SUCCESS_MESSAGE).accept(actual, HttpStatus.OK);
        assertFalse(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertTrue(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("threeDay(): a successful call returns a valid response")
    void successfulThreeDay() {
        var expectedCount = 24;
        when(forecastAccessServiceMock.findThreeDay(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(expectedCount));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeThreeDay(restTemplate, port));
        AssertionHelper.assertCall(SUCCESS_MESSAGE).accept(actual, HttpStatus.OK);
        assertFalse(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertTrue(actual.getBody().isSuccess());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("diurnal(): an unsuccessful call returns NOT_FOUND when NotFound exception is thrown")
    void unsuccessfulNotFoundDiurnal() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeDiurnal(restTemplate, port));
        AssertionHelper.assertCall(NOT_FOUND_MESSAGE).accept(actual, HttpStatus.NOT_FOUND);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("diurnal(): an unsuccessful call returns INTERNAL_SERVER_ERROR when an ParseException is thrown")
    void unsuccessfulParseInternalServerErrorDiurnal() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenThrow(new ParseException(INTERNAL_SERVER_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeDiurnal(restTemplate, port));
        AssertionHelper.assertCall(INTERNAL_SERVER_MESSAGE).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("diurnal(): an unsuccessful call returns INTERNAL_SERVER_ERROR when an any unknown is thrown")
    void unsuccessfulInternalServerErrorDiurnal() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenThrow(new RuntimeException(INTERNAL_SERVER_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeDiurnal(restTemplate, port));
        AssertionHelper.assertCall(INTERNAL_SERVER_MESSAGE).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("current(): an unsuccessful call returns NOT_FOUND when NotFound exception is thrown")
    void unsuccessfulNotFoundCurrent() {
        when(forecastAccessServiceMock.findCurrent(any())).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeCurrent(restTemplate, port));
        AssertionHelper.assertCall(NOT_FOUND_MESSAGE).accept(actual, HttpStatus.NOT_FOUND);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("current(): an unsuccessful call returns INTERNAL_SERVER_ERROR when an ParseException is thrown")
    void unsuccessfulParseInternalServerErrorCurrent() {
        when(forecastAccessServiceMock.findCurrent(any())).thenThrow(new ParseException(INTERNAL_SERVER_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeCurrent(restTemplate, port));
        AssertionHelper.assertCall(INTERNAL_SERVER_MESSAGE).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("current(): an unsuccessful call returns INTERNAL_SERVER_ERROR when an any unknown exception is thrown")
    void unsuccessfulInternalServerErrorCurrent() {
        when(forecastAccessServiceMock.findCurrent(any())).thenThrow(new RuntimeException(INTERNAL_SERVER_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeCurrent(restTemplate, port));
        AssertionHelper.assertCall(INTERNAL_SERVER_MESSAGE).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("threeDay(): an unsuccessful call returns NOT_FOUND when NotFound exception is thrown")
    void unsuccessfulNotFoundThreeDay() {
        when(forecastAccessServiceMock.findThreeDay(any())).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeThreeDay(restTemplate, port));
        AssertionHelper.assertCall(NOT_FOUND_MESSAGE).accept(actual, HttpStatus.NOT_FOUND);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("threeDay(): an unsuccessful call returns INTERNAL_SERVER_ERROR when an ParseException is thrown")
    void unsuccessfulParseInternalServerErrorThreeDay() {
        when(forecastAccessServiceMock.findThreeDay(any())).thenThrow(new ParseException(INTERNAL_SERVER_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeThreeDay(restTemplate, port));
        AssertionHelper.assertCall(INTERNAL_SERVER_MESSAGE).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }

    @Test
    @DisplayName("threeDay(): an unsuccessful call returns INTERNAL_SERVER_ERROR when an any unknown exception is thrown")
    void unsuccessfulInternalServerErrorThreeDay() {
        when(forecastAccessServiceMock.findThreeDay(any())).thenThrow(new RuntimeException(INTERNAL_SERVER_MESSAGE));

        var actual = assertDoesNotThrow(() -> ClientHelper.exchangeThreeDay(restTemplate, port));
        AssertionHelper.assertCall(INTERNAL_SERVER_MESSAGE).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getGeomagneticForecasts().isEmpty());
        assertFalse(actual.getBody().isSuccess());
    }
}