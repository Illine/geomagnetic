package net.c7j.weather.geomagnetic.dao.base.converter;

import net.c7j.weather.geomagnetic.dao.base.IndexType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IndexConverter implements AttributeConverter<IndexType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(IndexType type) {
        return type.getIndex();
    }

    @Override
    public IndexType convertToEntityAttribute(Integer index) {
        return IndexType.indexOf(index);
    }
}