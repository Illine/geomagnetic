package ru.illine.weather.geomagnetic.service;

import ru.illine.weather.geomagnetic.model.dto.TxtForecastDto;

import java.util.Set;

public interface ForecastParserService {

    Set<TxtForecastDto> parse(String stringForecast);

}