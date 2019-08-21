package net.c7j.weather.geomagnetic.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.service.ForecastUpsertService;
import net.c7j.weather.geomagnetic.service.HandleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j(topic = "GEOMAGNETIC_SERVICE")
@Service
public class ForecastUpsertServiceImpl implements ForecastUpsertService, HandleException {

    private final ForecastAccessService forecastAccessService;

    @Autowired
    ForecastUpsertServiceImpl(ForecastAccessService forecastAccessService) {
        this.forecastAccessService = forecastAccessService;
    }
    
    @Override
    public Stream<ForecastEntity> upsertForecasts(Stream<ForecastEntity> forecasts, LocalDate date) {
        Assert.notNull(forecasts, "The 'forecasts' shouldn't be null!");
        Assert.notNull(date, "The 'date' shouldn't be null!");
        LOGGER.info("The upsert of the forecast is starting...");
        try (var threeDayEntityStream = forecastAccessService.getThreeDay(date)) {
            var date2ThreeDayEntity =
                    threeDayEntityStream
                            .collect(Collectors.toMap(k -> LocalDateTime.of(k.getForecastDate(), k.getForecastTime()), Function.identity()));
            throwWhen(date2ThreeDayEntity, Map::isEmpty, () -> new NotFoundException("Not found a three day forecast!"));
            return forecasts.map(merge(date2ThreeDayEntity));
        }
    }

    @Override
    public <T extends RuntimeException> void throwWhen(Map<?, ?> map, Predicate<Map<?, ?>> predicate, Supplier<T> exception) {
        LOGGER.debug("Verification the list of results of the forecast for emptiness");
        HandleException.super.throwWhen(map, predicate, exception);
    }

    private Function<ForecastEntity, ForecastEntity> merge(Map<LocalDateTime, ForecastEntity> date2ThreeDayEntity) {
        return it -> date2ThreeDayEntity.merge(LocalDateTime.of(it.getForecastDate(), it.getForecastTime()), it, remappingFunction());
    }

    private BiFunction<ForecastEntity, ForecastEntity, ForecastEntity> remappingFunction() {
        return (oldEntity, newEntity) -> oldEntity.updateIndex(newEntity.getIndex());
    }
}