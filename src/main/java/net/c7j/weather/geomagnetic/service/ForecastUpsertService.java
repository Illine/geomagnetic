package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface ForecastUpsertService {

    Stream<ForecastEntity> upsertForecasts(Stream<ForecastEntity> forecasts, LocalDate date);
}