package ru.illine.weather.geomagnetic.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.illine.weather.geomagnetic.model.base.IndexType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MobileForecastDto {

    @ApiModelProperty(required = true)
    @JsonProperty(value = "value")
    private IndexType index;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "time")
    private Long time;

}