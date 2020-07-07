package com.illine.weather.geomagnetic.test.helper.generator;

import com.illine.weather.geomagnetic.model.base.IndexType;
import com.illine.weather.geomagnetic.model.base.TimeIntervalType;
import com.illine.weather.geomagnetic.model.dto.ForecastDto;
import com.illine.weather.geomagnetic.model.dto.MobileForecastResponse;
import com.illine.weather.geomagnetic.model.dto.TxtForecastDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

import static com.illine.weather.geomagnetic.model.base.IndexType.indexOf;
import static com.illine.weather.geomagnetic.model.base.TimeIntervalType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoGeneratorHelper {

    public static final IndexType DEFAULT_INDEX = IndexType.UNSETTLED;
    public static final TimeIntervalType DEFAULT_INTERVAL = TimeIntervalType.INTERVAL_12_15;
    public static final LocalDate DEFAULT_DATE = LocalDate.now();

    private static final Locale CHECK_DATE_LOCALE = Locale.ENGLISH;
    private static final String CHECK_DATE_PATTERN = "dd MMM";
    private static final DateTimeFormatter CHECK_DATE_FORMATTER = DateTimeFormatter.ofPattern(CHECK_DATE_PATTERN, CHECK_DATE_LOCALE);

    private static final String TXT_FORECAST_PATTERN = ":Product: Geomagnetic Forecast\n" +
            ":Issued: Year Month Date 2205 UTC\n" +
            "# Prepared by the U.S. Dept. of Commerce, NOAA, Space Weather Prediction Center\n" +
            "#\n" +
            "NOAA Ap Index Forecast\n" +
            "Observed Date\n" +
            "Estimated Date\n" +
            "Predicted DateFrom-DateTo 012-008-005\n" +
            "\n" +
            "NOAA Geomagnetic Activity Probabilities today-afterTomorrow\n" +
            "Active                40/25/10\n" +
            "Minor storm           25/05/01\n" +
            "Moderate storm        10/01/01\n" +
            "Strong-Extreme storm  01/01/01\n" +
            "\n" +
            "NOAA Kp index forecast %s - %s\n" +
            "           Today  Tomorrow    AfterTomorrow\n" +
            "00-03UT        4         3         2\n" +
            "03-06UT        4         3         2\n" +
            "06-09UT        4         3         2\n" +
            "09-12UT        4         3         2\n" +
            "12-15UT        4         3         2\n" +
            "15-18UT        4         3         2\n" +
            "18-21UT        4         3         2\n" +
            "21-00UT        4         3         2";

    public static Set<TxtForecastDto> generateTxtForecastDto(LocalDate todayDate) {
        var tomorrowDate = todayDate.plusDays(1);
        var afterTomorrowDate = todayDate.plusDays(2);
        return Set.of(
                new TxtForecastDto(indexOf(1), INTERVAL_00_03, todayDate),
                new TxtForecastDto(indexOf(1), INTERVAL_03_06, todayDate),
                new TxtForecastDto(indexOf(1), INTERVAL_06_09, todayDate),
                new TxtForecastDto(indexOf(1), INTERVAL_09_12, todayDate),
                new TxtForecastDto(indexOf(1), INTERVAL_12_15, todayDate),
                new TxtForecastDto(indexOf(1), INTERVAL_15_18, todayDate),
                new TxtForecastDto(indexOf(1), INTERVAL_18_21, todayDate),
                new TxtForecastDto(indexOf(1), INTERVAL_21_00, todayDate),

                new TxtForecastDto(indexOf(2), INTERVAL_00_03, tomorrowDate),
                new TxtForecastDto(indexOf(2), INTERVAL_03_06, tomorrowDate),
                new TxtForecastDto(indexOf(2), INTERVAL_06_09, tomorrowDate),
                new TxtForecastDto(indexOf(2), INTERVAL_09_12, tomorrowDate),
                new TxtForecastDto(indexOf(2), INTERVAL_12_15, tomorrowDate),
                new TxtForecastDto(indexOf(2), INTERVAL_15_18, tomorrowDate),
                new TxtForecastDto(indexOf(2), INTERVAL_18_21, tomorrowDate),
                new TxtForecastDto(indexOf(2), INTERVAL_21_00, tomorrowDate),

                new TxtForecastDto(indexOf(3), INTERVAL_00_03, afterTomorrowDate),
                new TxtForecastDto(indexOf(3), INTERVAL_03_06, afterTomorrowDate),
                new TxtForecastDto(indexOf(3), INTERVAL_06_09, afterTomorrowDate),
                new TxtForecastDto(indexOf(3), INTERVAL_09_12, afterTomorrowDate),
                new TxtForecastDto(indexOf(3), INTERVAL_12_15, afterTomorrowDate),
                new TxtForecastDto(indexOf(3), INTERVAL_15_18, afterTomorrowDate),
                new TxtForecastDto(indexOf(3), INTERVAL_18_21, afterTomorrowDate),
                new TxtForecastDto(indexOf(3), INTERVAL_21_00, afterTomorrowDate)
        );
    }

    public static ResponseEntity<String> generateSwpcNoaaResponseEntity() {
        var formattedToday = DEFAULT_DATE.format(CHECK_DATE_FORMATTER);
        var formattedAfterTomorrow = DEFAULT_DATE.plusDays(2).format(CHECK_DATE_FORMATTER);
        var textForecast = String.format(TXT_FORECAST_PATTERN, formattedToday, formattedAfterTomorrow);
        return ResponseEntity.ok(textForecast);
    }

    public static ForecastDto generateForecastDto() {
        var dto = new ForecastDto();
        dto.setForecastDate(DEFAULT_DATE);
        dto.setForecastTime(DEFAULT_INTERVAL.getTimeInterval());
        dto.setIndex(DEFAULT_INDEX);
        return dto;
    }

    public static ForecastDto generateForecastDto(LocalDate forecastDate, LocalTime forecastTime) {
        var dto = new ForecastDto();
        dto.setForecastDate(forecastDate);
        dto.setForecastTime(forecastTime);
        dto.setIndex(DEFAULT_INDEX);
        return dto;
    }

    public static TxtForecastDto generateTxtForecastDto() {
        return new TxtForecastDto(DEFAULT_INDEX, DEFAULT_INTERVAL, DEFAULT_DATE);
    }

    public static Set<ForecastDto> generateDiurnalForecastDtoSet() {
        return Set.of(
                generateForecastDto(DEFAULT_DATE, INTERVAL_03_06.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_06_09.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_09_12.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_12_15.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_15_18.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_18_21.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_21_00.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_00_03.getTimeInterval())
        );
    }

    public static Set<ForecastDto> generateCurrentForecastDtoSet() {
        return Set.of(
                generateForecastDto(DEFAULT_DATE, INTERVAL_15_18.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_18_21.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_21_00.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_00_03.getTimeInterval())
        );
    }

    public static Set<ForecastDto> generateThreeDaysForecastDtoSet() {
        return Set.of(
                generateForecastDto(DEFAULT_DATE, INTERVAL_03_06.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_06_09.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_09_12.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_12_15.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_15_18.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_18_21.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_21_00.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE, INTERVAL_00_03.getTimeInterval()),

                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_03_06.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_06_09.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_09_12.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_12_15.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_15_18.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_18_21.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_21_00.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(1), INTERVAL_00_03.getTimeInterval()),

                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_03_06.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_06_09.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_09_12.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_12_15.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_15_18.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_18_21.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_21_00.getTimeInterval()),
                generateForecastDto(DEFAULT_DATE.plusDays(2), INTERVAL_00_03.getTimeInterval())
        );
    }

    public static MobileForecastResponse generateMobileForecastResponse() {
        return new MobileForecastResponse();
    }
}