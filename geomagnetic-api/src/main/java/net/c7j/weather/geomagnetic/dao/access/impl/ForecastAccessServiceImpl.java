package net.c7j.weather.geomagnetic.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.base.TimeIntervalType;
import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.dao.repository.ForecastRepository;
import net.c7j.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j(topic = "GEOMAGNETIC-ACCESS")
public class ForecastAccessServiceImpl implements ForecastAccessService {

    private static final String DEFAULT_NON_NULL_MESSAGE = "The 'today' shouldn't be null!";

    private final ForecastRepository forecastRepository;
    private final ForecastDtoMapper mapper;

    @Autowired
    ForecastAccessServiceImpl(ForecastRepository forecastRepository, ForecastDtoMapper mapper) {
        this.forecastRepository = forecastRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Stream<ForecastDto> forecasts) {
        Assert.notNull(forecasts, "The 'collection' shouldn't be null!");
        LOGGER.info("A collection of a 'ForecastEntity' will be saved or updated.");
        var forecastEntities = mapper.convertToEntity(forecasts).collect(Collectors.toList());
        forecastRepository.saveAll(forecastEntities);
    }

    @Override
    public Stream<ForecastDto> findDiurnal(LocalDate today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A diurnal 'ForecastDto' is been looking for date: {}", today);
        var diurnalForecast = forecastRepository.findAllByForecastDateBetween(today, today);
        return mapper.convertToDto(diurnalForecast);
    }

    @Override
    public Stream<ForecastDto> findCurrent(LocalDateTime today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A current 'ForecastDto' is been looking for date: {}", today);
        var currentDate = today.toLocalDate();
        var currentTime = today.toLocalTime();
        var currentInterval = TimeIntervalType.timeIntervalOf(currentTime.getHour());
        var currentForecast = forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(currentDate, currentDate, currentInterval);
        return mapper.convertToDto(currentForecast);
    }

    @Override
    public Stream<ForecastDto> findThreeDay(LocalDate today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A three days 'ForecastDto' is been looking for date: {}", today);
        var todayPlusThreeDays = today.plusDays(3);
        var threeDayForecast = forecastRepository.findAllByForecastDateBetween(today, todayPlusThreeDays);
        return mapper.convertToDto(threeDayForecast);
    }

    @Override
    public Stream<ForecastEntity> getThreeDay(LocalDate today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A three days 'ForecastEntity' is been getting for date: {}", today);
        var todayPlusThreeDays = today.plusDays(3);
        return forecastRepository.findAllByForecastDateBetween(today, todayPlusThreeDays);
    }
}