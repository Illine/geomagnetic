package net.c7j.weather.geomagnetic.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.dao.repository.ForecastRepository;
import net.c7j.weather.geomagnetic.model.base.TimeIntervalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

@Service
@Slf4j(topic = "GEOMAGNETIC-ACCESS")
public class ForecastAccessServiceImpl implements ForecastAccessService {

    private static final String DEFAULT_NON_NULL_MESSAGE = "The 'today' shouldn't be null!";

    private final ForecastRepository forecastRepository;

    @Autowired
    ForecastAccessServiceImpl(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @Transactional
    @Override
    public void save(Collection<ForecastEntity> forecasts) {
        Assert.notNull(forecasts, "The 'collection' shouldn't be null!");
        LOGGER.info("A collection of a 'ForecastEntity' will be saved or updated.");
        forecastRepository.saveAll(forecasts);
    }

    @Transactional(readOnly = true)
    @Override
    public Stream<ForecastEntity> findDiurnal(LocalDate today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A diurnal 'ForecastDto' is been looking for date: {}", today);
        return forecastRepository.findAllByForecastDateBetween(today, today);
    }

    @Transactional(readOnly = true)
    @Override
    public Stream<ForecastEntity> findCurrent(LocalDateTime today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A current 'ForecastDto' is been looking for date: {}", today);
        var currentDate = today.toLocalDate();
        var currentTime = today.toLocalTime();
        var currentInterval = TimeIntervalType.timeIntervalOf(currentTime.getHour());
        return forecastRepository.findAllByForecastDateBetweenAndForecastTimeGreaterThanEqual(currentDate, currentDate, currentInterval);
    }

    @Transactional(readOnly = true)
    @Override
    public Stream<ForecastEntity> findThreeDay(LocalDate today) {
        Assert.notNull(today, DEFAULT_NON_NULL_MESSAGE);
        LOGGER.info("A three days 'ForecastEntity' is been getting for date: {}", today);
        var todayPlusThreeDays = today.plusDays(3);
        return forecastRepository.findAllByForecastDateBetween(today, todayPlusThreeDays);
    }
}