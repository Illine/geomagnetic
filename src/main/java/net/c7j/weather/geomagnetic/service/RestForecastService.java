package net.c7j.weather.geomagnetic.service;

import net.c7j.weather.geomagnetic.dao.dto.ForecastResponse;
import org.springframework.http.ResponseEntity;

public interface RestForecastService {

    ResponseEntity<ForecastResponse> getDiurnal();

    ResponseEntity<ForecastResponse> getCurrent();

    ResponseEntity<ForecastResponse> getThreeDay();

}