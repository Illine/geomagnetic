package com.illine.weather.geomagnetic.dao.access.impl;

import com.illine.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import com.illine.weather.geomagnetic.model.base.TimeIntervalType;
import com.illine.weather.geomagnetic.model.dto.ForecastDto;
import lombok.extern.slf4j.Slf4j;
import com.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import com.illine.weather.geomagnetic.dao.repository.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "GEOMAGNETIC-ACCESS")
public class ForecastAccessServiceImpl implements ForecastAccessService {

    private static final String DEFAULT_NON_NULL_MESSAGE = "The 'today' shouldn't be null!";

    private final ForecastRepository forecastRepository;
    private final ForecastDtoMapper forecastMapper;

    @Autowired
    ForecastAccessServiceImpl(ForecastRepository forecastRepository, ForecastDtoMapper forecastMapper) {
        this.forecastRepository = forecastRepository;
        this.forecastMapper = forecastMapper;
    }

    @Transactional
    @Override
    public void save(Collection<ForecastDto> forecasts) {
        Assert.notNull(forecasts, "The 'collection' shouldn't be null!");
        LOGGER.info("A collection of a 'ForecastEntity' will be saved or updated.");
        forecastRepository.saveAll(forecastMapper.convertToSources(forecasts));
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ForecastDto> findDiurnal(LocalDate today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A diurnal 'ForecastDto' is been looking for date: {}", today);
        var forecasts = forecastRepository.findAllByForecastDateBetween(today, today);
        return forecasts.stream().map(forecastMapper::convertToDestination).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ForecastDto> findCurrent(LocalDateTime today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A current 'ForecastDto' is been looking for date: {}", today);
        var currentDate = today.toLocalDate();
        var currentTime = today.toLocalTime();
        var currentInterval = TimeIntervalType.timeIntervalOf(currentTime.getHour());
        var forecasts = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(currentDate, currentDate, currentInterval);
        return forecasts.stream().map(forecastMapper::convertToDestination).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ForecastDto> findThreeDays(LocalDate today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A three days 'ForecastEntity' is been getting for date: {}", today);
        var todayPlusThreeDays = today.plusDays(3);
        var forecasts = forecastRepository.findAllByForecastDateBetween(today, todayPlusThreeDays);
        return forecasts.stream().map(forecastMapper::convertToDestination).collect(Collectors.toSet());
    }
}