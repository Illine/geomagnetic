package ru.illine.weather.geomagnetic.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import ru.illine.weather.geomagnetic.exception.NotFoundException;
import ru.illine.weather.geomagnetic.model.dto.ForecastDto;
import ru.illine.weather.geomagnetic.service.ForecastService;
import ru.illine.weather.geomagnetic.service.HandleException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "GEOMAGNETIC-SERVICE")
public class ForecastServiceImpl implements ForecastService, HandleException {

    private final ForecastAccessService forecastAccessService;

    @Autowired
    ForecastServiceImpl(ForecastAccessService forecastAccessService) {
        this.forecastAccessService = forecastAccessService;
    }

    @Override
    public List<ForecastDto> findDiurnal() {
        LOGGER.info("A diurnal forecast is requested for UTC zone.");
        var forecasts = compareAndToList(forecastAccessService.findDiurnal(LocalDate.now(ZoneOffset.UTC)));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("diurnal"));
        return forecasts;
    }

    @Override
    public List<ForecastDto> findCurrent() {
        LOGGER.info("The current forecast is requested for UTC zone.");
        var forecasts = compareAndToList(forecastAccessService.findCurrent(LocalDateTime.now(ZoneOffset.UTC)));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("current"));
        return forecasts;
    }

    @Override
    public List<ForecastDto> findThreeDays() {
        LOGGER.info("A three days forecast is requested for UTC zone.");
        var forecasts = compareAndToList(forecastAccessService.findThreeDays(LocalDate.now(ZoneOffset.UTC)));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("three days"));
        return forecasts;
    }

    @Override
    public <T extends RuntimeException> void throwWhen(List<?> list, Predicate<List<?>> predicate, Supplier<T> exception) {
        LOGGER.debug("Verification the list of results of the forecast for emptiness");
        HandleException.super.throwWhen(list, predicate, exception);
    }

    private Supplier<NotFoundException> notFoundException(String forecastType) {
        return () -> new NotFoundException(String.format("Not found %s forecast!", forecastType));
    }

    private List<ForecastDto> compareAndToList(Set<ForecastDto> forecasts) {
        return forecasts
                .stream()
                .sorted(ForecastDto::compareTo)
                .collect(Collectors.toList());
    }
}