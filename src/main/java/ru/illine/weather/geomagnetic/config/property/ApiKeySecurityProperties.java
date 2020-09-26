package ru.illine.weather.geomagnetic.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.security.api-key")
public class ApiKeySecurityProperties {

    private boolean enabled = true;

    private String headerName = "X-Api-Key-Authorization";

    private Set<String> accessKeys = new HashSet<>();

}