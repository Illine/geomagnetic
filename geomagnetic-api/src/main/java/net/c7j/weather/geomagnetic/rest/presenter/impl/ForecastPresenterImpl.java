package net.c7j.weather.geomagnetic.rest.presenter.impl;

import net.c7j.weather.geomagnetic.mapper.impl.MobileForecastDtoMapper;
import net.c7j.weather.geomagnetic.model.dto.MobileForecastResponse;
import net.c7j.weather.geomagnetic.rest.presenter.ForecastPresenter;
import net.c7j.weather.geomagnetic.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForecastPresenterImpl implements ForecastPresenter {

    private final ForecastService forecastService;
    private final MobileForecastDtoMapper mobileForecastMapper;

    @Autowired
    ForecastPresenterImpl(ForecastService forecastService, MobileForecastDtoMapper mobileForecastMapper) {
        this.forecastService = forecastService;
        this.mobileForecastMapper = mobileForecastMapper;
    }

    @Override
    public MobileForecastResponse getDiurnal() {
        var forecasts = forecastService.findDiurnal();
        return new MobileForecastResponse(mobileForecastMapper.convertToDestinations(forecasts));
    }

    @Override
    public MobileForecastResponse getCurrent() {
        var forecasts = forecastService.findCurrent();
        return new MobileForecastResponse(mobileForecastMapper.convertToDestinations(forecasts));
    }

    @Override
    public MobileForecastResponse getThreeDays() {
        var forecasts = forecastService.findThreeDays();
        return new MobileForecastResponse(mobileForecastMapper.convertToDestinations(forecasts));
    }
}