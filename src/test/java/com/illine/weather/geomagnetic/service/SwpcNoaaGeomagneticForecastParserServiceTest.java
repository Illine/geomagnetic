package com.illine.weather.geomagnetic.service;

import com.illine.weather.geomagnetic.exception.ParseException;
import com.illine.weather.geomagnetic.test.helper.FileHelper;
import com.illine.weather.geomagnetic.test.helper.generator.CommonGeneratorHelper;
import com.illine.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper;
import com.illine.weather.geomagnetic.test.tag.SpringMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.illine.weather.geomagnetic.test.helper.generator.CommonGeneratorHelper.generateInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringMockTest
@DisplayName("SwpcNoaaGeomagneticForecastParserService Spring Mock Test")
class SwpcNoaaGeomagneticForecastParserServiceTest {

    @Mock
    private Clock clockMock;

    @Autowired
    private ForecastParserService forecastParserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(forecastParserService, "clock", clockMock);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(clockMock);
    }

    //  -----------------------   successful tests   -------------------------

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
            }
    )
    @DisplayName("toParse(): returns a valid a TxtForecastDto set when a forecast is today")
    void successfulToParseValidToday(String txtForecastPattern) throws IOException {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, LocalDate.now(), LocalDate.now().plusDays(2));
        var expected = DtoGeneratorHelper.generateTxtForecastDto(LocalDate.now());
        var actual = forecastParserService.parse(fileContent);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
            }
    )
    @DisplayName("toParse(): returns a valid a TxtForecastDto set when a forecast is yesterday")
    void successfulToParseValidYesterday(String txtForecastPattern) throws IOException {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        var expected = DtoGeneratorHelper.generateTxtForecastDto(LocalDate.now());
        var actual = forecastParserService.parse(fileContent);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
            }
    )
    @DisplayName("toParse(): returns a valid a TxtForecastDto set when a forecast is started in the current and finished in the next month")
    void successfulToParseValidFromCurrentMonthAndToNextMonth(String txtForecastPattern) throws IOException {
        var forecastDay = LocalDate.of(2020, 1, 31);
        var yesterdayClock = Clock.fixed(forecastDay.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clockMock.instant()).thenReturn(yesterdayClock.instant());
        when(clockMock.getZone()).thenReturn(yesterdayClock.getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, forecastDay, forecastDay.plusDays(2));
        var expected = DtoGeneratorHelper.generateTxtForecastDto(LocalDate.now());
        var actual = forecastParserService.parse(fileContent);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
            }
    )
    @DisplayName("toParse(): returns a valid a TxtForecastDto set when a forecast is started in the prev and finished in the current month")
    void successfulToParseValidFromPrevMonthAndToCurrentMonth(String txtForecastPattern) throws IOException {
        var forecastDay = LocalDate.of(2020, 2, 1);
        var yesterdayClock = Clock.fixed(forecastDay.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clockMock.instant()).thenReturn(yesterdayClock.instant());
        when(clockMock.getZone()).thenReturn(yesterdayClock.getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, forecastDay.minusDays(1), forecastDay.plusDays(1));
        var expected = DtoGeneratorHelper.generateTxtForecastDto(LocalDate.now());
        var actual = forecastParserService.parse(fileContent);
        assertEquals(expected, actual);
    }

    @DisplayName("toParse(): returns a TxtForecastDto set has correct size")
    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
                    "forecast/Geomagnetic_Forecast_1.txt",
                    "forecast/Geomagnetic_Forecast_2.txt",
                    "forecast/Geomagnetic_Forecast_3.txt"
            }
    )
    void successfulToParse(String txtForecastPattern) throws IOException {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, LocalDate.now(), LocalDate.now().plusDays(2));
        var expected = 24;
        var actual = forecastParserService.parse(fileContent).size();
        assertEquals(expected, actual);
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("toParse(): a fail call throws IllegalArgumentException when a 'null' file")
    void failToParseNullFile() {
        String nullFile = null;
        assertThrows(IllegalArgumentException.class, () -> forecastParserService.parse(nullFile));
    }

    @Test
    @DisplayName("toParse(): a fail call throws IllegalArgumentException when an 'empty' file")
    void failToParseEmptyFile() {
        var emptyFile = "";
        assertThrows(IllegalArgumentException.class, () -> forecastParserService.parse(emptyFile));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
                    "forecast/Geomagnetic_Forecast_1.txt",
                    "forecast/Geomagnetic_Forecast_2.txt",
                    "forecast/Geomagnetic_Forecast_3.txt"
            }
    )
    @DisplayName("toParse(): a fail call throws ParseException when an invalid date")
    void failToParseInvalidDate(String txtForecastPattern) throws IOException {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var fileContent = FileHelper.getFileContent(path);
        assertThrows(ParseException.class, () -> forecastParserService.parse(fileContent));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
                    "forecast/Geomagnetic_Forecast_1.txt",
                    "forecast/Geomagnetic_Forecast_2.txt",
                    "forecast/Geomagnetic_Forecast_3.txt"
            }
    )
    @DisplayName("toParse(): a fail call throws ParseException when an incorrect date")
    void failToParseIncorrectDate(String txtForecastPattern) throws IOException {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var from = LocalDate.now().minusDays(generateInteger(3, 10));
        var to = LocalDate.now().plusDays(generateInteger(3, 10));
        var fileContent = FileHelper.setDate(tmpFileContent, from, to);
        assertThrows(ParseException.class, () -> forecastParserService.parse(fileContent));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
                    "forecast/Geomagnetic_Forecast_1.txt",
                    "forecast/Geomagnetic_Forecast_2.txt",
                    "forecast/Geomagnetic_Forecast_3.txt"
            }
    )
    @DisplayName("toParse(): a fail call throws ParseException when a forecast is day before yesterday")
    void failToParseValidDayBeforeYesterday(String txtForecastPattern) throws IOException {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, LocalDate.now().minusDays(2), LocalDate.now());
        assertThrows(ParseException.class, () -> forecastParserService.parse(fileContent));
    }

    @ParameterizedTest
    @ValueSource(strings = "forecast/Geomagnetic_Forecast_5.txt")
    @DisplayName("toParse(): a fail call throws ParseException when an invalid size of a result collection")
    void failToParseSizeDate(String txtForecastPattern) throws IOException {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var path = FileHelper.getPath(txtForecastPattern, getClass());
        var fileContent = FileHelper.setDate(FileHelper.getFileContent(path), LocalDate.now(), LocalDate.now().plusDays(2));
        assertThrows(ParseException.class, () -> forecastParserService.parse(fileContent));
    }

    @Test
    @DisplayName("toParse(): a fail call throws ParseException when an invalid file")
    void failToParseInvalidFile() {
        when(clockMock.instant()).thenReturn(Clock.systemDefaultZone().instant());
        when(clockMock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        var invalidFile = CommonGeneratorHelper.generateString();
        assertThrows(ParseException.class, () -> forecastParserService.parse(invalidFile));
    }
}