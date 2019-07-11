package net.c7j.weather.geomagnetic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.util.Map;

import static java.util.Map.entry;

@Configuration
class TimeIntervalConfig {

    @Bean
    Map<Integer, LocalTime> hourToInterval() {
        return Map.ofEntries(
                entry(0, LocalTime.of(0, 0)), entry(1, LocalTime.of(0, 0)), entry(2, LocalTime.of(0, 0)),
                entry(3, LocalTime.of(3, 0)), entry(4, LocalTime.of(3, 0)), entry(5, LocalTime.of(3, 0)),
                entry(6, LocalTime.of(6, 0)), entry(7, LocalTime.of(6, 0)), entry(8, LocalTime.of(6, 0)),
                entry(9, LocalTime.of(9, 0)), entry(10, LocalTime.of(9, 0)), entry(11, LocalTime.of(9, 0)),
                entry(12, LocalTime.of(12, 0)), entry(13, LocalTime.of(12, 0)), entry(14, LocalTime.of(12, 0)),
                entry(15, LocalTime.of(15, 0)), entry(16, LocalTime.of(15, 0)), entry(17, LocalTime.of(15, 0)),
                entry(18, LocalTime.of(18, 0)), entry(19, LocalTime.of(18, 0)), entry(20, LocalTime.of(18, 0)),
                entry(21, LocalTime.of(21, 0)), entry(22, LocalTime.of(21, 0)), entry(23, LocalTime.of(21, 0))
        );
    }

}