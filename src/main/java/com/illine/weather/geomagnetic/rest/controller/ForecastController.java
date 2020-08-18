package com.illine.weather.geomagnetic.rest.controller;

import com.illine.weather.geomagnetic.model.dto.MobileForecastResponse;
import com.illine.weather.geomagnetic.rest.presenter.ForecastPresenter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Controller returns geomagnetic forecasts")
@RestController
@RequestMapping("/forecasts")
public class ForecastController {

    private final ForecastPresenter forecastPresenter;

    @Autowired
    ForecastController(ForecastPresenter forecastPresenter) {
        this.forecastPresenter = forecastPresenter;
    }

    @ApiOperation(value = "Returns a forecast after a current time for a day")
    @GetMapping(value = "/diurnal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MobileForecastResponse> getDiurnal() {
        return ResponseEntity.ok(forecastPresenter.getDiurnal());
    }

    @ApiOperation(value = "Returns a forecast for all day")
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MobileForecastResponse> getCurrent() {
        return ResponseEntity.ok(forecastPresenter.getCurrent());
    }

    @ApiOperation(value = "Returns a three day forecast for today, tomorrow, a day after tomorrow")
    @GetMapping(value = "/three-day", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MobileForecastResponse> getThreeDays() {
        return ResponseEntity.ok(forecastPresenter.getThreeDays());
    }
}