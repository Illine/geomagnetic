package net.c7j.weather.geomagnetic.test.helper.generator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.model.base.IndexType;
import net.c7j.weather.geomagnetic.model.base.TimeIntervalType;

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