package net.c7j.weather.geomagnetic.dao.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.c7j.weather.geomagnetic.dao.base.IndexType;
import net.c7j.weather.geomagnetic.dao.base.TimeIntervalType;
import net.c7j.weather.geomagnetic.util.JsonWriter;

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