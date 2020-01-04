package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.model.dto.ForecastDto;

import java.util.List;

public interface ForecastService {

    List<ForecastDto> findDiurnal();

    List<ForecastDto> findCurrent();

    List<ForecastDto> findThreeDays();

}