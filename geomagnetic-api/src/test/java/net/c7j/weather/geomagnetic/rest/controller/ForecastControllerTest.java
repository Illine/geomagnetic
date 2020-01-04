package net.c7j.weather.geomagnetic.rest.controller;

import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.exception.ParseException;
import net.c7j.weather.geomagnetic.model.dto.MobileForecastResponse;
import net.c7j.weather.geomagnetic.rest.presenter.ForecastPresenter;
import net.c7j.weather.geomagnetic.test.tag.LocalTest;
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

import static net.c7j.weather.geomagnetic.test.helper.AssertionHelper.assertCall;
import static net.c7j.weather.geomagnetic.test.helper.ControllerHelper.exchangeGet;
import static net.c7j.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateMobileForecastResponse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@LocalTest
@DisplayName("ForecastController System Test")
class ForecastControllerTest {

    private static final String URI_GET_DIURNAL = "/forecasts/diurnal";
    private static final String URI_GET_CURRENT = "/forecasts/current";
    private static final String URI_GET_THREE_DAY = "/forecasts/three-day";

    private static final String NOT_FOUND_MESSAGE = "NotFound";
    private static final String INTERNAL_SERVER_MESSAGE = "InternalServerError";

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
        when(forecastPresenterMock.getDiurnal()).thenReturn(generateMobileForecastResponse());
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        assertCall(true).accept(actual, HttpStatus.OK);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getCurrent(): returns 200 and a success body")
    void successfulGetCurrent() {
        when(forecastPresenterMock.getCurrent()).thenReturn(generateMobileForecastResponse());
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        assertCall(true).accept(actual, HttpStatus.OK);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getThreeDay(): returns 200 and a success body")
    void successfulThreeDays() {
        when(forecastPresenterMock.getThreeDays()).thenReturn(generateMobileForecastResponse());
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        assertCall(true).accept(actual, HttpStatus.OK);
        verify(forecastPresenterMock).getThreeDays();
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): returns 404 when a forecast not found")
    void unsuccessfulGetDiurnalNotFound() {
        when(forecastPresenterMock.getDiurnal()).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        assertCall(false).accept(actual, HttpStatus.NOT_FOUND);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getDiurnal(): returns 500 when ParseException is thrown")
    void unsuccessfulGetDiurnalParseInternalServerError() {
        when(forecastPresenterMock.getDiurnal()).thenThrow(new ParseException(INTERNAL_SERVER_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getDiurnal(): returns 500 when an any unknown exception is thrown")
    void unsuccessfulGetDiurnalInternalServerError() {
        when(forecastPresenterMock.getDiurnal()).thenThrow(new RuntimeException(INTERNAL_SERVER_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_DIURNAL, port));
        assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getDiurnal();
    }

    @Test
    @DisplayName("getCurrent(): returns 404 when a forecast not found")
    void unsuccessfulGetCurrentNotFound() {
        when(forecastPresenterMock.getCurrent()).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        assertCall(false).accept(actual, HttpStatus.NOT_FOUND);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getCurrent(): returns 500 when ParseException is thrown")
    void unsuccessfulGetCurrentParseInternalServerError() {
        when(forecastPresenterMock.getCurrent()).thenThrow(new ParseException(INTERNAL_SERVER_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getCurrent(): returns 500 when an any unknown exception is thrown")
    void unsuccessfulGetCurrentInternalServerError() {
        when(forecastPresenterMock.getCurrent()).thenThrow(new RuntimeException(INTERNAL_SERVER_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_CURRENT, port));
        assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getCurrent();
    }

    @Test
    @DisplayName("getThreeDay(): returns 404 when a forecast not found")
    void unsuccessfulGetThreeDaysNotFound() {
        when(forecastPresenterMock.getThreeDays()).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        assertCall(false).accept(actual, HttpStatus.NOT_FOUND);
        verify(forecastPresenterMock).getThreeDays();
    }

    @Test
    @DisplayName("getThreeDay(): returns 500 when ParseException is thrown")
    void unsuccessfulThreeDaysParseInternalServerError() {
        when(forecastPresenterMock.getThreeDays()).thenThrow(new ParseException(INTERNAL_SERVER_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        assertTrue(Objects.requireNonNull(actual.getBody()).getForecasts().isEmpty());
        verify(forecastPresenterMock).getThreeDays();
    }

    @Test
    @DisplayName("getThreeDay(): returns 500 when an any unknown exception is thrown")
    void unsuccessfulThreeDaysInternalServerError() {
        when(forecastPresenterMock.getThreeDays()).thenThrow(new RuntimeException(INTERNAL_SERVER_MESSAGE));
        var actual = assertDoesNotThrow(() -> exchangeGet(restTemplate, MobileForecastResponse.class, URI_GET_THREE_DAY, port));
        assertCall(false).accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(forecastPresenterMock).getThreeDays();
    }
}