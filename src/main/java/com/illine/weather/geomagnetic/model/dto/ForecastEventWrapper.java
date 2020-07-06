package com.illine.weather.geomagnetic.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ForecastEventWrapper {

    @NotEmpty
    private final Set<TxtForecastDto> txtForecasts;

}