package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.exception.ParseException;
import net.c7j.weather.geomagnetic.service.impl.TxtForecastParserServiceImpl;
import net.c7j.weather.geomagnetic.test.helper.FileHelper;
import net.c7j.weather.geomagnetic.test.tag.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.time.LocalDate;

import static net.c7j.weather.geomagnetic.test.helper.generator.CommonGeneratorHelper.generateString;
import static net.c7j.weather.geomagnetic.test.helper.generator.DtoGeneratorHelper.generateTxtForecastDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTest
@DisplayName("TextForecastParserService Unit Test")
class TxtForecastParserServiceTest {

    private ForecastParserService forecastParserService;

    @BeforeEach
    void setUp() {
        forecastParserService = new TxtForecastParserServiceImpl();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("toParse(): returns a valid a TxtForecastDto set")
    void successfulToParseValid() throws IOException {
        var path = FileHelper.getPath("forecast/Geomagnetic_Forecast_0.txt", getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, LocalDate.now(), LocalDate.now().plusDays(2));
        var expected = generateTxtForecastDto(LocalDate.now());
        var actual = forecastParserService.toParse(fileContent);
        assertEquals(expected, actual);
    }

    @DisplayName("toParse(): returns a TxtForecastDto set")
    @ParameterizedTest
    @ValueSource(
            strings = {
                    "forecast/Geomagnetic_Forecast_0.txt",
                    "forecast/Geomagnetic_Forecast_1.txt",
                    "forecast/Geomagnetic_Forecast_2.txt",
                    "forecast/Geomagnetic_Forecast_3.txt"
            }
    )
    void successfulToParse(String arg) throws IOException {
        var path = FileHelper.getPath(arg, getClass());
        var tmpFileContent = FileHelper.getFileContent(path);
        var fileContent = FileHelper.setDate(tmpFileContent, LocalDate.now(), LocalDate.now().plusDays(2));
        var expected = 24;
        var actual = forecastParserService.toParse(fileContent).size();
        assertEquals(expected, actual);
    }

    //  -----------------------   unsuccessful tests   -------------------------

    @Test
    @DisplayName("toParse(): an unsuccessful call throws IllegalArgumentException when a 'null' file")
    void unsuccessfulToParseNullFile() {
        String nullFile = null;
        assertThrows(IllegalArgumentException.class, () -> forecastParserService.toParse(nullFile));
    }

    @Test
    @DisplayName("toParse(): an unsuccessful call throws IllegalArgumentException when an 'empty' file")
    void unsuccessfulToParseEmptyFile() {
        var emptyFile = "";
        assertThrows(IllegalArgumentException.class, () -> forecastParserService.toParse(emptyFile));
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
    @DisplayName("toParse(): an unsuccessful call throws ParseException when an invalid current date")
    void unsuccessfulToParseInvalidDate(String arg) throws IOException {
        var path = FileHelper.getPath(arg, getClass());
        var fileContent = FileHelper.getFileContent(path);
        assertThrows(ParseException.class, () -> forecastParserService.toParse(fileContent));
    }

    @ParameterizedTest
    @ValueSource(strings = "forecast/Geomagnetic_Forecast_5.txt")
    @DisplayName("toParse(): an unsuccessful call throws ParseException when an invalid size of a result collection")
    void unsuccessfulToParseSizeDate(String arg) throws IOException {
        var path = FileHelper.getPath(arg, getClass());
        var fileContent = FileHelper.setDate(FileHelper.getFileContent(path), LocalDate.now(), LocalDate.now().plusDays(2));
        assertThrows(ParseException.class, () -> forecastParserService.toParse(fileContent));
    }

    @Test
    @DisplayName("toParse(): an unsuccessful call throws ParseException when an invalid file")
    void unsuccessfulToParseInvalidFile() {
        var invalidFile = generateString();
        assertThrows(ParseException.class, () -> forecastParserService.toParse(invalidFile));
    }
}