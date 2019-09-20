package net.c7j.weather.geomagnetic.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.util.JsonWriter;

import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ForecastResponse extends BaseResponse {

    private Collection<ForecastDto> geomagneticForecasts = Collections.emptyList();

    public ForecastResponse(Collection<ForecastDto> geomagneticForecasts) {
        this.geomagneticForecasts = geomagneticForecasts;
    }

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }
}