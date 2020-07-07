package com.illine.weather.geomagnetic.service;

import com.illine.weather.geomagnetic.client.SwpcNoaaClient;
import com.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import com.illine.weather.geomagnetic.model.base.IndexType;
import com.illine.weather.geomagnetic.model.dto.ForecastDto;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringIntegrationTest
@DisplayName("ForecastEtlService Spring Integration Test")
class ForecastEtlServiceTest {

    private static final int DEFAULT_SIZE_THREE_DAYS_FORECAST = 24;

    @Mock
    private SwpcNoaaClient swpcNoaaClientMock;

    @Autowired
    private ForecastAccessService forecastAccessService;

    @Autowired
    private EtlService forecastEtlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastEtlService, "swpcNoaaClient", swpcNoaaClientMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/ForecastEtlService/fill.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/ForecastEtlService/clear.sql")
    @DisplayName("updateForecasts(): a successful update of forecasts")
    void successfulUpdateForecasts() {
        when(swpcNoaaClientMock.get3DayGeomagneticForecast()).thenReturn(DtoGeneratorHelper.generateSwpcNoaaResponseEntity());
        assertDoesNotThrow(forecastEtlService::updateForecasts);
        var updatedForecastIndexes = forecastAccessService.findThreeDays(LocalDate.now()).stream().map(ForecastDto::getIndex).collect(Collectors.toList());
        assertAll(() -> {
            assertEquals(DEFAULT_SIZE_THREE_DAYS_FORECAST, updatedForecastIndexes.size());
            assertThat(updatedForecastIndexes, hasItems(IndexType.QUITE, IndexType.UNSETTLED, IndexType.ACTIVE));
        });
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("updateForecasts(): throws an exception")
    void failUpdateForecasts() {
        when(swpcNoaaClientMock.get3DayGeomagneticForecast()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, forecastEtlService::updateForecasts);
    }
}