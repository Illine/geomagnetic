package net.c7j.weather.geomagnetic.rest.presenter;

import net.c7j.weather.geomagnetic.mapper.impl.MobileForecastDtoMapper;
import net.c7j.weather.geomagnetic.service.ForecastService;
import net.c7j.weather.geomagnetic.test.tag.LocalTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@LocalTest
@DisplayName("ForecastPresenter Local Test")
class ForecastPresenterTest {

    @Mock
    private ForecastService forecastServiceMock;
    @Mock
    private MobileForecastDtoMapper mobileForecastMapperMock;

    @Autowired
    private ForecastPresenter forecastPresenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastPresenter, "forecastService", forecastServiceMock);
        ReflectionTestUtils.setField(forecastPresenter, "mobileForecastMapper", mobileForecastMapperMock);
    }

    @AfterEach
    void tearDown() {
        reset(forecastServiceMock);
        reset(mobileForecastMapperMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("getDiurnal(): returns a correct forecast response")
    void successfulGetDiurnal() {
        var actual = forecastPresenter.getDiurnal();
        verify(forecastServiceMock).findDiurnal();
        verify(mobileForecastMapperMock).convertToDestinations(anyCollection());
        assertNotNull(actual);
    }

    @Test
    @DisplayName("getCurrent(): returns a current forecast response")
    void successfulGetCurrent() {
        var actual = forecastPresenter.getCurrent();
        verify(forecastServiceMock).findCurrent();
        verify(mobileForecastMapperMock).convertToDestinations(anyCollection());
        assertNotNull(actual);
    }

    @Test
    @DisplayName("getThreeDays(): returns  OK status and a list of a three day forecast")
    void successfulGetThreeDays() {
        var actual = forecastPresenter.getThreeDays();
        verify(forecastServiceMock).findThreeDays();
        verify(mobileForecastMapperMock).convertToDestinations(anyCollection());
        assertNotNull(actual);
    }
}