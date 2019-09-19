package net.c7j.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.model.base.ActiveType;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.model.base.TimeIntervalType;
import net.c7j.weather.geomagnetic.model.dto.ForecastDto;
import net.c7j.weather.geomagnetic.model.dto.ForecastEventWrapper;
import net.c7j.weather.geomagnetic.model.dto.TxtForecastDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static net.c7j.weather.geomagnetic.model.base.IndexType.indexOf;
import static net.c7j.weather.geomagnetic.model.base.TimeIntervalType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneratorHelper {

    private static final int SEC_IN_MILLS = 1000;

    private static final IndexType DEFAULT_INDEX = IndexType.UNSETTLED;
    private static final TimeIntervalType DEFAULT_INTERVAL = TimeIntervalType.INTERVAL_12_15;

    private static final Locale CHECK_DATE_LOCALE = Locale.ENGLISH;
    private static final String CHECK_DATE_PATTERN = "dd MMM";
    private static final DateTimeFormatter CHECK_DATE_FORMATTER = DateTimeFormatter.ofPattern(CHECK_DATE_PATTERN, CHECK_DATE_LOCALE);

    private static final LocalDateTime TESTING_LOCAL_DATE_TIME = LocalDateTime.of(2019, 1, 1, 0, 0, 0);

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
            "03-06UT        4         2         2\n" +
            "06-09UT        3         2         1\n" +
            "09-12UT        2         2         1\n" +
            "12-15UT        2         2         1\n" +
            "15-18UT        2         2         1\n" +
            "18-21UT        2         2         1\n" +
            "21-00UT        3         2         2";


    static String generateUri(String path, int port) {
        return new TestUriBuilder().apply(path, port);
    }

    public static String generateAlphabet() {
        return IntStream.rangeClosed('A', 'Z')
                .mapToObj(character -> (char) character)
                .collect(Collectors.toSet())
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining())
                .toLowerCase();
    }

    public static ResponseEntity<String> generateResponseEntity() {
        var formattedToday = LocalDate.now().format(CHECK_DATE_FORMATTER);
        var formattedAfterTomorrow = LocalDate.now().plusDays(2).format(CHECK_DATE_FORMATTER);
        var textForecast = String.format(TXT_FORECAST_PATTERN, formattedToday, formattedAfterTomorrow);
        return ResponseEntity.ok(textForecast);
    }

    public static Stream<ForecastDto> generateStreamForecastDto(int count) {
        return Stream.iterate(0, i -> i + 1)
                .limit(count)
                .map(it -> (int) (Math.random() * 9))
                .map(IndexType::indexOf)
                .map(GeneratorHelper::generateForecastDto);
    }

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

    public static ForecastEventWrapper generateForecastEventWrapper(LocalDate todayDate) {
        var txtForecasts = generateTxtForecastDto(todayDate);
        return new ForecastEventWrapper(txtForecasts);
    }

    public static ForecastEntity generateForecastEntity() {
        var entity = new ForecastEntity();
        entity.setIndex(DEFAULT_INDEX);
        entity.setForecastTime(LocalTime.now());
        entity.setForecastDate(LocalDate.now());
        return entity;
    }

    public static ForecastEntity generateForecastEntity(Long id) {
        var entity = new ForecastEntity();
        entity.setId(id);
        entity.setIndex(DEFAULT_INDEX);
        entity.setForecastTime(TESTING_LOCAL_DATE_TIME.toLocalTime());
        entity.setForecastDate(TESTING_LOCAL_DATE_TIME.toLocalDate());
        entity.setCreated(TESTING_LOCAL_DATE_TIME);
        entity.setModified(TESTING_LOCAL_DATE_TIME);
        entity.setActive(ActiveType.ENABLED);
        return entity;
    }

    public static Stream<ForecastEntity> generateStreamForecastEntity(int count) {
        return Stream.iterate(0, i -> i + 1)
                .limit(count)
                .map(it -> generateForecastEntity());
    }

    public static Stream<ForecastEntity> generateStreamForecastEntity() {
        return generateStreamForecastEntity(LocalDate.now());
    }

    public static Stream<ForecastEntity> generateStreamForecastEntity(LocalDate todayDate) {
        var tomorrowDate = todayDate.plusDays(1);
        var afterTomorrowDate = todayDate.plusDays(2);

        return Stream.of(
                new ForecastEntity(indexOf(1), INTERVAL_00_03.getTimeInterval(), todayDate),
                new ForecastEntity(indexOf(1), INTERVAL_03_06.getTimeInterval(), todayDate),
                new ForecastEntity(indexOf(1), INTERVAL_06_09.getTimeInterval(), todayDate),
                new ForecastEntity(indexOf(1), INTERVAL_09_12.getTimeInterval(), todayDate),
                new ForecastEntity(indexOf(1), INTERVAL_12_15.getTimeInterval(), todayDate),
                new ForecastEntity(indexOf(1), INTERVAL_15_18.getTimeInterval(), todayDate),
                new ForecastEntity(indexOf(1), INTERVAL_18_21.getTimeInterval(), todayDate),
                new ForecastEntity(indexOf(1), INTERVAL_21_00.getTimeInterval(), todayDate),

                new ForecastEntity(indexOf(2), INTERVAL_00_03.getTimeInterval(), tomorrowDate),
                new ForecastEntity(indexOf(2), INTERVAL_03_06.getTimeInterval(), tomorrowDate),
                new ForecastEntity(indexOf(2), INTERVAL_06_09.getTimeInterval(), tomorrowDate),
                new ForecastEntity(indexOf(2), INTERVAL_09_12.getTimeInterval(), tomorrowDate),
                new ForecastEntity(indexOf(2), INTERVAL_12_15.getTimeInterval(), tomorrowDate),
                new ForecastEntity(indexOf(2), INTERVAL_15_18.getTimeInterval(), tomorrowDate),
                new ForecastEntity(indexOf(2), INTERVAL_18_21.getTimeInterval(), tomorrowDate),
                new ForecastEntity(indexOf(2), INTERVAL_21_00.getTimeInterval(), tomorrowDate),

                new ForecastEntity(indexOf(3), INTERVAL_00_03.getTimeInterval(), afterTomorrowDate),
                new ForecastEntity(indexOf(3), INTERVAL_03_06.getTimeInterval(), afterTomorrowDate),
                new ForecastEntity(indexOf(3), INTERVAL_06_09.getTimeInterval(), afterTomorrowDate),
                new ForecastEntity(indexOf(3), INTERVAL_09_12.getTimeInterval(), afterTomorrowDate),
                new ForecastEntity(indexOf(3), INTERVAL_12_15.getTimeInterval(), afterTomorrowDate),
                new ForecastEntity(indexOf(3), INTERVAL_15_18.getTimeInterval(), afterTomorrowDate),
                new ForecastEntity(indexOf(3), INTERVAL_18_21.getTimeInterval(), afterTomorrowDate),
                new ForecastEntity(indexOf(3), INTERVAL_21_00.getTimeInterval(), afterTomorrowDate)
        );
    }


    public static TxtForecastDto generateTxtForecastDto() {
        return generateTxtForecastDto(DEFAULT_INTERVAL, DEFAULT_INDEX);
    }

    public static TxtForecastDto generateTxtForecastDto(TimeIntervalType interval, IndexType index) {
        return new TxtForecastDto(index, interval, TESTING_LOCAL_DATE_TIME.toLocalDate());
    }

    public static Set<TxtForecastDto> generateSetTxtForecastDto() {
        return EnumSet.allOf(TimeIntervalType.class)
                .stream()
                .map(it -> new TxtForecastDto(DEFAULT_INDEX, it, LocalDate.now()))
                .collect(Collectors.toSet());
    }

    public static ForecastDto generateForecastDto(IndexType index, boolean withTime) {
        Long time = withTime ? (System.currentTimeMillis() + (int) (Math.random() * SEC_IN_MILLS)) : null;
        return new ForecastDto(index, time);
    }

    private static ForecastDto generateForecastDto(IndexType index) {
        return generateForecastDto(index, true);
    }

    private static class TestUriBuilder implements BiFunction<String, Integer, String> {

        private static final String BASE_SCHEMA = "http";
        private static final String BASE_HOST = "localhost";

        @Override
        public String apply(String path, Integer port) {
            return UriComponentsBuilder.newInstance()
                    .scheme(BASE_SCHEMA)
                    .host(BASE_HOST)
                    .port(port)
                    .path(path)
                    .toUriString();
        }
    }
}
