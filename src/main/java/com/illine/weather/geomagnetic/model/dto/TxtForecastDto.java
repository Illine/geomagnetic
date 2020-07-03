package com.illine.weather.geomagnetic.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import com.illine.weather.geomagnetic.model.base.IndexType;
import com.illine.weather.geomagnetic.model.base.TimeIntervalType;
import com.illine.weather.geomagnetic.util.JsonWriter;

import java.time.LocalDate;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class TxtForecastDto {

    private IndexType index;
    private TimeIntervalType interval;
    private LocalDate forecastDate;

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }
}