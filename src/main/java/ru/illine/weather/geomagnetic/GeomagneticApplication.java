package ru.illine.weather.geomagnetic;

import ru.illine.weather.geomagnetic.config.property.LogbookProperties;
import ru.illine.weather.geomagnetic.config.property.RestProperties;
import ru.illine.weather.geomagnetic.config.property.SwaggerProperties;
import ru.illine.weather.geomagnetic.config.property.SwpcNoaaProperties;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Generated
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableSwagger2
@EnableConfigurationProperties(value =
        {
                RestProperties.class,
                SwpcNoaaProperties.class,
                LogbookProperties.class,
                SwaggerProperties.class
        }
)
public class GeomagneticApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeomagneticApplication.class, args);
    }
}