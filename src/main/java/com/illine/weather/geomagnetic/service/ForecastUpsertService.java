package com.illine.weather.geomagnetic.service;

import com.illine.weather.geomagnetic.model.dto.ForecastDto;

import java.time.LocalDate;
import java.util.Set;

public interface ForecastUpsertService {

    Set<ForecastDto> upsertForecasts(Set<ForecastDto> forecasts, LocalDate date);
}