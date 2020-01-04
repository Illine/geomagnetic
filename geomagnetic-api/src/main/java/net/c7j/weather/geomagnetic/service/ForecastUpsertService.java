package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.model.dto.ForecastDto;

import java.time.LocalDate;
import java.util.Set;

public interface ForecastUpsertService {

    Set<ForecastDto> upsertForecasts(Set<ForecastDto> forecasts, LocalDate date);
}