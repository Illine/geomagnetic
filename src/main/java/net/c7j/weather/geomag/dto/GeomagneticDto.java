package net.c7j.weather.geomag.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeomagneticDto extends BaseDto {

    @JsonProperty(value = "value")
    private Integer index;

    @JsonProperty(value = "time")
    private Long time;

    public GeomagneticDto(String error) {
        super(false, error);
    }

    public GeomagneticDto(Integer index, Long time) {
        this.index = index;
        this.time = time;
    }
}