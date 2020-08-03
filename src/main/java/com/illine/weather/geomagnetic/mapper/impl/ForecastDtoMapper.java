package com.illine.weather.geomagnetic.mapper.impl;

import com.illine.weather.geomagnetic.dao.entity.ForecastEntity;
import com.illine.weather.geomagnetic.mapper.AbstractDtoMapper;
import com.illine.weather.geomagnetic.model.dto.ForecastDto;
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