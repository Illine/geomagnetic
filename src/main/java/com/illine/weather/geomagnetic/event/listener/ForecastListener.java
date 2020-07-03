package com.illine.weather.geomagnetic.event.listener;

import com.illine.weather.geomagnetic.mapper.impl.TxtForecastDtoMapper;
import com.illine.weather.geomagnetic.model.dto.ForecastEventWrapper;
import com.illine.weather.geomagnetic.service.ForecastUpsertService;
import lombok.extern.slf4j.Slf4j;
import com.illine.weather.geomagnetic.dao.access.ForecastAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Component
@Slf4j(topic = "GEOMAGNETIC-EVENT")
public class ForecastListener {

    private final TxtForecastDtoMapper txtForecastMapper;
    private final ForecastAccessService forecastAccessService;
    private final ForecastUpsertService forecastUpsertService;

    @Autowired
    ForecastListener(TxtForecastDtoMapper txtForecastMapper, ForecastAccessService forecastAccessService, ForecastUpsertService forecastUpsertService) {
        this.txtForecastMapper = txtForecastMapper;
        this.forecastAccessService = forecastAccessService;
        this.forecastUpsertService = forecastUpsertService;
    }

    @EventListener
    @Async(value = "forecastEventThreadPool")
    public void onEvent(ForecastEventWrapper eventWrapper) {
        Assert.notNull(eventWrapper, "The 'eventWrapper' shouldn't be null!");
        Assert.notEmpty(eventWrapper.getTxtForecasts(), "A set of TxtForecast shouldn't be null or empty!");
        LOGGER.info("A set of text forecast was listened");
        var convertedForecasts = txtForecastMapper.convertToSources(eventWrapper.getTxtForecasts());
        var upsertedForecast = forecastUpsertService.upsertForecasts(convertedForecasts, LocalDate.now());
        forecastAccessService.save(upsertedForecast);
    }
}