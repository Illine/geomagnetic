package net.c7j.weather.geomagnetic.mapper.impl;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;
import net.c7j.weather.geomagnetic.mapper.AbstractDtoMapper;
import net.c7j.weather.geomagnetic.model.base.ActiveType;
import net.c7j.weather.geomagnetic.model.dto.ForecastDto;
import net.c7j.weather.geomagnetic.service.DtoMapperChecker;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.function.BiConsumer;

@Component
public class ForecastDtoMapper extends AbstractDtoMapper<ForecastEntity, ForecastDto> {

    protected ForecastDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(entityClass, dtoClass)
                .setPostConverter(toDtoConverter(new DtoConverter()));
        modelMapper
                .createTypeMap(dtoClass, entityClass)
                .setPostConverter(toEntityConverter(new EntityConverter()));
    }

    public static class DtoConverter implements BiConsumer<ForecastEntity, ForecastDto>, DtoMapperChecker {

        @Override
        public void accept(ForecastEntity entity, ForecastDto dto) {
            whenNotNull(entity, it -> dto.setTime(getTime(it)));
        }

        private Long getTime(ForecastEntity entity) {
            var forecastTime = LocalDateTime.of(entity.getForecastDate(), entity.getForecastTime());
            var greenwichZone = forecastTime.atZone(ZoneOffset.UTC);
            return greenwichZone.toInstant().getEpochSecond();
        }
    }

    public static class EntityConverter implements BiConsumer<ForecastDto, ForecastEntity>, DtoMapperChecker {

        @Override
        public void accept(ForecastDto dto, ForecastEntity entity) {
            whenNotNull(dto, it -> entity.setForecastDate(getLocalDate(it.getTime())));
            whenNotNull(dto, it -> entity.setForecastTime(getLocalTime(it.getTime())));
            whenNotNull(dto, it -> entity.setActive(ActiveType.ENABLED));
        }

        private LocalDate getLocalDate(Long mills) {
            return Instant.ofEpochMilli(mills).atZone(ZoneOffset.UTC).toLocalDate();
        }

        private LocalTime getLocalTime(Long mills) {
            return Instant.ofEpochMilli(mills).atZone(ZoneOffset.UTC).toLocalTime();
        }
    }
}