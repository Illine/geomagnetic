package com.illine.weather.geomagnetic.rest.controller;

import com.illine.weather.geomagnetic.model.dto.BaseResponse;
import com.illine.weather.geomagnetic.service.EtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final EtlService forecastEtlService;

    @Autowired
    ServiceController(EtlService forecastEtlService) {
        this.forecastEtlService = forecastEtlService;
    }

    @PatchMapping("/forecasts")
    public ResponseEntity<BaseResponse> updateForecasts() {
        forecastEtlService.updateForecasts();
        return ResponseEntity.ok(new BaseResponse());
    }

}