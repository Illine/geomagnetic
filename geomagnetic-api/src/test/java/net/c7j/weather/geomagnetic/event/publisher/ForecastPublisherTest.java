package net.c7j.weather.geomagnetic.event.publisher;

import net.c7j.weather.geomagnetic.model.dto.ForecastEventWrapper;
import net.c7j.weather.geomagnetic.service.ForecastParserService;
import net.c7j.weather.geomagnetic.test.tag.LocalTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static net.c7j.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateSwaNoaaResponseEntity;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@LocalTest
@DisplayName("ForecastPublisher Local Test")
class ForecastPublisherTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisherMock;

    @Mock
    private ForecastParserService forecastParserServiceMock;

    @Autowired
    private ForecastPublisher forecastPublisher;

    private ResponseEntity<String> expectedResponseEntitySwaNoaa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastPublisher, "applicationEventPublisher", applicationEventPublisherMock);
        ReflectionTestUtils.setField(forecastPublisher, "forecastParserService", forecastParserServiceMock);

        expectedResponseEntitySwaNoaa = generateSwaNoaaResponseEntity();
    }

    @AfterEach
    void tearDown() {
        reset(applicationEventPublisherMock);
        reset(forecastParserServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("publish(): a successful call")
    void successfulPublish() {
        doNothing().when(applicationEventPublisherMock).publishEvent(any(ForecastEventWrapper.class));
        forecastPublisher.publish(expectedResponseEntitySwaNoaa);
        verify(applicationEventPublisherMock).publishEvent(any(ForecastEventWrapper.class));
        verify(forecastParserServiceMock).toParse(anyString());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("publish(): an unsuccessful call when an arg is null")
    void unsuccessfulPublishNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastPublisher.publish(null));
        verify(applicationEventPublisherMock, never()).publishEvent(any(ForecastEventWrapper.class));
        verify(forecastParserServiceMock, never()).toParse(anyString());
    }

    @Test
    @DisplayName("publish(): an unsuccessful call when a ParseException is thrown")
    void unsuccessfulPublishParse() {
        forecastPublisher.publish(ResponseEntity.ok("Invalid Text Forecast"));
        verify(applicationEventPublisherMock).publishEvent(any(ForecastEventWrapper.class));
        verify(forecastParserServiceMock).toParse(anyString());
    }

    @Test
    @DisplayName("publish(): an unsuccessful call when an any unknown exception is thrown")
    void unsuccessfulPublishUnknown() {
        doThrow(new RuntimeException("Unknown")).when(applicationEventPublisherMock).publishEvent(any(ForecastEventWrapper.class));
        forecastPublisher.publish(expectedResponseEntitySwaNoaa);
        verify(applicationEventPublisherMock).publishEvent(any(ForecastEventWrapper.class));
        verify(forecastParserServiceMock).toParse(anyString());
    }
}