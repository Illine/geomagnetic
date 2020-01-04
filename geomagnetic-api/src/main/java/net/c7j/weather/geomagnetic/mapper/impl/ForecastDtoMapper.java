package net.c7j.weather.geomagnetic.mapper.impl;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.mapper.AbstractDtoMapper;
import net.c7j.weather.geomagnetic.model.dto.ForecastDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ForecastDtoMapper extends AbstractDtoMapper<ForecastEntity, ForecastDto> {

    public ForecastDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(sourceClass, destinationClass);
        modelMapper.createTypeMap(destinationClass, sourceClass);
    }
}