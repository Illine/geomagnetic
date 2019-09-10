package net.c7j.weather.geomagnetic.service.impl;

import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.dto.ForecastResponse;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import net.c7j.weather.geomagnetic.service.HandleException;
import net.c7j.weather.geomagnetic.service.ViewForecastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "GEOMAGNETIC-SERVICE")
public class ViewForecastServiceImpl implements ViewForecastService<ForecastResponse>, HandleException {

    private final ForecastAccessService forecastAccessService;
    private final ForecastDtoMapper mapper;

    @Autowired
    ViewForecastServiceImpl(ForecastAccessService forecastAccessService, ForecastDtoMapper mapper) {
        this.forecastAccessService = forecastAccessService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ForecastResponse> getDiurnal() {
        LOGGER.info("Receipt of a diurnal forecast is starting...");
        var forecasts = compareAndToList(forecastAccessService.findDiurnal(LocalDate.now()));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("diurnal"));
        return ResponseEntity.ok(new ForecastResponse(forecasts));
    }

    @Override
    public ResponseEntity<ForecastResponse> getCurrent() {
        LOGGER.info("Receipt of a current forecast is starting...");
        List<ForecastDto> forecasts = compareAndToList(forecastAccessService.findCurrent(LocalDateTime.now()));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("current"));
        return ResponseEntity.ok(new ForecastResponse(forecasts));
    }

    @Override
    public ResponseEntity<ForecastResponse> getThreeDay() {
        LOGGER.info("Receipt of a three days' forecast is starting...");
        List<ForecastDto> forecasts = compareAndToList(forecastAccessService.findThreeDay(LocalDate.now()));
        throwWhen(forecasts, Collection::isEmpty, notFoundException("three day"));
        return ResponseEntity.ok(new ForecastResponse(forecasts));
    }

    @Override
    public <T extends RuntimeException> void throwWhen(List<?> list, Predicate<List<?>> predicate, Supplier<T> exception) {
        LOGGER.debug("Verification the list of results of the forecast for emptiness");
        HandleException.super.throwWhen(list, predicate, exception);
    }

    private Supplier<NotFoundException> notFoundException(String forecastType) {
        return () -> new NotFoundException(String.format("Not found %s forecast!", forecastType));
    }

    private List<ForecastDto> compareAndToList(Stream<ForecastEntity> forecastEntities) {
        return forecastEntities
                .map(mapper::convertToDto)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(ForecastDto::compareTo)
                .collect(Collectors.toList());
    }
}