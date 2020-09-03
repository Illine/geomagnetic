package ru.illine.weather.geomagnetic.service;

import ru.illine.weather.geomagnetic.model.dto.ForecastDto;

import java.util.List;

public interface ForecastService {

    List<ForecastDto> findDiurnal();

    List<ForecastDto> findCurrent();

    List<ForecastDto> findThreeDays();

}