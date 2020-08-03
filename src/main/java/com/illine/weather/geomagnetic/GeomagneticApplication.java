package com.illine.weather.geomagnetic;

import com.illine.weather.geomagnetic.config.property.LogbookProperties;
import com.illine.weather.geomagnetic.config.property.RestProperties;
import com.illine.weather.geomagnetic.config.property.RestRetryProperties;
import com.illine.weather.geomagnetic.config.property.SwpcNoaaProperties;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Generated
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties(value =
        {
                RestProperties.class, SwpcNoaaProperties.class,
                RestRetryProperties.class, LogbookProperties.class
        }
)
public class GeomagneticApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeomagneticApplication.class, args);
    }
}