package com.illine.weather.geomagnetic.client.impl;

import com.illine.weather.geomagnetic.config.rest.RestProperties;
import lombok.extern.slf4j.Slf4j;
import com.illine.weather.geomagnetic.client.SwpNoaaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Function;

@Service
@Slf4j(topic = "GEOMAGNETIC-CLIENT")
public class SwpcNoaaClientImpl implements SwpNoaaClient {

    private final RestProperties properties;
    private final RestTemplate swpNoaaRestTemplate;

    @Autowired
    SwpcNoaaClientImpl(RestProperties properties, RestTemplate swpNoaaRestTemplate) {
        this.properties = properties;
        this.swpNoaaRestTemplate = swpNoaaRestTemplate;
    }

    @Override
    public ResponseEntity<String> get3DayGeomagForecast() {
        LOGGER.info("Exchange of a text forecast from SWPC NOAA is starting...");
        var uri = new SwpcNoaaUriBuilder();
        return swpNoaaRestTemplate.getForEntity(uri.apply(properties), String.class);
    }

    private static class SwpcNoaaUriBuilder implements Function<RestProperties, String> {

        @Override
        public String apply(RestProperties restProperties) {
            return UriComponentsBuilder.newInstance()
                    .scheme(restProperties.getSwpNoaa().getSchema())
                    .host(restProperties.getSwpNoaa().getHost())
                    .path(restProperties.getSwpNoaa().getPath())
                    .toUriString();
        }
    }
}