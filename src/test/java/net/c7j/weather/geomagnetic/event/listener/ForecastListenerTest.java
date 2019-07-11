package net.c7j.weather.geomagnetic.event.listener;

import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.service.ForecastUpsertService;
import net.c7j.weather.geomagnetic.test.helper.GeneratorHelper;
import net.c7j.weather.geomagnetic.test.tag.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
@DisplayName("ForecastUpsertService Integration Test")
class ForecastListenerTest {

    private static final long DEFAULT_TIMEOUT = 200L;

    @Mock
    @Autowired
    private ForecastUpsertService forecastUpsertServiceMock;

    @Mock
    @Autowired
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

    @RepeatedTest(2)
    @DisplayName("onEvent(): a successful call")
    void successfulOnEvent() {
        when(forecastUpsertServiceMock.upsertForecasts(any(), any())).thenReturn(Stream.empty());
        doNothing().when(forecastAccessServiceMock).upsert(anyCollection());

        forecastListener.onEvent(GeneratorHelper.generateTxtForecastDto(LocalDate.now()));
        verify(forecastUpsertServiceMock, timeout(DEFAULT_TIMEOUT).only()).upsertForecasts(any(), any());
        verify(forecastAccessServiceMock, timeout(DEFAULT_TIMEOUT).only()).upsert(anyCollection());
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @RepeatedTest(2)
    @DisplayName("onEvent(): an unsuccessful call")
    void unsuccessfulOnEvent() {
        forecastListener.onEvent(null);
        verify(forecastUpsertServiceMock, timeout(DEFAULT_TIMEOUT).times(0)).upsertForecasts(any(), any());
        verify(forecastAccessServiceMock, timeout(DEFAULT_TIMEOUT).times(0)).upsert(anyCollection());
    }
}