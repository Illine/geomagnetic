package com.illine.weather.geomagnetic.event.publisher;

import com.illine.weather.geomagnetic.exception.ParseException;
import com.illine.weather.geomagnetic.model.dto.ForecastEventWrapper;
import com.illine.weather.geomagnetic.service.ForecastParserService;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringMockTest
@DisplayName("ForecastPublisher Spring Mock Test")
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

        expectedResponseEntitySwaNoaa = DtoGeneratorHelper.generateSwaNoaaResponseEntity();
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

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("publish(): an fail call when an arg is null")
    void failPublishNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastPublisher.publish(null));
        verify(applicationEventPublisherMock, never()).publishEvent(any(ForecastEventWrapper.class));
        verify(forecastParserServiceMock, never()).toParse(anyString());
    }

    @Test
    @DisplayName("publish(): an fail call when a ParseException is thrown")
    void failPublishParse() {
        when(forecastParserServiceMock.toParse(anyString())).thenThrow(ParseException.class);
        forecastPublisher.publish(ResponseEntity.ok("Invalid Text Forecast"));
        verify(applicationEventPublisherMock, never()).publishEvent(any(ForecastEventWrapper.class));
        verify(forecastParserServiceMock).toParse(anyString());
    }

    @Test
    @DisplayName("publish(): an fail call when an any unknown exception is thrown")
    void failPublishUnknown() {
        doThrow(new RuntimeException("Unknown")).when(applicationEventPublisherMock).publishEvent(any(ForecastEventWrapper.class));
        forecastPublisher.publish(expectedResponseEntitySwaNoaa);
        verify(applicationEventPublisherMock).publishEvent(any(ForecastEventWrapper.class));
        verify(forecastParserServiceMock).toParse(anyString());
    }
}