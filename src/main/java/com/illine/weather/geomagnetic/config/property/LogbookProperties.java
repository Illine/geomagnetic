package com.illine.weather.geomagnetic.config.property;

import ch.qos.logback.classic.Level;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Validated
@ConfigurationProperties("logbook.logger")
public class LogbookProperties {

    @NotEmpty
    private String name;

    @Pattern(regexp = "^(OFF|DEBUG|INFO|WARN)$")
    private String level;

    public Level getLevel() {
        return Level.valueOf(level);
    }
}