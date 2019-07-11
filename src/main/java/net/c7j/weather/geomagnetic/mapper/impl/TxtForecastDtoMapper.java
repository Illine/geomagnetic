package net.c7j.weather.geomagnetic.mapper.impl;

import net.c7j.weather.geomagnetic.dao.dto.TxtForecastDto;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.mapper.AbstractDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class TxtForecastDtoMapper extends AbstractDtoMapper<ForecastEntity, TxtForecastDto> {

    protected TxtForecastDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(dtoClass, entityClass)
                .setPostConverter(toEntityConverter(new EntityConverter()));
    }

    public static class EntityConverter implements BiConsumer<TxtForecastDto, ForecastEntity> {

        @Override
        public void accept(TxtForecastDto dto, ForecastEntity entity) {
            entity.setForecastTime(dto.getInterval().getTimeInterval());
        }
    }
}