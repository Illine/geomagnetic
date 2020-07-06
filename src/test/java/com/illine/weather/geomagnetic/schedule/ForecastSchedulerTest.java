package com.illine.weather.geomagnetic.schedule;

import com.illine.weather.geomagnetic.client.SwpNoaaClient;
import com.illine.weather.geomagnetic.event.publisher.ForecastPublisher;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringMockTest
@DisplayName("ForecastScheduler Spring Mock Test")
class ForecastSchedulerTest {

    private static final String STUB_TXT_FORECAST = "";

    @Mock
    private SwpNoaaClient swpNoaaClientMock;
    @Mock
    private ForecastPublisher forecastPublisherMock;

    @Autowired
    private ForecastScheduler forecastScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastScheduler, "swpNoaaClient", swpNoaaClientMock);
        ReflectionTestUtils.setField(forecastScheduler, "forecastPublisher", forecastPublisherMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("receiveForecast(): a successful call")
    void successfulReceiveForecast() {
        var stubResponseEntity = ResponseEntity.ok(STUB_TXT_FORECAST);

        when(swpNoaaClientMock.get3DayGeomagForecast()).thenReturn(stubResponseEntity);
        doNothing().when(forecastPublisherMock).publish(stubResponseEntity);

        assertDoesNotThrow(() -> forecastScheduler.receiveForecast());
        verify(swpNoaaClientMock).get3DayGeomagForecast();
        verify(forecastPublisherMock).publish(stubResponseEntity);
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("receiveForecast(): an fail call when the Swp Noaa isn't availability")
    void failReceiveForecast() {
        doThrow(new RestClientException("Some error status http")).when(swpNoaaClientMock).get3DayGeomagForecast();

        assertDoesNotThrow(() -> forecastScheduler.receiveForecast());
        verify(swpNoaaClientMock).get3DayGeomagForecast();
        verify(forecastPublisherMock, never()).publish(any());
    }
}