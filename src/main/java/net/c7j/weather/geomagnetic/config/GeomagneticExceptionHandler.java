package net.c7j.weather.geomagnetic.config;

import net.c7j.weather.geomagnetic.dao.dto.BaseDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
class GeomagneticExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<BaseDto> exception(Exception e) {
        return new ResponseEntity<>(new BaseDto(false, e.getMessage()), INTERNAL_SERVER_ERROR);
    }
}