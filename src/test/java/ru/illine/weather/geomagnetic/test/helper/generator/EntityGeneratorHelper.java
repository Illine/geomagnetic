package ru.illine.weather.geomagnetic.test.helper.generator;

import ru.illine.weather.geomagnetic.dao.entity.ForecastEntity;
import ru.illine.weather.geomagnetic.model.base.IndexType;
import ru.illine.weather.geomagnetic.model.base.TimeIntervalType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityGeneratorHelper {

    private static final IndexType DEFAULT_INDEX = IndexType.UNSETTLED;
    private static final TimeIntervalType DEFAULT_INTERVAL = TimeIntervalType.INTERVAL_12_15;
    private static final LocalDate DEFAULT_DATE = LocalDate.now();

    public static ForecastEntity generateForecastEntity() {
        var entity = new ForecastEntity();
        entity.setIndex(DEFAULT_INDEX);
        entity.setForecastTime(DEFAULT_INTERVAL.getTimeInterval());
        entity.setForecastDate(DEFAULT_DATE);
        return entity;
    }
}