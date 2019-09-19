package net.c7j.weather.geomagnetic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ForecastEventWrapper {

    @NotEmpty
    private Set<TxtForecastDto> txtForecasts;

}