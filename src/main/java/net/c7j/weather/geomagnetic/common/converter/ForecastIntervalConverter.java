package net.c7j.weather.geomagnetic.common.converter;

import net.c7j.weather.geomagnetic.common.ForecastIntervalType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ForecastIntervalConverter implements AttributeConverter<ForecastIntervalType, String> {

    @Override
    public String convertToDatabaseColumn(ForecastIntervalType type) {
        return type.getInterval();
    }

    @Override
    public ForecastIntervalType convertToEntityAttribute(String interval) {
        return ForecastIntervalType.intervalOf(interval);
    }
}
