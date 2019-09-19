package net.c7j.weather.geomagnetic.rest.handler;

import lombok.extern.slf4j.Slf4j;
import net.c7j.weather.geomagnetic.exception.NotFoundException;
import net.c7j.weather.geomagnetic.exception.ParseException;
import net.c7j.weather.geomagnetic.model.dto.BaseResponse;
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