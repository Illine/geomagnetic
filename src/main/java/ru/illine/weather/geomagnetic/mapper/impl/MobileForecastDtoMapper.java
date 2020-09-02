package ru.illine.weather.geomagnetic.mapper.impl;

import ru.illine.weather.geomagnetic.mapper.AbstractDtoMapper;
import ru.illine.weather.geomagnetic.model.dto.ForecastDto;
import ru.illine.weather.geomagnetic.model.dto.MobileForecastDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.BiConsumer;

@Component
public class MobileForecastDtoMapper extends AbstractDtoMapper<ForecastDto, MobileForecastDto> {

    protected MobileForecastDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .setPostConverter(toDestinationConverter(new DestinationConverter()));
    }

    public static class DestinationConverter implements BiConsumer<ForecastDto, MobileForecastDto> {

        @Override
        public void accept(ForecastDto source, MobileForecastDto destination) {
            destination.setTime(getTime(source));
        }

        private Long getTime(ForecastDto source) {
            var forecastDateTime = LocalDateTime.of(source.getForecastDate(), source.getForecastTime());
            return forecastDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        }
    }
}