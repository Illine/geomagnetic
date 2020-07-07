package com.illine.weather.geomagnetic.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "rest.retry")
public
class RestRetryProperties {

    @NotNull
    @Min(50)
    @Max(1000 * 60)
    private Long delayInMs;

    @NotNull
    @Min(2)
    @Max(10)
    private Integer maxAttempts;

}