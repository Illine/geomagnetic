package net.c7j.weather.geomagnetic.config.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "rest")
public class RestProperties {

    @NotNull
    @Min(1)
    private Integer maxThread;
    @NotNull
    @Min(1)
    private Integer maxRoute;
    @NotNull
    @Min(1000)
    private Integer readTimeout;
    @NotNull
    @Min(1000)
    private Integer connectionTimeout;
    @NotNull
    private SwpNoaa swpNoaa;

    @Validated
    @Getter
    @Setter
    public static class SwpNoaa {

        @Pattern(regexp = "^(http)(s?)$")
        private String schema;
        @NotEmpty
        private String host;
        @NotEmpty
        private String path;
    }
}