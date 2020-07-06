package com.illine.weather.geomagnetic.event.listener;

import com.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import com.illine.weather.geomagnetic.model.dto.ForecastEventWrapper;
import com.illine.weather.geomagnetic.service.ForecastUpsertService;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Collections;

import static com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateForecastEventWrapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringMockTest
@DisplayName("ForecastUpsertService Spring Mock Test")
class ForecastListenerTest {

    private static final long DEFAULT_TIMEOUT = 200L;

    @Mock
    private ForecastUpsertService forecastUpsertServiceMock;
    @Mock
    private ForecastAccessService forecastAccessServiceMock;

    @Autowired
    private ForecastListener forecastListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastListener, "forecastUpsertService", forecastUpsertServiceMock);
        ReflectionTestUtils.setField(forecastListener, "forecastAccessService", forecastAccessServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @RepeatedTest(5)
    @DisplayName("onEvent(): a successful call")
    void successfulOnEvent() {
        when(forecastUpsertServiceMock.upsertForecasts(any(), any())).thenReturn(Collections.emptySet());
        doNothing().when(forecastAccessServiceMock).save(any());

        forecastListener.onEvent(generateForecastEventWrapper(LocalDate.now()));
        verify(forecastUpsertServiceMock, timeout(DEFAULT_TIMEOUT).only()).upsertForecasts(any(), any());
        verify(forecastAccessServiceMock, timeout(DEFAULT_TIMEOUT).only()).save(any());
    }

    //  -----------------------   fail tests   -------------------------

    @RepeatedTest(5)
    @DisplayName("onEvent(): an fail call when an arg is null")
    void failOnEventArgNull() {
        forecastListener.onEvent(null);
        verify(forecastUpsertServiceMock, timeout(DEFAULT_TIMEOUT).times(0)).upsertForecasts(any(), any());
        verify(forecastAccessServiceMock, timeout(DEFAULT_TIMEOUT).times(0)).save(any());
    }

    @RepeatedTest(5)
    @DisplayName("onEvent(): an fail call when a collection into an arg is null or empty")
    void failOnEventCollectionEmpty() {
        forecastListener.onEvent(new ForecastEventWrapper(null));
        verify(forecastUpsertServiceMock, timeout(DEFAULT_TIMEOUT).times(0)).upsertForecasts(any(), any());
        verify(forecastAccessServiceMock, timeout(DEFAULT_TIMEOUT).times(0)).save(any());
    }
}