package com.illine.weather.geomagnetic.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class MobileForecastResponse extends BaseResponse {

    private List<MobileForecastDto> forecasts = Collections.emptyList();

    public MobileForecastResponse(Set<MobileForecastDto> forecasts) {
        this.forecasts = List.copyOf(forecasts);
    }
}