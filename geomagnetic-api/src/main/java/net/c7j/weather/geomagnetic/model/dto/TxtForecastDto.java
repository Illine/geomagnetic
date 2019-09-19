package net.c7j.weather.geomagnetic.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.model.base.TimeIntervalType;
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