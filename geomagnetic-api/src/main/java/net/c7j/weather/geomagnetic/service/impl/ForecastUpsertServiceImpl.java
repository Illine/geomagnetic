package net.c7j.weather.geomagnetic.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.service.ForecastUpsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j(topic = "GEOMAGNETIC_SERVICE")
@Service
public class ForecastUpsertServiceImpl implements ForecastUpsertService {

    private final ForecastAccessService forecastAccessService;

    @Autowired
    ForecastUpsertServiceImpl(ForecastAccessService forecastAccessService) {
        this.forecastAccessService = forecastAccessService;
    }
    
    @Override
    public List<ForecastEntity> upsertForecasts(Stream<ForecastEntity> forecasts, LocalDate date) {
        Assert.notNull(forecasts, "The 'forecasts' shouldn't be null!");
        Assert.notNull(date, "The 'date' shouldn't be null!");
        LOGGER.info("The upsert of the forecast is starting...");
        try (var threeDayEntityStream = forecastAccessService.findThreeDay(date)) {
            var date2ThreeDayEntity =
                    threeDayEntityStream
                            .collect(Collectors.toMap(k -> LocalDateTime.of(k.getForecastDate(), k.getForecastTime()), Function.identity()));
            return forecasts.map(merge(date2ThreeDayEntity)).collect(Collectors.toList());
        }
    }

    private Function<ForecastEntity, ForecastEntity> merge(Map<LocalDateTime, ForecastEntity> date2ThreeDayEntity) {
        return it -> date2ThreeDayEntity.merge(LocalDateTime.of(it.getForecastDate(), it.getForecastTime()), it, remappingFunction());
    }

    private BiFunction<ForecastEntity, ForecastEntity, ForecastEntity> remappingFunction() {
        return (oldEntity, newEntity) -> oldEntity.updateIndex(newEntity.getIndex());
    }
}