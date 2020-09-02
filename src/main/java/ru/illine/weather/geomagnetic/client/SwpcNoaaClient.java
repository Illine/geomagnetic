package ru.illine.weather.geomagnetic.client;

import org.springframework.http.ResponseEntity;

public interface SwpcNoaaClient {

    ResponseEntity<String> get3DayGeomagneticForecast();

}