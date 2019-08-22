package net.c7j.weather.geomagnetic.dao.access;

import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface ForecastAccessService {

    void save(Stream<ForecastDto> forecasts);

    Stream<ForecastDto> findDiurnal(LocalDate today);

    Stream<ForecastDto> findCurrent(LocalDateTime today);

    Stream<ForecastDto> findThreeDay(LocalDate today);

    Stream<ForecastEntity> getThreeDay(LocalDate today);
}