package com.illine.weather.geomagnetic.rest.presenter;

import com.illine.weather.geomagnetic.model.dto.MobileForecastResponse;

public interface ForecastPresenter {

    MobileForecastResponse getDiurnal();

    MobileForecastResponse getCurrent();

    MobileForecastResponse getThreeDays();

}