package net.c7j.weather.geomagnetic.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.dao.repository.ForecastRepository;
import net.c7j.weather.geomagnetic.mapper.impl.ForecastDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Slf4j(topic = "GEOMAGNETIC-ACCESS")
public class ForecastAccessServiceImpl implements ForecastAccessService {

    private static final String DEFAULT_NON_NULL_MESSAGE = "The 'today' shouldn't be null!";

    private final ForecastRepository forecastRepository;
    private final ForecastDtoMapper mapper;
    private final Map<Integer, LocalTime> hourToInterval;

    @Autowired
    ForecastAccessServiceImpl(ForecastRepository forecastRepository,
                              ForecastDtoMapper mapper, Map<Integer, LocalTime> hourToInterval) {
        this.forecastRepository = forecastRepository;
        this.mapper = mapper;
        this.hourToInterval = hourToInterval;
    }

    @Override
    public void upsert(Collection<ForecastEntity> collection) {
        Assert.notEmpty(collection, "The 'collection' shouldn't be null or empty!");
        LOGGER.info("A collection of a 'ForecastEntity' will be saved or updated.");
        forecastRepository.saveAll(collection);
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
        var currentInterval = hourToInterval.get(currentTime.getHour());
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