package com.illine.weather.geomagnetic.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "application.swpc-noaa")
public class SwpcNoaaProperties {

    @Pattern(regexp = "^(http)(s?)$")
    private String schema;

    @NotEmpty
    private String host;

    @NotEmpty
    private String path;

}