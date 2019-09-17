package net.c7j.weather.geomagnetic.event.publisher;

import net.c7j.weather.geomagnetic.dao.dto.ForecastEventWrapper;
import net.c7j.weather.geomagnetic.service.ForecastParserService;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@IntegrationTest
@DisplayName("ForecastPublisher Integration Test")
class ForecastPublisherIntegrationTest {

    private ResponseEntity<String> expectedResponseEntity;

    @Autowired
    private ForecastPublisher forecastPublisher;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Spy
    private ForecastParserService forecastParserServiceSpy;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastPublisher, "applicationEventPublisher", applicationEventPublisher);
        ReflectionTestUtils.setField(forecastPublisher, "forecastParserService", forecastParserServiceSpy);

        expectedResponseEntity = GeneratorHelper.generateResponseEntity();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("publish(): a successful call")
    void publish() {
        doNothing().when(applicationEventPublisher).publishEvent(any(ForecastEventWrapper.class));

        forecastPublisher.publish(expectedResponseEntity);
        verify(applicationEventPublisher).publishEvent(any(ForecastEventWrapper.class));
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("publish(): an successful call when an arg is null")
    void unsuccessfulNullPublish() {
        assertThrows(IllegalArgumentException.class, () -> forecastPublisher.publish(null));

        verify(applicationEventPublisher, never()).publishEvent(anyCollection());
    }


    @Test
    @DisplayName("publish(): an unsuccessful call when a ParseException")
    void unsuccessfulParsePublish() {
        forecastPublisher.publish(ResponseEntity.ok("Invalid Text Forecast"));

        verify(applicationEventPublisher, never()).publishEvent(anyCollection());
    }

    @Test
    @DisplayName("publish(): an unsuccessful call when an any unknown exception")
    void unsuccessfulUnknownPublish() {
        doThrow(new RuntimeException("Unknown")).when(applicationEventPublisher).publishEvent(any(ForecastEventWrapper.class));

        forecastPublisher.publish(expectedResponseEntity);

        verify(applicationEventPublisher).publishEvent(any(ForecastEventWrapper.class));
    }
}