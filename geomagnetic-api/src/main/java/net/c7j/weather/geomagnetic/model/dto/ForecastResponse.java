package net.c7j.weather.geomagnetic.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.util.JsonWriter;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ForecastResponse extends BaseResponse {

    private List<ForecastDto> geomagneticForecasts = Collections.emptyList();

    public ForecastResponse(List<ForecastDto> geomagneticForecasts) {
        this.geomagneticForecasts = geomagneticForecasts;
    }

    @Override
    public String toString() {
        return JsonWriter.toStringAsJson(this);
    }
}