package net.c7j.weather.geomagnetic.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.dao.access.ForecastAccessService;
import net.c7j.weather.geomagnetic.model.dto.ForecastDto;
import net.c7j.weather.geomagnetic.service.ForecastUpsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j(topic = "GEOMAGNETIC-SERVICE")
@Service
public class ForecastUpsertServiceImpl implements ForecastUpsertService {

    private final ForecastAccessService forecastAccessService;

    @Autowired
    ForecastUpsertServiceImpl(ForecastAccessService forecastAccessService) {
        this.forecastAccessService = forecastAccessService;
    }

    @Override
    public Set<ForecastDto> upsertForecasts(Set<ForecastDto> forecasts, LocalDate date) {
        Assert.notNull(forecasts, "The 'forecasts' shouldn't be null!");
        Assert.notNull(date, "The 'date' shouldn't be null!");
        LOGGER.info("The upsert of the forecast is starting...");
        var date2ThreeDayForecast =
                forecastAccessService.findThreeDays(date)
                        .stream()
                        .collect(Collectors.toMap(k -> LocalDateTime.of(k.getForecastDate(), k.getForecastTime()), Function.identity()));
        return forecasts
                .stream()
                .map(merge(date2ThreeDayForecast))
                .collect(Collectors.toSet());
    }

    private Function<ForecastDto, ForecastDto> merge(Map<LocalDateTime, ForecastDto> date2ThreeDayEntity) {
        return it -> date2ThreeDayEntity.merge(LocalDateTime.of(it.getForecastDate(), it.getForecastTime()), it, remappingFunction());
    }

    private BiFunction<ForecastDto, ForecastDto, ForecastDto> remappingFunction() {
        return (oldForecast, newForecast) -> oldForecast.updateIndex(newForecast.getIndex());
    }
}