package ru.illine.weather.geomagnetic.rest.presenter;

import ru.illine.weather.geomagnetic.model.dto.MobileForecastResponse;

public interface ForecastPresenter {

    MobileForecastResponse getDiurnal();

    MobileForecastResponse getCurrent();

    MobileForecastResponse getThreeDays();

}