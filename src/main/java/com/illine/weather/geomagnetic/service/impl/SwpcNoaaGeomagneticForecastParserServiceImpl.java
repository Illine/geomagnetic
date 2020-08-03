package com.illine.weather.geomagnetic.service.impl;

import com.illine.weather.geomagnetic.exception.ParseException;
import com.illine.weather.geomagnetic.model.base.IndexType;
import com.illine.weather.geomagnetic.model.base.TimeIntervalType;
import com.illine.weather.geomagnetic.model.dto.TxtForecastDto;
import com.illine.weather.geomagnetic.service.ForecastParserService;
import com.illine.weather.geomagnetic.service.HandleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "GEOMAGNETIC-SERVICE")
public class SwpcNoaaGeomagneticForecastParserServiceImpl implements ForecastParserService, HandleException {

    private static final Locale CHECK_DATE_LOCALE = Locale.ENGLISH;
    private static final String CHECK_DATE_PATTERN = "dd MMM";
    private static final DateTimeFormatter CHECK_DATE_FORMATTER = DateTimeFormatter.ofPattern(CHECK_DATE_PATTERN, CHECK_DATE_LOCALE);

    private static final String CHECK_DATE_TEMPLATE_PATTERN = "(NOAA Kp index forecast (%s - %s|%s - %s))";
    private static final String PARSE_PATTERN = "^([012][01235689]-[012][01235689]UT)\\s+(\\d)\\s+(\\d)\\s+(\\d)\\s*$";
    private static final Pattern GEOMAGNETIC_FORECAST_PATTERN = Pattern.compile(PARSE_PATTERN);

    private static final int EXPECTED_SIZE_SIZE = 24;
    private static final int GROUP_INTERVAL = 1;
    private static final int GROUP_DATE_TODAY = 2;
    private static final int GROUP_DATE_TOMORROW = 3;
    private static final int GROUP_DATE_AFTER_TOMORROW = 4;

    private final Clock clock;

    @Autowired
    SwpcNoaaGeomagneticForecastParserServiceImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Set<TxtForecastDto> parse(String stringForecast) {
        Assert.hasText(stringForecast, "The 'threeDayGeomagneticForecast' should have a text!");
        LOGGER.info("A text forecast is being parsed to a 'TxtForecastDto' set");
        LOGGER.debug("A file content is: \n===============================================\n{}\n===============================================", stringForecast);
        checkValidDate(stringForecast);
        var txtForecasts = stringForecast.lines()
                .filter(line -> GEOMAGNETIC_FORECAST_PATTERN.matcher(line).find())
                .flatMap(line -> GEOMAGNETIC_FORECAST_PATTERN.matcher(line).results())
                .map(this::createTxtForecast)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableSet());
        throwWhen(txtForecasts, set -> set.size() != EXPECTED_SIZE_SIZE, () -> new ParseException("A 'TxtForecastDto' set should have a size 24"));
        return txtForecasts;
    }

    @Override
    public <T extends RuntimeException> void throwWhen(Set<?> set, Predicate<Set<?>> predicate, Supplier<T> exception) {
        LOGGER.debug("Verification the list of results of the forecast for invalid size");
        HandleException.super.throwWhen(set, predicate, exception);
    }

    private void checkValidDate(String fileContent) {
        LOGGER.debug("Verification a date of the geomagnetic forecast text file");
        var formattedPattern = buildCheckPattern();
        var pattern = Pattern.compile(formattedPattern);
        if (!pattern.matcher(fileContent).find()) {
            throw new ParseException("An invalid geomagnetic forecast date!");
        }
    }

    private String buildCheckPattern() {
        var fromToday = LocalDate.now(clock);
        var fromDayAfterTomorrow = fromToday.plusDays(2);
        var fromYesterday = LocalDate.now(clock).minusDays(1);
        var toTomorrow = fromYesterday.plusDays(2);

        var formattedFromToday = fromToday.format(CHECK_DATE_FORMATTER);
        var formattedToAfterTomorrow = fromDayAfterTomorrow.format(CHECK_DATE_FORMATTER);
        var formattedFromYesterday = fromYesterday.format(CHECK_DATE_FORMATTER);
        var formattedToTomorrow = toTomorrow.format(CHECK_DATE_FORMATTER);

        return String.format(
                CHECK_DATE_TEMPLATE_PATTERN,
                formattedFromToday, formattedToAfterTomorrow,
                formattedFromYesterday, formattedToTomorrow
        );
    }

    private List<TxtForecastDto> createTxtForecast(MatchResult matcher) {
        LOGGER.debug("A dto will be created via: {}", matcher.group().replaceAll("\\s+", " | "));
        var interval = TimeIntervalType.intervalOf(matcher.group(GROUP_INTERVAL));
        var todayDate = LocalDate.now();
        var tomorrowDate = LocalDate.now().plusDays(1);
        var afterTomorrowDate = LocalDate.now().plusDays(2);

        var todayForecast = new TxtForecastDto(IndexType.indexOf(matcher.group(GROUP_DATE_TODAY)), interval, todayDate);
        var tomorrowForecast = new TxtForecastDto(IndexType.indexOf(matcher.group(GROUP_DATE_TOMORROW)), interval, tomorrowDate);
        var afterTomorrowForecast = new TxtForecastDto(IndexType.indexOf(matcher.group(GROUP_DATE_AFTER_TOMORROW)), interval, afterTomorrowDate);
        return List.of(todayForecast, tomorrowForecast, afterTomorrowForecast);
    }
}