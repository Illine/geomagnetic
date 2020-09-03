package ru.illine.weather.geomagnetic.scheduler;

import ru.illine.weather.geomagnetic.exception.SwpcNoaaException;
import ru.illine.weather.geomagnetic.service.EtlService;
import ru.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringMockTest
@DisplayName("ForecastScheduler Spring Mock Test")
class ForecastSchedulerTest {

    private static final Duration DELAY_SCHEDULE_SECONDS = Duration.ofSeconds(15);

    @Mock
    private EtlService forecastEtlServiceMock;

    @Autowired
    private ForecastScheduler forecastScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastScheduler, "forecastEtlService", forecastEtlServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(forecastEtlServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("scheduleForecast(): a successful call automatically")
    void successfulScheduleForecast() {
        doNothing().when(forecastEtlServiceMock).updateForecasts();
        await()
                .atMost(DELAY_SCHEDULE_SECONDS)
                .untilAsserted(() -> verify(forecastEtlServiceMock, atLeastOnce()).updateForecasts());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("scheduleForecast(): a fail call when the Swp Noaa isn't availability")
    void failScheduleForecast() {
        var restClientException = new RestClientException("Some error status http");
        Mockito.doThrow(new SwpcNoaaException(restClientException.getMessage(), restClientException)).when(forecastEtlServiceMock).updateForecasts();
        assertDoesNotThrow(forecastScheduler::update);
        verify(forecastEtlServiceMock).updateForecasts();
    }
}