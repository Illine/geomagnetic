package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.model.dto.ForecastResponse;
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
class ViewForecastServiceIntegrationTest {

    @Mock
    private ForecastAccessService forecastAccessServiceMock;

    @Autowired
    private ViewForecastService<ForecastResponse> viewForecastService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(viewForecastService, "forecastAccessService", forecastAccessServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): a successful call returns OK status and a list of a diurnal forecast")
    void successfulGetDiurnal() {
        var countForecast = 8;
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(countForecast));

        AssertionHelper.assertCall(countForecast).apply(viewForecastService.getDiurnal());
    }

    @Test
    @DisplayName("getCurrent(): a successful call returns OK status and a list of a current forecast")
    void successfulGetCurrent() {
        var countForecast = 8;
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(countForecast));

        AssertionHelper.assertCall(countForecast).apply(viewForecastService.getCurrent());
    }

    @Test
    @DisplayName("getThreeDay(): a successful call returns  OK status and a list of a three day forecast")
    void successfulGetThreeDay() {
        var countForecast = 24;
        when(forecastAccessServiceMock.findThreeDay(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(countForecast));

        AssertionHelper.assertCall(countForecast).apply(viewForecastService.getThreeDay());
    }

    @Test
    @DisplayName("getDiurnal(): a successful call returns a sorted list of diurnal forecast")
    void successfulSortedGetDiurnal() {
        var countForecast = 50;
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(countForecast));

        var forecasts = AssertionHelper.assertCall(countForecast).apply(viewForecastService.getDiurnal());
        assertThat(forecasts).isSorted();
    }

    @Test
    @DisplayName("getCurrent(): a successful call returns a sorted list of current forecast")
    void successfulSortedGetCurrent() {
        var countForecast = 50;
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(countForecast));

        var forecasts = AssertionHelper.assertCall(countForecast).apply(viewForecastService.getCurrent());
        assertThat(forecasts).isSorted();
    }

    @Test
    @DisplayName("getThreeDay(): a successful call returns a sorted list of three day forecast")
    void successfulSortedGetThreeDay() {
        var countForecast = 50;
        when(forecastAccessServiceMock.findThreeDay(any())).thenReturn(GeneratorHelper.generateStreamForecastEntity(countForecast));

        var forecasts = AssertionHelper.assertCall(countForecast).apply(viewForecastService.getThreeDay());
        assertThat(forecasts).isSorted();
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): an unsuccessful call throws NotFoundException when an 'empty' stream")
    void unsuccessfulGetDiurnal() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(Stream.empty());
        assertThrows(NotFoundException.class, () -> viewForecastService.getDiurnal());
    }

    @Test
    @DisplayName("getCurrent(): an unsuccessful call throws NotFoundException when an 'empty' stream")
    void unsuccessfulGetCurrent() {
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(Stream.empty());
        assertThrows(NotFoundException.class, () -> viewForecastService.getCurrent());
    }

    @Test
    @DisplayName("getThreeDay(): an unsuccessful call throws NotFoundException when an 'empty' stream")
    void unsuccessfulGetThreeDay() {
        when(forecastAccessServiceMock.findThreeDay(any())).thenReturn(Stream.empty());
        assertThrows(NotFoundException.class, () -> viewForecastService.getThreeDay());
    }
}