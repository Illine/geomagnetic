package ru.illine.weather.geomagnetic.dao.access.impl;

import ru.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import ru.illine.weather.geomagnetic.dao.repository.ForecastRepository;
import ru.illine.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import ru.illine.weather.geomagnetic.model.base.TimeIntervalType;
import ru.illine.weather.geomagnetic.model.dto.ForecastDto;
import lombok.extern.slf4j.Slf4j;
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
    public void update(Collection<ForecastDto> forecasts) {
        Assert.notNull(forecasts, "The 'collection' shouldn't be null!");
        LOGGER.info("A collection of a 'ForecastEntity' will be updated.");
        forecastRepository.saveAll(forecastMapper.convertToSources(forecasts));
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ForecastDto> findDiurnal(LocalDate from) {
        Assert.notNull(from, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A diurnal 'ForecastDto' is been looking for date: {}", from);
        var forecasts = forecastRepository.findAllByForecastDateBetween(from, from);
        return forecasts.stream().map(forecastMapper::convertToDestination).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ForecastDto> findCurrent(LocalDateTime from) {
        Assert.notNull(from, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A current 'ForecastDto' is been looking for date: {}", from);
        var currentDate = from.toLocalDate();
        var currentTime = from.toLocalTime();
        var currentInterval = TimeIntervalType.timeIntervalOf(currentTime.getHour());
        var forecasts = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(currentDate, currentDate, currentInterval);
        return forecasts.stream().map(forecastMapper::convertToDestination).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<ForecastDto> findThreeDays(LocalDate from) {
        Assert.notNull(from, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A three days 'ForecastEntity' is been getting for date: {}", from);
        var todayPlusTwoDays = from.plusDays(2);
        var forecasts = forecastRepository.findAllByForecastDateBetween(from, todayPlusTwoDays);
        return forecasts.stream().map(forecastMapper::convertToDestination).collect(Collectors.toSet());
    }
}