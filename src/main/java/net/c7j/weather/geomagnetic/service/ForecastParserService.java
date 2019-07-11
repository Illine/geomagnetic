package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.dao.dto.TxtForecastDto;

import java.util.Set;

public interface ForecastParserService {

    Set<TxtForecastDto> toParse(String threeDayGeomagForecast);

}