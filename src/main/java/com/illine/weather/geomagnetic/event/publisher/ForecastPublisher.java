package com.illine.weather.geomagnetic.event.publisher;

import com.illine.weather.geomagnetic.exception.ParseException;
import com.illine.weather.geomagnetic.model.dto.ForecastEventWrapper;
import com.illine.weather.geomagnetic.model.dto.TxtForecastDto;
import com.illine.weather.geomagnetic.service.ForecastParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Set;

@Component
@Slf4j(topic = "GEOMAGNETIC-EVENT")
public class ForecastPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ForecastParserService forecastParserService;

    @Autowired
    ForecastPublisher(ApplicationEventPublisher applicationEventPublisher, ForecastParserService forecastParserService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.forecastParserService = forecastParserService;
    }

    public void publish(ResponseEntity<String> forecastTxt) {
        Assert.notNull(forecastTxt, "The 'forecastTxt' shouldn't be null!");
        LOGGER.info("A text forecast will be published...");

        try {
            var wrappedForecast = toWrap(forecastTxt);
            applicationEventPublisher.publishEvent(wrappedForecast);
        } catch (ParseException e) {
            LOGGER.error("Impossible parse to a collect a 'forecastTxt'. An event won't be published!", e);
        } catch (Exception e) {
            LOGGER.error("Unknown error. An event won't publish!", e);
        }
    }

    private ForecastEventWrapper toWrap(ResponseEntity<String> forecastTxt) {
        return new ForecastEventWrapper(toCollection(forecastTxt));
    }

    private Set<TxtForecastDto> toCollection(ResponseEntity<String> forecastTxt) {
        var body = forecastTxt.getBody();
        return forecastParserService.toParse(body);
    }
}