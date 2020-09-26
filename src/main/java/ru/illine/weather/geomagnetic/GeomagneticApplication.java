package ru.illine.weather.geomagnetic;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.illine.weather.geomagnetic.config.property.*;

@Generated
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value =
        {
                RestProperties.class,
                SwpcNoaaProperties.class,
                LogbookProperties.class,
                SwaggerProperties.class,
                ApiKeySecurityProperties.class
        }
)
public class GeomagneticApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeomagneticApplication.class, args);
    }
}