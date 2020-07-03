package com.illine.weather.geomagnetic.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import com.illine.weather.geomagnetic.exception.NotFoundException;
import com.illine.weather.geomagnetic.model.dto.ForecastDto;
import com.illine.weather.geomagnetic.service.ForecastService;
import com.illine.weather.geomagnetic.service.HandleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        LOGGER.info("Receipt of a diurnal forecast is starting...");
        var forecasts = compareAndToList(forecastAccessService.findDiurnal(LocalDate.now()));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("diurnal"));
        return forecasts;
    }

    @Override
    public List<ForecastDto> findCurrent() {
        LOGGER.info("Receipt of a current forecast is starting...");
        var forecasts = compareAndToList(forecastAccessService.findCurrent(LocalDateTime.now()));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("current"));
        return forecasts;
    }

    @Override
    public List<ForecastDto> findThreeDays() {
        LOGGER.info("Receipt of a three days' forecast is starting...");
        var forecasts = compareAndToList(forecastAccessService.findThreeDays(LocalDate.now()));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("three day"));
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