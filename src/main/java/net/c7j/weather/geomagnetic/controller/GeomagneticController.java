package net.c7j.weather.geomagnetic.controller;

import net.c7j.weather.geomagnetic.dto.GeomagneticDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/forecast")
public class GeomagneticController {

    @GetMapping("/")
    public GeomagneticDto getGeomagnetic() {
        return null;
    }
}