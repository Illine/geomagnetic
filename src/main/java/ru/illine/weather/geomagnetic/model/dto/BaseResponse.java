package ru.illine.weather.geomagnetic.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    @ApiModelProperty(required = true)
    @JsonProperty(value = "message")
    protected final String message;

    public BaseResponse() {
        this.message = "Success";
    }

    public BaseResponse(String message) {
        this.message = message;
    }
}