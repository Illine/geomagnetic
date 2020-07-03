package com.illine.weather.geomagnetic.client;

import org.springframework.http.ResponseEntity;

public interface SwpNoaaClient {

    ResponseEntity<String> get3DayGeomagForecast();

}