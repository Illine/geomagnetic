package net.c7j.weather.geomagnetic.dao.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForecastEventWrapper {

    @NotEmpty
    private Set<TxtForecastDto> txtForecasts;

}