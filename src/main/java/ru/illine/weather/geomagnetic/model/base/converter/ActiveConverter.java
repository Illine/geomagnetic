package ru.illine.weather.geomagnetic.model.base.converter;

import ru.illine.weather.geomagnetic.model.base.ActiveType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActiveConverter implements AttributeConverter<ActiveType, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(ActiveType type) {
        return type.isActive();
    }

    @Override
    public ActiveType convertToEntityAttribute(Boolean active) {
        return ActiveType.activeOf(active);
    }
}