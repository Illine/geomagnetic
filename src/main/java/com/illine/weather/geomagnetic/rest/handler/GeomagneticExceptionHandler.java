package com.illine.weather.geomagnetic.rest.handler;

import com.illine.weather.geomagnetic.exception.NotFoundException;
import com.illine.weather.geomagnetic.exception.ParseException;
import com.illine.weather.geomagnetic.model.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j(topic = "GEOMAGNETIC-EXCEPTION-HANDLER")
class GeomagneticExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<BaseResponse> notFoundException(NotFoundException e) {
        LOGGER.warn("A 'NotFoundException' was caught", e);
        return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(ParseException.class)
    ResponseEntity<BaseResponse> parseException(ParseException e) {
        LOGGER.warn("A 'ParseException' was caught", e);
        return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<BaseResponse> exception(Exception e) {
        LOGGER.error("An unknown exception was caught", e);
        return new ResponseEntity<>(new BaseResponse(false, e.getMessage()), INTERNAL_SERVER_ERROR);
    }
}