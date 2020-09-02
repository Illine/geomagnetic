package ru.illine.weather.geomagnetic.scheduler;

import ru.illine.weather.geomagnetic.exception.SwpcNoaaException;
import ru.illine.weather.geomagnetic.service.EtlService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

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
    @SchedulerLock(name = "updateForecasts", lockAtLeastFor = "PT5M", lockAtMostFor = "PT5M")
    public void update() {
        LOGGER.info("A forecast scheduler is launched at: {} and node: {}", LocalTime.now(), System.getenv("HOSTNAME"));
        LOGGER.info("Starting forecast scheduler...");
        try {
            forecastEtlService.updateForecasts();
        } catch (SwpcNoaaException e) {
            LOGGER.error("The Swpc Noaa isn't availability! ", e);
        }
    }
}