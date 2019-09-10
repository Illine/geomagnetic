package net.c7j.weather.geomagnetic.dao.access;

import net.c7j.weather.geomagnetic.dao.entity.ForecastEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

public interface ForecastAccessService {

    void save(Collection<ForecastEntity> forecasts);

    Stream<ForecastEntity> findDiurnal(LocalDate today);

    Stream<ForecastEntity> findCurrent(LocalDateTime today);

    Stream<ForecastEntity> findThreeDay(LocalDate today);

}