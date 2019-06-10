package net.c7j.weather.geomagnetic.common.converter;

import net.c7j.weather.geomagnetic.common.TimeIntervalType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ForecastIntervalConverter implements AttributeConverter<TimeIntervalType, String> {

    @Override
    public String convertToDatabaseColumn(TimeIntervalType type) {
        return type.getInterval();
    }

    @Override
    public TimeIntervalType convertToEntityAttribute(String interval) {
        return TimeIntervalType.intervalOf(interval);
    }
}
