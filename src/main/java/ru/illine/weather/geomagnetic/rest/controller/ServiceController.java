package ru.illine.weather.geomagnetic.rest.controller;

import ru.illine.weather.geomagnetic.model.dto.BaseResponse;
import ru.illine.weather.geomagnetic.service.EtlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Controller manages the Application")
@RestController
@RequestMapping("/services")
public class ServiceController {

    private final EtlService forecastEtlService;

    @Autowired
    ServiceController(EtlService forecastEtlService) {
        this.forecastEtlService = forecastEtlService;
    }

    @ApiOperation(value = "Manual updating forecasts")
    @ApiResponses(value = {
            @ApiResponse(code = 503, message = "Swpc Noaa Unavailable", response = BaseResponse.class)
    })
    @PatchMapping("/forecasts")
    public ResponseEntity<BaseResponse> updateForecasts() {
        forecastEtlService.updateForecasts();
        return ResponseEntity.ok(new BaseResponse());
    }

}