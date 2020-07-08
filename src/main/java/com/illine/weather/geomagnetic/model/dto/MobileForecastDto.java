package com.illine.weather.geomagnetic.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.illine.weather.geomagnetic.model.base.IndexType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MobileForecastDto {

    @JsonProperty(value = "value")
    private IndexType index;

    @JsonProperty(value = "time")
    private Long time;

}