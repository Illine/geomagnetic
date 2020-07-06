package com.illine.weather.geomagnetic.service;

import com.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import com.illine.weather.geomagnetic.exception.NotFoundException;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringMockTest
@DisplayName("ForecastService Spring Mock Test")
class ForecastServiceTest {

    public static final String UNKNOWN_EXCEPTION_ERROR = "UnknownException";

    @Mock
    private ForecastAccessService forecastAccessServiceMock;

    @Autowired
    private ForecastService forecastService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastService, "forecastAccessService", forecastAccessServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(forecastAccessServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("findDiurnal(): returns a correct size of a collection")
    void successfulFindDiurnal() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(DtoGeneratorHelper.generateDiurnalForecastDtoSet());
        var actual = forecastService.findDiurnal();
        assertNotNull(actual);
        var expectedSize = 8;
        assertEquals(expectedSize, actual.size());
        verify(forecastAccessServiceMock).findDiurnal(any());
    }

    @Test
    @DisplayName("findDiurnal(): returns a sorted of a collection")
    void successfulFindDiurnalSorted() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(DtoGeneratorHelper.generateDiurnalForecastDtoSet());
        Assertions.assertThat(List.copyOf(forecastService.findDiurnal())).isSorted();
        verify(forecastAccessServiceMock).findDiurnal(any());
    }

    @Test
    @DisplayName("findCurrent(): returns a correct size of a collection")
    void successfulFindCurrent() {
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(DtoGeneratorHelper.generateCurrentForecastDtoSet());
        var actual = forecastService.findCurrent();
        assertNotNull(actual);
        var expectedSize = 4;
        assertEquals(expectedSize, actual.size());
        verify(forecastAccessServiceMock).findCurrent(any());
    }

    @Test
    @DisplayName("findCurrent(): returns a sorted of a collection")
    void successfulFindCurrentSorted() {
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(DtoGeneratorHelper.generateCurrentForecastDtoSet());
        Assertions.assertThat(List.copyOf(forecastService.findCurrent())).isSorted();
        verify(forecastAccessServiceMock).findCurrent(any());
    }

    @Test
    @DisplayName("findThreeDays(): returns a correct size of a collection")
    void successfulFindThreeDays() {
        when(forecastAccessServiceMock.findThreeDays(any())).thenReturn(DtoGeneratorHelper.generateThreeDaysForecastDtoSet());
        var actual = forecastService.findThreeDays();
        assertNotNull(actual);
        var expectedSize = 24;
        assertEquals(expectedSize, actual.size());
        verify(forecastAccessServiceMock).findThreeDays(any());
    }

    @Test
    @DisplayName("findThreeDays(): returns a sorted of a collection")
    void successfulFindThreeDaysSorted() {
        when(forecastAccessServiceMock.findThreeDays(any())).thenReturn(DtoGeneratorHelper.generateThreeDaysForecastDtoSet());
        Assertions.assertThat(List.copyOf(forecastService.findThreeDays())).isSorted();
        verify(forecastAccessServiceMock).findThreeDays(any());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("findDiurnal(): throws NotFound exception when a forecast isn't exist")
    void failFindDiurnalNotFound() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenReturn(Collections.emptySet());
        assertThrows(NotFoundException.class, () -> forecastService.findDiurnal());
    }

    @Test
    @DisplayName("findDiurnal(): throws an exception when an unknown error")
    void failFindDiurnalUnknownError() {
        when(forecastAccessServiceMock.findDiurnal(any())).thenThrow(new RuntimeException(UNKNOWN_EXCEPTION_ERROR));
        assertThrows(RuntimeException.class, () -> forecastService.findDiurnal());
    }

    @Test
    @DisplayName("findCurrent(): throws NotFound exception when a forecast isn't exist")
    void failFindCurrentNotFound() {
        when(forecastAccessServiceMock.findCurrent(any())).thenReturn(Collections.emptySet());
        assertThrows(NotFoundException.class, () -> forecastService.findCurrent());
    }

    @Test
    @DisplayName("findCurrent(): throws an exception when an unknown error")
    void failFindCurrentUnknownError() {
        when(forecastAccessServiceMock.findCurrent(any())).thenThrow(new RuntimeException(UNKNOWN_EXCEPTION_ERROR));
        assertThrows(RuntimeException.class, () -> forecastService.findCurrent());
    }

    @Test
    @DisplayName("findThreeDays(): throws NotFound exception when a forecast isn't exist")
    void failFindThreeDaysNotFound() {
        when(forecastAccessServiceMock.findThreeDays(any())).thenReturn(Collections.emptySet());
        assertThrows(NotFoundException.class, () -> forecastService.findThreeDays());
    }

    @Test
    @DisplayName("findThreeDays(): throws an exception when an unknown error")
    void failFindThreeDaysUnknownError() {
        when(forecastAccessServiceMock.findThreeDays(any())).thenThrow(new RuntimeException(UNKNOWN_EXCEPTION_ERROR));
        assertThrows(RuntimeException.class, () -> forecastService.findThreeDays());
    }
}