package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.test.helper.AssertionHelper;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@IntegrationTest
@DisplayName("RestForecastService Integration Test")
class RestForecastServiceIntegrationTest {

    @Mock
    @Autowired
    private ForecastAccessService forecastAccessServiceMock;

    @Autowired
    private RestForecastService restForecastService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(restForecastService, "forecastAccessService", forecastAccessServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): a successful call returns OK status and a list of a diurnal forecast")
    void successfulGetDiurnal() {
        var countForecast = 8;
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(GeneratorHelper.generateStreamForecastDto(countForecast));

        AssertionHelper.assertCall(countForecast).apply(restForecastService.getDiurnal());
    }

    @Test
    @DisplayName("getCurrent(): a successful call returns OK status and a list of a current forecast")
    void successfulGetCurrent() {
        var countForecast = 8;
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(GeneratorHelper.generateStreamForecastDto(countForecast));

        AssertionHelper.assertCall(countForecast).apply(restForecastService.getCurrent());
    }

    @Test
    @DisplayName("getThreeDay(): a successful call returns  OK status and a list of a three day forecast")
    void successfulGetThreeDay() {
        var countForecast = 24;
        when(forecastAccessServiceMock.findThreeDay(any())).thenReturn(GeneratorHelper.generateStreamForecastDto(countForecast));

        AssertionHelper.assertCall(countForecast).apply(restForecastService.getThreeDay());
    }

    @Test
    @DisplayName("getDiurnal(): a successful call returns a sorted list of diurnal forecast")
    void successfulSortedGetDiurnal() {
        var countForecast = 50;
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(GeneratorHelper.generateStreamForecastDto(countForecast));

        var forecasts = AssertionHelper.assertCall(countForecast).apply(restForecastService.getDiurnal());
        assertThat(forecasts).isSorted();
    }

    @Test
    @DisplayName("getCurrent(): a successful call returns a sorted list of current forecast")
    void successfulSortedGetCurrent() {
        var countForecast = 50;
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(GeneratorHelper.generateStreamForecastDto(countForecast));

        var forecasts = AssertionHelper.assertCall(countForecast).apply(restForecastService.getCurrent());
        assertThat(forecasts).isSorted();
    }

    @Test
    @DisplayName("getThreeDay(): a successful call returns a sorted list of three day forecast")
    void successfulSortedGetThreeDay() {
        var countForecast = 50;
        when(forecastAccessServiceMock.findThreeDay(any())).thenReturn(GeneratorHelper.generateStreamForecastDto(countForecast));

        var forecasts = AssertionHelper.assertCall(countForecast).apply(restForecastService.getThreeDay());
        assertThat(forecasts).isSorted();
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): an unsuccessful call throws NotFoundException when an 'empty' stream")
    void unsuccessfulGetDiurnal() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(Stream.empty());
        assertThrows(NotFoundException.class, () -> restForecastService.getDiurnal());
    }

    @Test
    @DisplayName("getCurrent(): an unsuccessful call throws NotFoundException when an 'empty' stream")
    void unsuccessfulGetCurrent() {
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(Stream.empty());
        assertThrows(NotFoundException.class, () -> restForecastService.getCurrent());
    }

    @Test
    @DisplayName("getThreeDay(): an unsuccessful call throws NotFoundException when an 'empty' stream")
    void unsuccessfulGetThreeDay() {
        when(forecastAccessServiceMock.findThreeDay(any())).thenReturn(Stream.empty());
        assertThrows(NotFoundException.class, () -> restForecastService.getThreeDay());
    }
}