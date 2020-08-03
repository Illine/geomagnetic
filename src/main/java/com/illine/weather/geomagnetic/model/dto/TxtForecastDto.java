package com.illine.weather.geomagnetic.model.dto;

import com.illine.weather.geomagnetic.model.base.IndexType;
import com.illine.weather.geomagnetic.model.base.TimeIntervalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class TxtForecastDto {

    private final IndexType index;
    private final TimeIntervalType interval;
    private final LocalDate forecastDate;
}