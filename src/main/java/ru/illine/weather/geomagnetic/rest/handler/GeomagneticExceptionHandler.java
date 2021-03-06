package ru.illine.weather.geomagnetic.rest.handler;

import ru.illine.weather.geomagnetic.exception.NotFoundException;
import ru.illine.weather.geomagnetic.exception.ParseException;
import ru.illine.weather.geomagnetic.exception.SwpcNoaaException;
import ru.illine.weather.geomagnetic.model.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j(topic = "GEOMAGNETIC-EXCEPTION-HANDLER")
class GeomagneticExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<BaseResponse> notFoundException(NotFoundException e) {
        LOGGER.error("A 'NotFoundException' was caught", e);
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(ParseException.class)
    ResponseEntity<BaseResponse> parseException(ParseException e) {
        LOGGER.error("A 'ParseException' was caught", e);
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SwpcNoaaException.class)
    ResponseEntity<BaseResponse> swpcNoaaException(SwpcNoaaException e) {
        LOGGER.error("A 'SwpcNoaaException' was caught", e);
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<BaseResponse> exception(Exception e) {
        LOGGER.error("An unknown exception was caught", e);
        return new ResponseEntity<>(new BaseResponse(e.getMessage()), INTERNAL_SERVER_ERROR);
    }
}