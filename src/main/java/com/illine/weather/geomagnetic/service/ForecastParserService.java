package com.illine.weather.geomagnetic.service;

import com.illine.weather.geomagnetic.model.dto.TxtForecastDto;

import java.util.Set;

public interface ForecastParserService {

    Set<TxtForecastDto> toParse(String threeDayGeomagForecast);

}