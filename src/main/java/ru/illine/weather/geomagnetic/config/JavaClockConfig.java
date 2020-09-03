package ru.illine.weather.geomagnetic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class JavaClockConfig {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

}