package ru.illine.weather.geomagnetic.model.dto;

import ru.illine.weather.geomagnetic.model.base.IndexType;
import ru.illine.weather.geomagnetic.model.base.TimeIntervalType;
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