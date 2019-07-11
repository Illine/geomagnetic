package net.c7j.weather.geomagnetic.dao.access;

import net.c7j.weather.geomagnetic.dao.dto.ForecastDto;
import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

public interface ForecastAccessService {

    void upsert(Collection<ForecastEntity> collection);

    Stream<ForecastDto> findDiurnal(LocalDate today);

    Stream<ForecastDto> findCurrent(LocalDateTime today);

    Stream<ForecastDto> findThreeDay(LocalDate today);

    Stream<ForecastEntity> getThreeDay(LocalDate today);
}