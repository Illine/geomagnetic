package ru.illine.weather.geomagnetic.client.impl;

import ru.illine.weather.geomagnetic.client.SwpcNoaaClient;
import ru.illine.weather.geomagnetic.config.property.SwpcNoaaProperties;
import ru.illine.weather.geomagnetic.exception.SwpcNoaaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Function;

@Component
@Slf4j(topic = "GEOMAGNETIC-CLIENT")
public class SwpcNoaaClientImpl implements SwpcNoaaClient {

    private final SwpcNoaaProperties properties;
    private final RestTemplate swpcNoaaRestTemplate;
    private final RetryTemplate restRetryTemplate;

    @Autowired
    SwpcNoaaClientImpl(SwpcNoaaProperties properties,
                       RestTemplate swpcNoaaRestTemplate,
                       RetryTemplate restRetryTemplate) {
        this.properties = properties;
        this.swpcNoaaRestTemplate = swpcNoaaRestTemplate;
        this.restRetryTemplate = restRetryTemplate;
    }

    @Override
    public ResponseEntity<String> get3DayGeomagneticForecast() {
        LOGGER.info("Exchange of a text forecast from SWPC NOAA is starting...");
        return retryGet3DayGeomagneticForecast();
    }

    private ResponseEntity<String> retryGet3DayGeomagneticForecast() {
        return restRetryTemplate.execute(context -> {
            try {
                return swpcNoaaRestTemplate.getForEntity(new SwpcNoaaUriBuilder().apply(properties), String.class);
            } catch (RestClientException e) {
                LOGGER.warn("Retry exception! Attempt is: {}", context.getRetryCount());
                throw new SwpcNoaaException("SWP NOAA returned error!", context.getLastThrowable());
            }
        });
    }

    private static class SwpcNoaaUriBuilder implements Function<SwpcNoaaProperties, String> {

        @Override
        public String apply(SwpcNoaaProperties restProperties) {
            return UriComponentsBuilder.newInstance()
                    .scheme(restProperties.getSchema())
                    .host(restProperties.getHost())
                    .path(restProperties.getPath())
                    .toUriString();
        }
    }
}