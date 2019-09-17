package net.c7j.weather.geomagnetic.schedule;

import net.c7j.weather.geomagnetic.client.SwpNoaaClient;
import net.c7j.weather.geomagnetic.event.publisher.ForecastPublisher;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
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

@IntegrationTest
@DisplayName("ForecastScheduler Integration Test")
class ForecastSchedulerIntegrationTest {

    private static final String STUB_TXT_FORECAST = "";

    @Mock
    private SwpNoaaClient swpNoaaClientSpy;

    @Mock
    private ForecastPublisher forecastPublisherSpy;

    @Autowired
    private ForecastScheduler forecastScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastScheduler, "swpNoaaClient", swpNoaaClientSpy);
        ReflectionTestUtils.setField(forecastScheduler, "forecastPublisher", forecastPublisherSpy);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("receiveForecast(): a successful call")
    void successfulReceiveForecast() {
        var stubResponseEntity = ResponseEntity.ok(STUB_TXT_FORECAST);

        when(swpNoaaClientSpy.get3DayGeomagForecast()).thenReturn(stubResponseEntity);
        doNothing().when(forecastPublisherSpy).publish(stubResponseEntity);

        assertDoesNotThrow(() -> forecastScheduler.receiveForecast());
        verify(swpNoaaClientSpy).get3DayGeomagForecast();
        verify(forecastPublisherSpy).publish(stubResponseEntity);
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("receiveForecast(): an unsuccessful call when the Swp Noaa isn't availability")
    void unsuccessfulReceiveForecast() {
        doThrow(new RestClientException("Some error status http")).when(swpNoaaClientSpy).get3DayGeomagForecast();

        assertDoesNotThrow(() -> forecastScheduler.receiveForecast());
        verify(swpNoaaClientSpy).get3DayGeomagForecast();
        verify(forecastPublisherSpy, never()).publish(any());
    }
}