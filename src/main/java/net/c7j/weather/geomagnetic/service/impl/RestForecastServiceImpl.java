package net.c7j.weather.geomagnetic.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.dto.ForecastResponse;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.service.HandleException;
import net.c7j.weather.geomagnetic.service.RestForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "GEOMAGNETIC-SERVICE")
public class RestForecastServiceImpl implements RestForecastService, HandleException {

    private final ForecastAccessService forecastAccessService;

    @Autowired
    RestForecastServiceImpl(ForecastAccessService forecastAccessService) {
        this.forecastAccessService = forecastAccessService;
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<ForecastResponse> getDiurnal() {
        LOGGER.info("Receipt of a diurnal forecast is starting...");
        List<ForecastDto> forecasts =
                forecastAccessService.findDiurnal(LocalDate.now())
                        .sorted(ForecastDto::compareTo)
                        .collect(Collectors.toList());
        throwWhen(forecasts, Collection::isEmpty, notFoundException("diurnal"));
        return ResponseEntity.ok(new ForecastResponse(forecasts));
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<ForecastResponse> getCurrent() {
        LOGGER.info("Receipt of a current forecast is starting...");
        List<ForecastDto> forecasts =
                forecastAccessService.findCurrent(LocalDateTime.now())
                        .sorted(ForecastDto::compareTo)
                        .collect(Collectors.toList());
        throwWhen(forecasts, Collection::isEmpty, notFoundException("current"));
        return ResponseEntity.ok(new ForecastResponse(forecasts));
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<ForecastResponse> getThreeDay() {
        LOGGER.info("Receipt of a three days' forecast is starting...");
        List<ForecastDto> forecasts =
                forecastAccessService.findThreeDay(LocalDate.now())
                        .sorted(ForecastDto::compareTo)
                        .collect(Collectors.toList());
        throwWhen(forecasts, Collection::isEmpty, notFoundException("three day"));
        return ResponseEntity.ok(new ForecastResponse(forecasts));
    }

    private Supplier<NotFoundException> notFoundException(String forecastType) {
        return () -> new NotFoundException(String.format("Not found %s forecast!", forecastType));
    }

    @Override
    public <T extends RuntimeException> void throwWhen(List<?> list, Predicate<List<?>> predicate, Supplier<T> exception) {
        LOGGER.debug("Verification the list of results of the forecast for emptiness");
        HandleException.super.throwWhen(list, predicate, exception);
    }
}