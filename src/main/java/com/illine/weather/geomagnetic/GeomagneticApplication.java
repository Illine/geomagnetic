package com.illine.weather.geomagnetic;

import com.illine.weather.geomagnetic.config.rest.RestProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties(RestProperties.class)
public class GeomagneticApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeomagneticApplication.class, args);
    }
}