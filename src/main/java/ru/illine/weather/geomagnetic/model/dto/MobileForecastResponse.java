package ru.illine.weather.geomagnetic.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MobileForecastResponse extends BaseResponse {

    @ApiModelProperty(required = true)
    @JsonProperty(value = "forecasts")
    private final List<MobileForecastDto> forecasts;

    public MobileForecastResponse() {
        this.forecasts = List.of();
    }

    public MobileForecastResponse(List<MobileForecastDto> forecasts) {
        this.forecasts = forecasts;
    }
}