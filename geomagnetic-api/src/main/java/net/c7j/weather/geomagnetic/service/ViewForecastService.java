package net.c7j.weather.geomagnetic.service;

import org.springframework.http.ResponseEntity;

public interface ViewForecastService<T> {

    ResponseEntity<T> getDiurnal();

    ResponseEntity<T> getCurrent();

    ResponseEntity<T> getThreeDay();

}