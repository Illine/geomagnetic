package net.c7j.weather.geomagnetic.rest.controller;

import net.c7j.weather.geomagnetic.dao.dto.ForecastResponse;
import net.c7j.weather.geomagnetic.service.ViewForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forecasts")
public class ForecastController {

    private final ViewForecastService<ForecastResponse> viewForecastService;

    @Autowired
    ForecastController(ViewForecastService<ForecastResponse> viewForecastService) {
        this.viewForecastService = viewForecastService;
    }

    @GetMapping(value = "/get/diurnal", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ForecastResponse> diurnal() {
        return viewForecastService.getDiurnal();
    }

    @GetMapping(value = "/get/current", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ForecastResponse> current() {
        return viewForecastService.getCurrent();
    }

    @GetMapping(value = "/get/three-day", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ForecastResponse> threeDay() {
        return viewForecastService.getThreeDay();
    }
}