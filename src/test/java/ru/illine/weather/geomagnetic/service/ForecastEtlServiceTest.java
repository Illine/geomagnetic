package ru.illine.weather.geomagnetic.service;

import ru.illine.weather.geomagnetic.client.SwpcNoaaClient;
import ru.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import ru.illine.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import ru.illine.weather.geomagnetic.model.base.IndexType;
import ru.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringMockTest
@DisplayName("ForecastEtlService Spring Mock Test")
class ForecastEtlServiceTest {

    @Mock
    private SwpcNoaaClient swpcNoaaClientMock;

    @Mock
    private ForecastParserService forecastParserServiceMock;

    @Mock
    private TxtForecastDtoMapper txtForecastMapperMock;

    @Mock
    private ForecastAccessService forecastAccessServiceMock;

    @Autowired
    private EtlService forecastEtlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastEtlService, "swpcNoaaClient", swpcNoaaClientMock);
        ReflectionTestUtils.setField(forecastEtlService, "forecastParserService", forecastParserServiceMock);
        ReflectionTestUtils.setField(forecastEtlService, "txtForecastMapper", txtForecastMapperMock);
        ReflectionTestUtils.setField(forecastEtlService, "forecastAccessService", forecastAccessServiceMock);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(swpcNoaaClientMock, forecastParserServiceMock, txtForecastMapperMock, forecastAccessServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("updateForecasts(): a successful update of forecasts")
    void successfulUpdateForecasts() {
        when(swpcNoaaClientMock.get3DayGeomagneticForecast()).thenReturn(DtoGeneratorHelper.generateSwpcNoaaResponseEntity());
        when(forecastParserServiceMock.parse(anyString())).thenReturn(DtoGeneratorHelper.generateTxtForecastDto(LocalDate.now()));
        when(txtForecastMapperMock.convertToSources(anyCollection())).thenReturn(DtoGeneratorHelper.generateDiurnalForecastDtoList());
        when(forecastAccessServiceMock.findThreeDays(any())).thenReturn(DtoGeneratorHelper.generateThreeDaysForecastDtoSet(IndexType.EXTREME_STORM));
        assertDoesNotThrow(forecastEtlService::updateForecasts);
        verify(swpcNoaaClientMock).get3DayGeomagneticForecast();
        verify(forecastParserServiceMock).parse(anyString());
        verify(txtForecastMapperMock).convertToSources(anyCollection());
        verify(forecastAccessServiceMock).update(anyCollection());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("updateForecasts(): throws an exception")
    void failUpdateForecasts() {
        when(swpcNoaaClientMock.get3DayGeomagneticForecast()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, forecastEtlService::updateForecasts);
    }
}