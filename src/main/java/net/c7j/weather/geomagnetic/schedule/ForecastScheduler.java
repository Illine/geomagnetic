package net.c7j.weather.geomagnetic.schedule;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.client.SwpNoaaClient;
import net.c7j.weather.geomagnetic.event.publisher.ForecastPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j(topic = "GEOMAGNETIC_SCHEDULE")
public class ForecastScheduler {

    private final SwpNoaaClient swpNoaaClient;
    private final ForecastPublisher forecastPublisher;

    @Autowired
    ForecastScheduler(SwpNoaaClient swpNoaaClient, ForecastPublisher forecastPublisher) {
        this.swpNoaaClient = swpNoaaClient;
        this.forecastPublisher = forecastPublisher;
    }

    @Scheduled(cron = "${schedule.forecast.cron}")
    public void receiveForecast() {
        LOGGER.info("The Receive Forecast is starting...");
        try {
            var forecast = swpNoaaClient.get3DayGeomagForecast();
            forecastPublisher.publish(forecast);
        } catch (RestClientException e) {
            LOGGER.error("The Swp Noaa isn't availability! ", e);
        }
    }
}