package com.illine.weather.geomagnetic.scheduler;

import com.illine.weather.geomagnetic.exception.SwpcNoaaException;
import com.illine.weather.geomagnetic.service.EtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "application.etl.scheduled", name = "enabled", havingValue = "true")
@Slf4j(topic = "GEOMAGNETIC-SCHEDULER")
public class ForecastScheduler {

    private final EtlService forecastEtlService;

    @Autowired
    ForecastScheduler(EtlService forecastEtlService) {
        this.forecastEtlService = forecastEtlService;
    }

    @Scheduled(cron = "${application.etl.scheduled.cron}")
    public void scheduleForecast() {
        LOGGER.info("Starting forecast scheduler...");
        try {
            forecastEtlService.updateForecasts();
        } catch (SwpcNoaaException e) {
            LOGGER.error("The Swpc Noaa isn't availability! ", e);
        }
    }
}