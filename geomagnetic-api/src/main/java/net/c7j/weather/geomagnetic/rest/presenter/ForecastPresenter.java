package net.c7j.weather.geomagnetic.rest.presenter;

import net.c7j.weather.geomagnetic.model.dto.MobileForecastResponse;

public interface ForecastPresenter {

    MobileForecastResponse getDiurnal();

    MobileForecastResponse getCurrent();

    MobileForecastResponse getThreeDays();

}